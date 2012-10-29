package body Mmr
is
   
  --===========================================================================
  --  [Spec] Function: Invariant -- implements the package invariant for Mmr
  --
  --  Pointers -- all pointers should point to distinct memory cells
  --
  --===========================================================================
  function Invariant return Boolean
  is
    Result: Boolean;
  begin
    Result := True;
    -- Checking for uniqueness requires a double iteration over the data 
    -- structure.  In this case, the data structure is a two dimensional array.
    -- Thus, we need a total of four iterations.
    --
    -- I,J are coordinates for "first" pass over the data structure
    for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
      for J in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
        -- K,L are coordinates for "second" pass over the data structure
        for K in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
          for L in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
            if ((not ((I=K) and (J=L))) 
              and then (Pointers(I)(J) = Pointers(K)(L))) then
              Result := False;
              -- we should exit from the loop at this point, but I don't want to
              -- stress Kiasan
            end if;
	    exit when Result = False;
          end loop;
	  exit when Result = False;
        end loop;
	exit when Result = False;
      end loop;
      exit when Result = False;
    end loop;
    return Result;
  end Invariant;
  
  --===========================================================================
  --  [Spec] Function: OutBoxWellFormed 
  --
  --  Given an outbox for P, if the flag for a mailbox slot I is True, then
  --  the message at that slot has origin P and destination I.  If the flag is False, then 
  --  the message at that slot is the default message.
  --
  --===========================================================================
  function OutBoxWellFormed(P: in Lbl_t.Proc_Id) return Boolean
  is
    Result: Boolean;
    M : Msg_T.Msg;
    Dest: Lbl_t.Proc_Id;
    Origin: Lbl_t.Proc_Id;
  begin
    Result := True;

    for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
      M := Memory.Read(Pointers(P)(I));
      if Flags(P)(I) then
	 Dest   := Msg_t.Get_Dest(M);
         Origin := Msg_t.Get_Origin(M);
        if not ((Origin = P) and then (Dest = I)) then
          Result := False;
        end if;
      else
        if not Msg_t.Is_Default_Message(M) then
          Result := False;
        end if;
      end if;
    end loop;
    return Result;
  end OutBoxWellFormed;
  
  --===========================================================================
  --  [Spec] Function: InBoxWellFormed 
  --
  --  Given an inbox for P, if the flag for a mailbox slot I is True, then
  --  the message at that slot has destination P and origin I.  If the flag is False, then 
  --  the message at that slot is the default message.
  --
  --===========================================================================
  function InBoxWellFormed(P: in Lbl_t.Proc_Id) return Boolean
  is
    Result: Boolean;
    M : Msg_T.Msg;
    Dest: Lbl_t.Proc_Id;
    Origin: Lbl_t.Proc_Id;
  begin
    Result := True;

    for I in Lbl_t.Proc_Id loop
      M := Memory.Read(Pointers(P)(I));
      if Flags(P)(I) then
	 Dest   := Msg_t.Get_Dest(M);
         Origin := Msg_t.Get_Origin(M);
        if not ((Origin = I) and then (Dest = P)) then
          Result := False;
        end if;
      else
        if not Msg_t.Is_Default_Message(M) then
          Result := False;
        end if;
      end if;
    end loop;
    return Result;
  end InBoxWellFormed;
  
   --=====================================================================
   -- Procedure: Fill_Mem_Row
   --
   -- Retrieve messages for process P and store them in the memory row
   -- data structure M.  The data structure M acts as a "mailbox" for P
   -- holding messages destined to P.  When a process Q has not sent a message 
   -- to P (as indicated by Flags), Q's slot in P's row holds a default message.
   --
   --=====================================================================
  procedure Fill_Mem_Row(P: in Lbl_t.Proc_Id; M: out Mem_t.Mem_Row)
    --# global in Flags;
    --#        in Pointers;
    --#        in Memory.Mem_Space;
    --# derives M from P,
    --#                Flags,
    --#                Pointers,
    --#                Memory.Mem_Space;
    --# --===== Post-condition =======    
    --# post for all I in Lbl_t.Proc_ID =>
    --#        ((Flags(P)(I) -> (M(I) = Memory.Mem_Space(Pointers(P)(I))))
    --#      and (not Flags(P)(I) -> (M(I) = Msg_t.Def_Msg)));
  is
  begin
    -- initialize mem row to default message (using an aggregate here will allow us
    -- to class M as an "out"-only variable
    M := Mem_t.Mem_Row'(others => Msg_t.Def_Msg);
    -- loop through each (sending) process I
    for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
      if Flags(P)(I) = True then
        -- if Flags indicates that P has been sent a message from I,
	      -- then copy that message into T(I) via temp variable M.
        M(I) := Memory.Read(Pointers(P)(I));
      end if;
    end loop;
  end Fill_Mem_Row;

   --=====================================================================
   -- Procedure: Zero_Flags
   --
   -- Sets all of P's flags to false
   --
   --=====================================================================
  procedure Zero_Flags(P: in Lbl_t.Proc_Id)
    --# global in out Flags;
    --# derives Flags from Flags,
    --#                    P;
    --# --===== Post-condition =======    
    --# post for all Q in Lbl_t.Proc_ID =>
    --#     (for all I in Lbl_t.Proc_ID => (
    --#        (Q = P -> not Flags(Q)(I))
    --#        and then (Q /= P -> Flags(Q)(I) = Flags~(Q)(I))));
  is
  begin
    for I in Lbl_t.Proc_Id loop
      Flags(P)(I) := False;
    end loop;
  end Zero_Flags;
  
  --=====================================================================
  -- Procedure: Zero_Mem_Row
  --
  -- Clear (write default message to) all the memory slots "owned" by the 
  -- mailbox of P (ownership is indicated by Pointers data structure)
  --
  --=====================================================================
  procedure Zero_Mem_Row(P: in Lbl_t.Proc_Id)
    --# global in     Pointers;
    --#        in out Memory.Mem_Space;
    --# derives Memory.Mem_Space from Memory.Mem_Space,
    --#                               Pointers,
    --#                               P;
    --# --============= Pre-condition ==================
    --# pre Invariant(Pointers);
    --# --===== Post/Frame-condition =======    
    --# post for all Q in Lbl_t.Proc_ID =>
    --#     (for all I in Lbl_t.Proc_ID =>
    --#         ((Q = P  ->  Memory.Mem_Space(Pointers(Q)(I)) = Msg_t.Def_Msg)
    --#     and (Q /= P ->  Memory.Mem_Space(Pointers(Q)(I)) = Memory.Mem_Space~(Pointers(Q)(I)))));
  is
  begin
    for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
      Memory.Write(Msg_t.Def_Msg, Pointers(P)(I));
    end loop;
  end Zero_Mem_Row;

  -- Public procedures
  procedure Route
  is
     T: Lbl_T.Pointer;
     B: Boolean;
  begin
      for I in Lbl_t.Proc_ID loop
         for J in Lbl_t.Proc_ID range I .. Lbl_t.Proc_ID'Last loop
               if not Policy.Is_Allowed(I,J) then
                  Memory.Write(Msg_t.Def_Msg, Pointers(I)(J));
                  Flags(I)(J) := FALSE;
               end if;

               if not Policy.Is_Allowed(J,I) then
                  Memory.Write(Msg_t.Def_Msg, Pointers(J)(I));
                  Flags(J)(I) := FALSE;
               end if;
	       
	       -- note: it may not be necessary to swap the flags or even pointers below,
	       -- but the rest of the code currently depends on Flags being swapped
               if Flags(I)(J) or Flags(J)(I) then
                  T := Pointers(I)(J);
		  B := Flags(I)(J);
                  Pointers(I)(J) := Pointers(J)(I);
		  Flags(I)(J) := Flags(J)(I);
                  Pointers(J)(I) := T;
		  Flags(J)(I) := B;
               end if;
         end loop;
      end loop;
--    for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
--      for J in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
--        if(Flags(I)(J) = True) then
--          -- there is a message waiting to be delivered from process I to J
--          
--          M := Memory.Read(Pointers(I)(J));
--          Origin := Msg_t.Get_Origin(M);
--          Dest   := Msg_t.Get_Dest(M);
--
--          --Util.Output_Message(M, "The following message is waiting to " &
--          --                    "be delivered");
--          if Origin = I and Dest = J then
--            if Policy.Is_Allowed(Origin, Dest) = False then
--              -- Origin cannot talk with Dest so clear out the
--              --  memory and reset flag
--
--              -- Util.Output_String("NOT ALLOWED");
--
--              Memory.Write(Msg_t.Def_Msg, Pointers(Origin)(Dest));
--              Flags(Origin)(Dest) := False;
--            else
--              if Flags(Dest)(Origin) = True then
--                -- we have a situation where A -> B and B -> A
--                --  so we need to make sure that B can talk
--                --  with A before doing the swap
--                if Policy.Is_Allowed(Dest, Origin) = False then
--                  -- B cannot talk with A so only deliver A to B
--
--                  -- wipe out B -> A message
--                  Memory.Write(Msg_t.Def_Msg, Pointers(Dest)(Origin));
--                  Flags(Dest)(Origin) := False;
--
--                  -- deliver A -> B
--                  Temp := Pointers(Origin)(Dest);
--                  Pointers(Origin)(Dest) := Pointers(Dest)(Origin);
--                  Pointers(Dest)(Origin) := Temp;
--                  Flags(Dest)(Origin) := True;
--
--                  -- Util.Output_String("There was a message waiting " &
--                  --                   "from Dest to Origin but it is " &
--                  --                   "not allowed by the policy.  " &
--                  --                   "Only the first message has " &
--                  --                   "been delivered.");
--
--                else
--                  -- A can talk with B and B can talk with A so simply
--                  -- swap their pointers5
--                  Temp := Pointers(Origin)(Dest);
--                  Pointers(Origin)(Dest) := Pointers(Dest)(Origin);
--                  Pointers(Dest)(Origin) := Temp;
--                  -- both flags are already true
--
--                  -- Util.Output_String("Two messages delivered");
--                end if;
--              else
--                -- A wants to talk with B but B doesn't want
--                --  to talk with A simply swap pointers and
--                --  reset the flags
--                Temp := Pointers(Origin)(Dest);
--                Pointers(Origin)(Dest) := Pointers(Dest)(Origin);
--                Pointers(Dest)(Origin) := Temp;
--                Flags(Origin)(Dest) := False;
--                Flags(Dest)(Origin) := True;
--                        
--                -- Util.Output_String("One message delivered");
--              end if;
--            end if;
--          else
--            -- If the Origin != I or Dest != J then we know that
--            --  a previous iteration has already moved the
--            --  message originally located at J, I so there
--            --  is nothing more to do
--
--            -- Util.Output_String("Origin != I or Dest != J.  Message " &
--            --                   "already delivered.");
--                  
--            null;
--          end if;
--        end if;
--      end loop;
--    end loop;
  end Route;

     
  -- Send_Msg procedure is called by System when the system is in the
  -- "sending" state.  The MMR places the
  --  message in memory and sets the flag to indicate there is a
  --  message waiting to be delivered.  It is the responsibility
  --  of Route to check to see if the communication is allowed by
  --  the policy.
  procedure Send_Msg(M: in Msg_t.Msg)
  is
     Origin: Lbl_t.Proc_Id;
     Dest: Lbl_t.Proc_Id;
  begin
     Origin := Msg_t.Get_Origin(M);
     Dest := Msg_t.Get_Dest(M);
     Memory.Write(M, Pointers(Origin)(Dest));
     Flags(Origin)(Dest) := True;
  end Send_Msg;


  -- Read_Msgs will be called from System for each Proc_Id.  Route
  --  should have already been called so all the messages are
  --  valid.  We just need to transfer P's messages into A and then
  --  do clean up.
  --  In the use of Flags and Pointers matrices, the first process
  --  used in indexing is the destination process (e.g., P), and
  --  the second index represents the source of the message.
  procedure Read_Msgs(P: in Lbl_t.Proc_Id; A: out Mem_t.Mem_Row)
  is
     Temp_Mem_Row : Mem_t.Mem_Row;
  begin
     -- copy all flagged messages for P into Temp_Mem_Row
     Fill_Mem_Row(P, Temp_Mem_Row);
     -- clear flags (set to false) associated with P's mailbox
     Zero_Flags(P);
     -- clear memory "owned" by P's mailbox
     Zero_Mem_Row(P);
     -- move the result to the output parameter
     A := Temp_Mem_Row;
  end Read_Msgs;
begin
  Pointers := Pointer_Matrix'(others =>
                                Pointer_Row'(others => 1));

  -- initialize the Pointers array with the numbers 1 .. Lbl_t.Proc_Id'Last
  for I in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
     for J in Lbl_t.Proc_Id range Lbl_t.Proc_Id'First .. Lbl_t.Proc_Id'Last loop
        Pointers(I)(J) := J + (Lbl_t.Proc_Id'Last * (I - 1));
     end loop;
  end loop;

end Mmr;
