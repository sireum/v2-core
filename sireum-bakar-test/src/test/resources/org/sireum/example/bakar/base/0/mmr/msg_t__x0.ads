with Lbl_t;
--# inherit Lbl_t;

package Msg_t
is
   -- 0 represents a null message
   type Data_Range is range 0 .. Integer'Last;
   
   -- type Msg is private;
   --
   -- This type was declared to be private, but that did not allow the contract to
   -- refer to fields of the record directly or using update notation.
   --
   -- After thinking about the issue, we could view the Get-er methods as
   -- "axioms" and give them no contracts.  Then the results of the Set methods would
   -- be stated in terms of the Get-er methods instead of the data structure value itself.
   -- Unfortunately, that would make it difficult to state the frame condition (values of
   -- the remaining fields do not change when setting one particular field).
   --
   type Msg is
      record
         Data: Data_Range;
         Origin: Lbl_t.Proc_Id;
         Dest: Lbl_t.Proc_Id;
      end record;

   Def_Msg: constant Msg;


   procedure Set_Origin(aMsg: in out Msg; O: in Lbl_t.Proc_Id);
   --# derives aMsg from aMsg,
   --#                   O;
   --# --============== Post-condition ==================
   --# post (aMsg = aMsg~[Origin => O]);
   
   function Get_Origin(aMsg: in Msg) return Lbl_t.Proc_Id;
   --# return aMsg.Origin;
   
   procedure Set_Dest(aMsg: in out Msg; D: in Lbl_t.Proc_Id);
   --# derives aMsg from aMsg,
   --#                   D;
   --# --============== Post-condition ==================
   --# post (aMsg = aMsg~[Dest => D]);
   
   function Get_Dest(aMsg: in Msg) return Lbl_t.Proc_Id; 
   --# return aMsg.Dest;
   
   procedure Set_Data(aMsg: in out Msg; B: in Data_Range);
   --# derives aMsg from aMsg,
   --#                   B;
   --# --============== Post-condition ==================
   --# post (aMsg = aMsg~[Data => B]);

   function Get_Data(aMsg: in Msg) return Data_Range;
   --# return aMsg.Data;
   
   function Is_Default_Message(aMsg: in Msg) return Boolean;
   
private
   Def_Msg: constant Msg := Msg'(Data => 0, 
                                 Origin => Lbl_t.Proc_Id'First, 
                                 Dest => Lbl_t.Proc_Id'First);
end Msg_t;

