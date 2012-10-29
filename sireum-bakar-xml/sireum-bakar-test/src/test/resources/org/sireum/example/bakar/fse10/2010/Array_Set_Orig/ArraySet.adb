with ArraySetDefs;
with ArraySetUnsigned;
use type ArraySetDefs.ID_Type;
use type ArraySetDefs.Value_Type;

package body ArraySet
--# own State is Item_List, Next_List, Free_Head, Used_Head;
is
  Max_Items : constant := 16;

  subtype Item_List_Index_Type is ArraySetUnsigned.Word range 0 .. Max_Items - 1;
  subtype Link_Type is ArraySetUnsigned.Word range 0 .. Max_Items;

  Terminator : constant := Link_Type'Last;

  type Item_Type is record
     ID           : ArraySetDefs.ID_Type;
     Value        : ArraySetDefs.Value_Type;
     Value_Length : ArraySetDefs.Value_Length_Type;
  end record;


  type Item_List_Type is array (Item_List_Index_Type) of Item_Type;
  type Next_List_Type is array (Item_List_Index_Type) of Link_Type;


  Item_List : Item_List_Type;
  Next_List : Next_List_Type;
  Free_Head : Link_Type;
  Used_Head : Link_Type;

  ------------------Invariant
  function Distinct_Element_Item_List return Boolean
    --# global in Item_List;
    --#return T => (T -> (for all I in Item_List_Index_Type range 0..Max_Items-1 =>
    --#(for all J in Item_List_Index_Type range I+1..Max_Items-1 =>
    --# (Item_List(I) /= Item_List(J)))));
    is
      Result : Boolean := True;
  begin
      for I in Item_List_Index_Type range 0..Max_Items-1 loop
        for J in Item_List_Index_Type range I+1..Max_Items-1 loop
          if Item_List(I) = Item_List(J) then
            Result := False;
          end if;
        end loop;
      end loop;
      return Result;
  end Distinct_Element_Item_List;

  function NonNull_ID return Boolean
    --# global in Item_List, Next_List, Used_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Used_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).ID = ArraySetDefs.Null_ID then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end NonNull_ID;

  function Unique_ID return Boolean
    --# global in Item_List, Next_List, Used_Head;
    is
      Cursor, Cursor2 : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Used_Head;
    while Cursor /= Terminator loop
      Cursor2 := Next_List(Cursor);
      while Cursor2 /= Terminator loop
        if Item_List(Cursor).ID = Item_List(Cursor2).ID then
          Result := False;
        end if;
        Cursor2 := Next_List(Cursor2);
      end loop;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Unique_ID;

  function NonNull_Value return Boolean
    --# global in Item_List, Next_List, Used_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Used_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).Value = ArraySetDefs.Null_Value then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end NonNull_Value;


  function NonNull_Value_Length return Boolean
    --# global in Item_List, Next_List, Used_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Used_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).Value_Length = ArraySetDefs.Null_Value_Length then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end NonNull_Value_Length;

  function Null_ID_Free_Item return Boolean
    --# global in Item_List, Next_List, Free_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Free_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).ID /= ArraySetDefs.Null_ID then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Null_ID_Free_Item;

  function Null_Value_Free_Item return Boolean
    --# global in Item_List, Next_List, Free_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Free_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).Value /= ArraySetDefs.Null_Value then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Null_Value_Free_Item;

  function Null_Value_Length_Free_Item return Boolean
    --# global in Item_List, Next_List, Free_Head;
    is
      Cursor : Item_List_Index_Type;
      Result : Boolean := True;
  begin
    Cursor := Free_Head;
    while Cursor /= Terminator loop
      if Item_List(Cursor).Value_Length /= ArraySetDefs.Null_Value_Length then
        Result := False;
      end if;
      Cursor := Next_List(Cursor);
    end loop;
    return Result;
  end Null_Value_Length_Free_Item;

  function Is_Cyclic(head : Item_List_Index_Type ) return Boolean
    --#global in Next_List;
    is
      Cursor, Fast_Cursor : Item_List_Index_Type;
      Result : Boolean := False;
    begin
      Cursor := head;
      Fast_Cursor := head;
      while Fast_Cursor /= Terminator and Next_List(Fast_Cursor) /= Terminator loop
        Cursor := Next_List(Cursor);
        Fast_Cursor := Next_List(Next_List(Fast_Cursor));
        if Cursor = Fast_Cursor then
          Result := True;
        end if;
      end loop;
      return Result;
   end Is_Cyclic;


   function Size_Of_List(head : Item_List_Index_Type ) return ArraySetUnsigned.Word
       --#global in Next_List;
       is
         Cursor : Item_List_Index_Type;
         Result : Link_Type := 0;
       begin
         Cursor := head;
         while Cursor /= Terminator loop
           Result := Result + 1;
           Cursor := Next_List(Cursor);
         end loop;
         return Result;
      end Size_Of_List;

   function Invariant return Boolean
     --#global in Next_List, Free_Head, Used_Head, Item_List;
     is
   begin
       return not Is_Cyclic(Free_Head) and not Is_Cyclic(Used_Head) and
       NonNull_ID and NonNull_Value and NonNull_Value_Length and
       Null_ID_Free_Item and Null_Value_Free_Item and Null_Value_Length_Free_Item 
       and Unique_ID and Size_Of_List(Free_Head) + Size_Of_List(Used_Head) =
         Max_Items;
   end Invariant;
  ------------------------
  -- Get_Value

  --   - pre-condition
  --        - ID is not null
  --   - post-condition
  --        - Value_Length actually corresponds to length of Value
  --        - Found is true  when ID is in the set;
  --          Found is false when ID is not in the set;
  --        - Found is true implies
  --          Value, Value_Length not null
  --        - Found is false implies
  --          Value, Value_Length are null
  ------------------------

  procedure Get_Value
    (ID           : in ArraySetDefs.ID_Type;
     Value        : out ArraySetDefs.Value_Type;
     Value_Length : out ArraySetDefs.Value_Length_Type;
     Found        : out Boolean)
  --# global in  Item_List, Used_Head,  Next_List;
  --# derives Value, Value_Length, Found from ID, Used_Head, Item_List, Next_List;
  --# pre not (ID = ArraySetDefs.Null_ID) ;
  --# post ((for some I in Item_List_Index_Type => ( ID = Item_List(I).ID)) = Found) 
  --# and (Found -> (Value /= ArraySetDefs.Null_Value and Value_Length /= ArraySetDefs.Null_Value_Length)) 
  --# and (not Found ->( Value /= ArraySetDefs.Null_Value and Value_Length /= ArraySetDefs.Null_Value_Length));
  is
     Curr_Index  : Link_Type;

  begin
     Value := ArraySetDefs.Null_Value;
     Value_Length := ArraySetDefs.Null_Value_Length;
     Curr_Index := Used_Head;
     Found := False;

      -- check Found = False;
      while not Found and then Curr_Index /= Terminator loop
         if Item_List (Curr_Index).ID = ID then
            Value := Item_List (Curr_Index).Value;
            Value_Length := Item_List(Curr_Index).Value_Length;
            Found := True;
          else
            Curr_Index := Next_List (Curr_Index);
         end if;
         --# assert Curr_Index /= Terminator and not Found;
      end loop;
      -- This is to suppress the unintialized Value, Value_Length
      if not Found then
        Value := ArraySetDefs.Null_Value;
        Value_Length := ArraySetDefs.Null_Value_Length;
      end if;

  end Get_Value;

  --------
  -- Add
  --         pre-condition
  --          - ID is not null
  --          - Value is not null
  --          - Value_Length is not null  check ****and matches length of Value****
  --         post-condition
  --          - Response code is "correct"
  --            Important to deal with cases where the DB is full and thus
  --            value does not get added.
  ---------

  procedure Add
    (ID           : in ArraySetDefs.ID_Type;
     Value        : in ArraySetDefs.Value_Type;
     Value_Length : in ArraySetDefs.Value_Length_Type;
     Response   : out ArraySetDefs.Response_Type)
    --# global in out Item_List, Next_List, Free_Head, Used_Head;
    --# derives Item_List
    --#    from ID,
    --#         Value,
    --#         Value_Length,
    --#         Item_List,
    --#         Next_List,
    --#         Free_Head,
    --#         Used_Head
    --#      &
    --#         Response, Next_List, Free_Head, Used_Head
    --#    from ID,
    --#         Item_List,
    --#         Next_List,
    --#         Free_Head,
    --#         Used_Head;
    --#
    --# pre   (ID /= ArraySetDefs.Null_ID) and
    --#       (Value /= ArraySetDefs.Null_Value) and
    --#       (Value_Length /= ArraySetDefs.Null_Value_Length);
    --#
    --# post  ((Free_Head~ = Terminator)  = (Response = ArraySetDefs.DB_No_Room))   --# and
    --#       ((for all I in Item_List_Index_Type =>
    --#                (ID /= Item_List~(I).ID)) ->
    --#            Response = ArraySetDefs.DB_Success) 
    --# and ((for some I in Item_List_Index_Type =>
    --#                (ID = Item_List~(I).ID)) =
    --#  (Response = ArraySetDefs.DB_Already_Exists));
  is
     Curr_Index : Link_Type;
     Temp_Value : ArraySetDefs.Value_Type;
     Temp_Value_Length : ArraySetDefs.Value_Length_Type;
     Found      : Boolean;
  begin

     if ID /= ArraySetDefs.Null_ID then

        -- Check for full used list
        if Free_Head /= Terminator then

           -- Ensure the ID is not already in the list
           --# accept Flow_Message, 10, Temp_Value, " Assignment to Temp_Value  is ineffective";
           --# accept Flow_Message, 10, Temp_Value_Length, " Assignment to Temp_Value_Length  is ineffective";
           Get_Value(ID,Temp_Value ,Temp_Value_Length, Found);
           --# end accept;

           if not Found then
              -- Use the entry at the head of the free list
              Curr_Index := Free_Head;
              -- Remove this entry from the free list
              Free_Head  := Next_List (Free_Head);
              -- Fill in the values
              Item_List (Curr_Index).ID := ID;
              Item_List (Curr_Index).Value :=  Value;
              Item_List (Curr_Index).Value_Length := Value_Length;

              -- Insert this entry into the used list
              Next_List (Curr_Index) := Used_Head;
              Used_Head              := Curr_Index;

              Response := ArraySetDefs.DB_Success;

           else
              Response := ArraySetDefs.DB_Already_Exists;
           end if;

        else
           Response := ArraySetDefs.DB_No_Room;
        end if;

     else
        Response := ArraySetDefs.DB_Input_Check_Fail;
     end if;

     --# accept Flow_Message, 33, Temp_Value, "The variable Temp_Value is neither referenced nor exported.";
     --# accept Flow_Message, 33, Temp_Value_Length, "The variable Temp_Value_Length is neither referenced nor exported.";

  end Add;

  -----------
  -- Delete
  --    * Delete
  --      - pre-condition
  --          - ID is not null
  --      - post-condition
  --          - Response code is "correct"
  -----------

  procedure Delete
    (ID          : in ArraySetDefs.ID_Type;
     Response    : out ArraySetDefs.Response_Type)
    --# global in out Item_List, Next_List, Free_Head, Used_Head;
    --# derives    Next_List, Free_Head from   ID, Item_List, Next_List, Free_Head, Used_Head &
    --#            Response, Used_Head, Item_List from   ID, Item_List, Next_List, Used_Head ;
    --#
    --# pre (ID /= ArraySetDefs.Null_ID);
    --#
    --# post ((Response = ArraySetDefs.DB_Does_Not_Exist -> (for all I in Item_List_Index_Type => (ID /= Item_List(I).ID) )) and
    --# ((for all I in Item_List_Index_Type => (ID /= Item_List(I).ID) ) -> (Response = ArraySetDefs.DB_Does_Not_Exist)) and
    --#   ((Response = ArraySetDefs.DB_Success) -> (for some I in Item_List_Index_Type =>
    --#       (Item_List~(I).ID = ID and 
    --# Item_List(I).ID = ArraySetDefs.Null_ID and Item_List(I).Value = ArraySetDefs.Null_Value and Item_List(I).Value_Length = ArraySetDefs.Null_Value_Length))));
  is
     Curr_Index : Link_Type;
     Prev_Index : Link_Type;
  begin

     Prev_Index := Terminator;
     Curr_Index := Used_Head;

     while Curr_Index /= Terminator and then
       Item_List (Curr_Index).ID /= ID loop

        Prev_Index := Curr_Index;
        Curr_Index := Next_List (Curr_Index);
        --#  assert Curr_Index /= Terminator;
     end loop;

     if Curr_Index /= Terminator then

        -- Not necessary but may help debug - impacts SPARK annotations
        Item_List (Curr_Index).ID := ArraySetDefs.Null_ID;
        Item_List (Curr_Index).Value := ArraySetDefs.Null_Value;
        Item_List (Curr_Index).Value_Length := 0;

        -- Update the used list to remove the deleted entry
        if Prev_Index = Terminator then
           Used_Head := Next_List (Curr_Index);
        else
           Next_List (Prev_Index) := Next_List (Curr_Index);
        end if;

        -- Move deleted entry to the head of the free list
        Next_List (Curr_Index) := Free_Head;
        Free_Head := Curr_Index;

        Response := ArraySetDefs.DB_Success;
     else
        Response := ArraySetDefs.DB_Does_Not_Exist;
     end if;

  end Delete;

  ---------
  -- Init
  ---------

  procedure Init
    --# global out Item_List, Next_List, Free_Head, Used_Head;
    --# derives Item_List, Next_List, Free_Head, Used_Head from ;
  is
  begin

     for I in Item_List_Index_Type loop

        --# accept Flow_Message, 23, Item_List, "undefined array element(s)";
        -- Note: this error has to be accepted because SPARK cannot tell
        -- that all array values are being initialized.  This is something
        -- that the KSU array information flow analysis is designed to
        -- overcome.
        Item_List (I).ID := ArraySetDefs.Null_ID;
        Item_List (I).Value := ArraySetDefs.Null_Value;
        Item_List (I).Value_Length := ArraySetDefs.Null_Value_Length;

        --# end accept;
     end loop;

     for I in Link_Type
       range 0 .. Link_Type'Last - 1 loop

        --# accept Flow_Message, 23, Next_List, "undefined array element(s)";
        Next_List (I) := I + 1;
        --# end accept;
     end loop;

     Next_List (Link_Type'Last-1) := Terminator;
     Free_Head := 0;
     Used_Head := Terminator;
     --# accept Flow_Message, 602, Item_List, Item_List, "undefined array element(s)";
     --# accept Flow_Message, 602, Next_List, Next_List, "undefined array element(s)";
     --  SPARK has a problem with initializing individual entries in
     --  an array or aggregate. Using deferred constants is prohibitive
     --  for runtime as code generated by the compiler is huge.
  end Init;

end ArraySet;
