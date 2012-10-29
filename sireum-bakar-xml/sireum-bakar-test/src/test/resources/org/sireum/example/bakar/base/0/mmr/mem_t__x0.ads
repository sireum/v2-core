with Lbl_t, Msg_t;
--# inherit Lbl_t, Msg_t;

package Mem_t
is

  type Mem_Row is array (Lbl_t.Proc_Id) of Msg_t.Msg;
  Def_Mem_Row: constant Mem_Row := Mem_Row'(others => Msg_t.Def_Msg);

end Mem_t;
