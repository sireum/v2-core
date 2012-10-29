with Msg_t, Lbl_t, Mem_t, Memory, Policy;
--# inherit Msg_t, Lbl_t, Mem_t, Memory, Policy;

package Mmr
  --# own Pointers, Flags;
  --# initializes Pointers, Flags;
is
  type Pointer_Row is array (Lbl_t.Proc_Id) of Lbl_t.Pointer;
  type Pointer_Matrix is array (Lbl_t.Proc_Id) of Pointer_Row;
  Pointers: Pointer_Matrix;
   
  type Flags_Row is array (Lbl_t.Proc_Id) of Boolean;
  type Flags_Matrix is array (Lbl_t.Proc_Id) of Flags_Row;
  Flags: Flags_Matrix := Flags_Matrix'(others =>
                                         Flags_Row'(others => False));
             
  function Invariant return Boolean;
    --# global in Pointers;

 function OutBoxWellFormed(P: in Lbl_t.Proc_Id) return Boolean;
    --# global in Pointers, Flags, Memory.Mem_Space;
 
 function InBoxWellFormed(P: in Lbl_t.Proc_Id) return Boolean;
    --# global in Pointers, Flags, Memory.Mem_Space;
                            
  procedure Route;
    --# global in out Pointers;
    --#        in out Memory.Mem_Space;
    --#        in out Flags;
    --#        in Policy.Policy_Space;
    --# derives Pointers         from Pointers,
    --#                               Flags,
    --#                               Policy.Policy_Space &
    --#         Memory.Mem_Space from Pointers,
    --#                               Memory.Mem_Space,
    --#                               Flags,
    --#                               Policy.Policy_Space &
    --#         Flags            from Flags,
    --#                               Policy.Policy_Space;
    --# --============= Pre-condition ==================
    --# pre Policy.Is_Satisfied(Policy.Policy_Space) and then
    --#     Invariant(Pointers) and then
    --#     (for all P in Lbl_t.Proc_ID => (OutBoxWellFormed(P,Pointers,Flags,Memory.Mem_Space)));
    --# --============= Post-condition ==================
    --# post Invariant(Pointers) and then
    --#      (for all P in Lbl_t.Proc_ID => 
    --#       ((InBoxWellFormed(P,Pointers,Flags,Memory.Mem_Space)) 
    --#         and then 
    --#         (for all Q in Lbl_t.Proc_ID =>
    --#           -- if message is allowed, outbox and flags are set appropriately
    --#           (((Flags~(P)(Q) and Policy.Is_Allowed(P,Q,Policy.Policy_Space))
    --#             -> ((Memory.Mem_Space(Pointers(Q)(P)) = Memory.Mem_Space~(Pointers~(P)(Q)))
    --#                 and 
    --#                 (Flags(Q)(P) = True))) 
    --#            and then
    --#           -- if message is present, but not allowed... 
    --#           ((Flags~(P)(Q) and (not Policy.Is_Allowed(P,Q,Policy.Policy_Space)))
    --#             -> ((Memory.Mem_Space(Pointers(Q)(P)) = Msg_t.Def_Msg)
    --#                 and
    --#                 (Flags(Q)(P) = False))) 
    --#            and then
    --#           -- if message is not present, ...
    --#            ((not Flags~(P)(Q)) 
    --#             -> ((Memory.Mem_Space(Pointers(Q)(P)) = Msg_t.Def_Msg)
    --#                 and
    --#                 (Flags(Q)(P) = False)))))));
  
  -- [JH] need to add OutBoxWellFormed invariant to both pre and post condition
  procedure Send_Msg(M: in Msg_t.Msg);
    --# global in     Pointers;
    --#        in out Flags;
    --#        in out Memory.Mem_Space;
    --# derives Flags            from Flags,
    --#                               M &
    --#         Memory.Mem_Space from M,
    --#                               Memory.Mem_Space,
    --#                               Pointers;
    --# --============= Pre-condition ==================
    --# pre Invariant(Pointers);
   --# --============= Post-condition ==================
    --# post Invariant(Pointers) and then
    --#      (for all P in Lbl_t.Proc_ID => 
    --#      (for all Q in Lbl_t.Proc_ID => 
    --#        (((P = Msg_t.Get_Origin(M) and Q = Msg_t.Get_Dest(M))
    --#          -> (Memory.Mem_Space(Pointers(P)(Q)) = M and Flags(P)(Q) = true))
    --#         and then
    --#        ((P /= Msg_t.Get_Origin(M) or Q /= Msg_t.Get_Dest(M))
    --#          -> (Memory.Mem_Space(Pointers(P)(Q)) = Memory.Mem_Space~(Pointers(P)(Q))
    --#              and Flags(P)(Q) = Flags~(P)(Q))))));

  procedure Read_Msgs(P: in Lbl_t.Proc_Id; A: out Mem_t.Mem_Row);
    --# global in     Pointers;
    --#        in out Flags;
    --#        in out Memory.Mem_Space;
    --# derives Flags            from Flags,
    --#                               P &
    --#         A                from Flags,
    --#                               P,
    --#                               Pointers,
    --#                               Memory.Mem_Space &
    --#         Memory.Mem_Space from P,
    --#                               Pointers,
    --#                               Memory.Mem_Space;
    --# --============= Pre-condition ==================
    --# pre Invariant(Pointers)
    --#  and then InBoxWellFormed(P,Pointers,Flags,Memory.Mem_Space);
    --# --============= Post-condition ==================
    --# post Invariant(Pointers) 
    --#  and then
    --#       -- the return value in A is built correctly
    --#        ((for all Q in Lbl_t.Proc_ID =>
    --#           (    (    Flags~(P)(Q) -> A(Q) = Memory.Mem_Space~(Pointers(P)(Q)))
    --#            and (not Flags~(P)(Q) -> A(Q) = Msg_t.Def_Msg)))
    --#       -- the output state of Flags and Memory is correct
    --#  and then (for all Q in Lbl_t.Proc_ID =>
    --#           (for all R in Lbl_t.Proc_ID =>
    --#           -- the output state of Flags & Memory for P's row (mailbox) is correct
    --#           -- i.e., the values have been cleared out
    --#              (    (Q = P  -> (    (Flags(Q)(R) = FALSE)
    --#                               and then (Memory.Mem_Space(Pointers(Q)(R)) 
    --#                                       = Msg_t.Def_Msg)))
    --#           -- the output state of Flags & Memory other rows remains unchanged
    --#               and ((Q /= P -> ((Flags(Q)(R) = Flags~(Q)(R))
    --#                                and then (Memory.Mem_Space(Pointers(Q)(R)) 
    --#                                      = Memory.Mem_Space~(Pointers(Q)(R))))))))));
end Mmr;
