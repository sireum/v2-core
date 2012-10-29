--**********************************************************************
--   Lustre2ada translation of specification: Microwave
--   Version 0.1; Build Date: Mon Aug 03 16:48:01 2009
--   Copyright (C) 2008,2009 Rockwell Collins, Inc.
--   All Rights Reserved.
--**********************************************************************/


with Unsigned, MicrowaveTypes;
--# inherit Signed, Unsigned, MicrowaveTypes;
package Microwave is

   procedure Rlt_Microwave_Init (
      Rlt_Node_State :  in out MicrowaveTypes.Rlt_Microwave_State_Type )
   ;

   procedure MicrowaveExec (
      Kp_Start :  in Boolean ;
      Kp_Clear :  in Boolean ;
      Kp_0 :  in Boolean ;
      Kp_1 :  in Boolean ;
      Kp_2 :  in Boolean ;
      Kp_3 :  in Boolean ;
      Kp_4 :  in Boolean ;
      Kp_5 :  in Boolean ;
      Kp_6 :  in Boolean ;
      Kp_7 :  in Boolean ;
      Kp_8 :  in Boolean ;
      Kp_9 :  in Boolean ;
      Door_Closed :  in Boolean ;
      Rlt_Init_Step :  in Boolean ;
      Rlt_Node_State :  in out MicrowaveTypes.Rlt_Microwave_State_Type ;
      Left_Digit :  out Unsigned.Byte;
      Middle_Digit :  out Unsigned.Byte;
      Right_Digit :  out Unsigned.Byte;
      Mode :  out Unsigned.Byte)
   ;


end Microwave;

--**********************************************************************
--   end of translation of specification: Microwave
--**********************************************************************/
