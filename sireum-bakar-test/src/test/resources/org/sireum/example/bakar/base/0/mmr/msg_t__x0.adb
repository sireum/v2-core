package body Msg_t
is
   procedure Set_Origin(aMsg: in out Msg; O: in Lbl_t.Proc_Id) is
   begin
      aMsg.Origin := O;
   end Set_Origin;

   function Get_Origin(aMsg: in Msg) return Lbl_t.Proc_Id is
   begin
      return aMsg.Origin;
   end Get_Origin;


   procedure Set_Dest(aMsg: in out Msg; D: in Lbl_t.Proc_Id) is
   begin
      aMsg.Dest := D;
   end Set_Dest;

   function Get_Dest(aMsg: in Msg) return Lbl_t.Proc_Id is
   begin
      return aMsg.Dest;
   end Get_Dest;


   procedure Set_Data(aMsg: in out Msg; B: in Data_Range) is
   begin
      aMsg.Data := B;
   end Set_Data;

   function Get_Data(aMsg: in Msg) return Data_Range is
   begin
      return aMsg.Data;
   end Get_Data;

   function Is_Default_Message(AMsg: in Msg) return Boolean
   is
     Z: Boolean := False;
   begin
      if AMsg.Data = 0 and AMsg.Origin = Lbl_t.Proc_Id'First and AMsg.Dest = Lbl_t.Proc_Id'First then
         Z := True;
      end if;
      return Z;
   end Is_Default_Message;
end Msg_t;
