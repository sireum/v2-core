package body Memory
is
  procedure Write(M: in Msg_t.Msg; S: in Lbl_t.Pointer) is
  begin
    Mem_Space(S) := M;
  end Write;

  function Read(S: in Lbl_t.Pointer) return Msg_t.Msg is
  begin
    return Mem_Space(S);
  end Read;

begin
  Mem_Space := Mem_Space_T'(others => Msg_t.Def_Msg);
end Memory;
