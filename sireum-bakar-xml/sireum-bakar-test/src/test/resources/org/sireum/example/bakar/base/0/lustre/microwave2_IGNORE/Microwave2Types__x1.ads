--**********************************************************************
--   Lustre2ada translation of specification: Microwave
--   Version 0.1; Build Date: Mon Aug 03 16:48:01 2009
--   Copyright (C) 2008,2009 Rockwell Collins, Inc.
--   All Rights Reserved.
--**********************************************************************/


with Unsigned;
--# inherit Unsigned;
package MicrowaveTypes is

   type Rlt_Record_2 is
   record
      Running : Unsigned.Byte;
      Microwave_Mode_Logic : Unsigned.Byte;
   end record ;

   type Rlt_Record_1 is
   record
      Mode : Unsigned.Byte;
      Steps_Remaining : Unsigned.Word;
   end record ;

   type Microwave_Mode_Logic_Type is
   record
      Outports : Rlt_Record_1 ;
      States : Rlt_Record_2 ;
   end record ;

   type Main_State_Type is
   record
      Rlt_Chart_Data : Microwave_Mode_Logic_Type ;
   end record ;

   type Keypad_Processing_State_Type is
   record
      Unit_Delay_Right_Digit : Unsigned.Byte;
      Unit_Delay_Middle_Digit : Unsigned.Byte;
      Unit_Delay_Left_Digit : Unsigned.Byte;
      Unit_Delay1 : Boolean ;
      Unit_Delay : Boolean ;
      Unit_Delay9 : Boolean ;
      Unit_Delay8 : Boolean ;
      Unit_Delay6 : Boolean ;
      Unit_Delay7 : Boolean ;
      Unit_Delay5 : Boolean ;
      Unit_Delay3 : Boolean ;
      Unit_Delay4 : Boolean ;
      Unit_Delay2 : Boolean ;
   end record ;

   type Rlt_Microwave_State_Type is
   record
      Unit_Delay2 : Boolean ;
      Keypad_Processing_steps_To_Cook_Temp : Unsigned.Word;
      Keypad_Processing_steps_To_Cook_Temp_Inst_Init : Boolean ;
      Unit_Delay : Boolean ;
      Unit_Delay1 : Boolean ;
      Rlt_Chart_Instance_1 : Main_State_Type ;
      Keypad_Processing_steps_To_Cook_Temp_Inst : Keypad_Processing_State_Type ;
   end record ;

   subtype Rlt_Array_Index_3 is Unsigned.Word range 0 .. 2;

   type Rlt_Array_3_Of_Uint8 is array (Rlt_Array_Index_3 ) of Unsigned.Byte;

end MicrowaveTypes;

--**********************************************************************
--   end of translation of specification: Microwave
--**********************************************************************/
