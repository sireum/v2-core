with Lbl_t, Msg_t;
--# inherit Lbl_t, Msg_t;
package Memory
  --# own Mem_Space;
  --# initializes Mem_Space;
is
  type Mem_Space_T is array (Lbl_t.Pointer) of Msg_t.Msg;
  Mem_Space: Mem_Space_T;
  
  procedure Write( M: in Msg_t.Msg; S: in Lbl_t.Pointer);
    --# global in out Mem_Space;
    --# derives Mem_Space from *,
    --#                        S,
    --#                        M;
    --# --============= Post ===============
    --# post Mem_Space = Mem_Space~[S => M];

  function Read(S: in Lbl_t.Pointer) return Msg_t.Msg;
    --# global in Mem_Space;
    --# return Mem_Space(S);
end Memory;
