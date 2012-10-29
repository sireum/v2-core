--**********************************************************************
--   Lustre2ada translation of specification: Microwave
--   Version 0.1; Build Date: Mon Aug 03 16:48:01 2009
--   Copyright (C) 2008,2009 Rockwell Collins, Inc.
--   All Rights Reserved.
--**********************************************************************/

with Signed, Unsigned, MicrowaveTypes;
use type Unsigned.Byte;
use type Unsigned.Word;
-- --# inherit Signed, Unsigned, MicrowaveTypes;
package body Microwave is

   procedure Rlt_Microwave_Init (
      Rlt_Node_State :  in out MicrowaveTypes.Rlt_Microwave_State_Type )

   is
   begin
      Rlt_Node_State.Unit_Delay2 := true;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst_Init := true;

      Rlt_Node_State.Unit_Delay := false;

      Rlt_Node_State.Unit_Delay1 := false;

      Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data := MicrowaveTypes.Microwave_Mode_Logic_Type'(
               States => MicrowaveTypes.Rlt_Record_2'(
                  Running => 0,
                  Microwave_Mode_Logic => 0 ) ,
               Outports => MicrowaveTypes.Rlt_Record_1'(
                  Mode => 0,
                  Steps_Remaining => 0 )  ) ;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Right_Digit := 0;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Middle_Digit := 0;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Left_Digit := 0;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay1 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay9 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay8 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay6 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay7 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay5 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay3 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay4 := false;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay2 := false;
   end Rlt_Microwave_Init;


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

   is
      Sum1 : Unsigned.Word;

      Mux : MicrowaveTypes.Rlt_Array_3_Of_Uint8 ;

      Logical_Operator11 : Boolean ;

      Logical_Operator1 : Boolean ;

      Keypad_Processing_steps_To_Cook : Unsigned.Word;

      Steps_To_Cook : Unsigned.Word;

      Switch13 : Unsigned.Byte;

      Switch : Unsigned.Byte;

      Switch11 : Unsigned.Byte;

      Switch1 : Unsigned.Byte;

      Mux1 : MicrowaveTypes.Rlt_Array_3_Of_Uint8 ;

      Mode1 : Unsigned.Byte;

      Steps_Remaining1 : Unsigned.Word;

   begin
      if (Rlt_Init_Step) then
         Rlt_Node_State.Unit_Delay2 := true;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst_Init := true;

         Rlt_Node_State.Unit_Delay := false;

         Rlt_Node_State.Unit_Delay1 := false;

      else
         null;
      end if ;

      if (Rlt_Node_State.Unit_Delay2) then
         if (Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst_Init) then
            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Right_Digit := 0;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Middle_Digit := 0;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Left_Digit := 0;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay1 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay9 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay8 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay6 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay7 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay5 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay3 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay4 := false;

            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay2 := false;

         else
            null;
         end if ;

         if ((Kp_0 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay))) then
            Switch := 0;
         else
         if ((Kp_1 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay1))) then
            Switch := 1;
         else
         if ((Kp_2 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay2))) then
            Switch := 2;
         else
         if ((Kp_3 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay4))) then
            Switch := 3;
         else
         if ((Kp_4 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay3))) then
            Switch := 4;
         else
         if ((Kp_5 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay5))) then
            Switch := 5;
         else
         if ((Kp_6 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay7))) then
            Switch := 6;
         else
         if ((Kp_7 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay6))) then
            Switch := 7;
         else
         if ((Kp_8 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay8))) then
            Switch := 8;
         else
         if ((Kp_9 and then ( not Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay9))) then
            Switch := 9;
         else
            Switch := 10;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;
         end if ;

         if (Kp_Clear) then
            Switch13 := 0;

            Switch11 := 0;

            Switch1 := 0;

         else
         if ((Switch <= 9)) then
            Switch13 := Switch;

            Switch11 := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Right_Digit;

            Switch1 := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Middle_Digit;

         else
            Switch13 := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Right_Digit;

            Switch11 := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Middle_Digit;

            Switch1 := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Left_Digit;

         end if ;
         end if ;

         Mux1 := MicrowaveTypes.Rlt_Array_3_Of_Uint8'( Switch1,
                  Switch11,
                  Switch13 ) ;

         Steps_To_Cook := (((Unsigned.Word ((Unsigned.Long ((Unsigned.Word (Mux1 (2)))))))+((Unsigned.Word (Mux1 (1))) * 10))+((Unsigned.Word (Mux1 (0))) * 60));

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay2 := Kp_2;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay4 := Kp_3;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay3 := Kp_4;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay5 := Kp_5;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay7 := Kp_6;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay6 := Kp_7;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay8 := Kp_8;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay9 := Kp_9;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay := Kp_0;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay1 := Kp_1;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Left_Digit := Switch1;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Middle_Digit := Switch11;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst.Unit_Delay_Right_Digit := Switch13;

         Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp := Steps_To_Cook;

         Keypad_Processing_steps_To_Cook := Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp;

      else
         if (Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst_Init) then
            Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp := 0;
         else
            null;
         end if ;

         Keypad_Processing_steps_To_Cook := 0;

      end if ;

      Logical_Operator1 := (Kp_Clear and then ( not Rlt_Node_State.Unit_Delay));

      Logical_Operator11 := (Kp_Start and then ( not Rlt_Node_State.Unit_Delay1));

      if (Rlt_Init_Step) then
         Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data := MicrowaveTypes.Microwave_Mode_Logic_Type'(
                  States => MicrowaveTypes.Rlt_Record_2'(
                     Running => 0,
                     Microwave_Mode_Logic => 0 ) ,
                  Outports => MicrowaveTypes.Rlt_Record_1'(
                     Mode => 0,
                     Steps_Remaining => 0 )  ) ;
      else
         null;
      end if ;

      if (true) then
         if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic /= 0)) then
            if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic = 1)) then
               if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining <= 0)) then
                  Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic := 2;

                  Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 1;

                  Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining := Keypad_Processing_steps_To_Cook;

               else
               if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running = 1)) then
                  if ((Logical_Operator1 or else ( not Door_Closed))) then
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 2;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 3;

                  else
                  if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining > 0)) then
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 0;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining := (Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining-1);

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 1;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 2;

                  else
                     null;
                  end if ;
                  end if ;
               else
               if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running = 2)) then
                  if (Logical_Operator1) then
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 0;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic := 0;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic := 2;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 1;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining := Keypad_Processing_steps_To_Cook;

                  else
                  if ((Logical_Operator11 and then Door_Closed)) then
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 1;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 2;

                  else
                     null;
                  end if ;
                  end if ;
               else
                  null;
               end if ;
               end if ;
               end if ;
            else
            if ((Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic = 2)) then
               Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining := Keypad_Processing_steps_To_Cook;

               if ((Logical_Operator11 and then (Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining > 0))) then
                  Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic := 1;

                  if (Door_Closed) then
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 1;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 2;

                  else
                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Running := 2;

                     Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 3;

                  end if ;

               else
                  null;
               end if ;

            else
               null;
            end if ;
            end if ;
         else
            null;

            Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.States.Microwave_Mode_Logic := 2;

            Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode := 1;

            Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining := Keypad_Processing_steps_To_Cook;

         end if ;
      else
         null;
      end if ;

      Mode1 := Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Mode;

      Steps_Remaining1 := Rlt_Node_State.Rlt_Chart_Instance_1.Rlt_Chart_Data.Outports.Steps_Remaining;

      Mode := Mode1;

      Sum1 := ((Unsigned.Word ((Signed.Integer_32 ((Steps_Remaining1 / 1)))))-(((Steps_Remaining1 / 1) / 60) * 60));

      Mux := MicrowaveTypes.Rlt_Array_3_Of_Uint8'( (Unsigned.Byte (((Steps_Remaining1 / 1) / 60))),
               (Unsigned.Byte ((Sum1 / 10))),
               (Unsigned.Byte (((Unsigned.Word ((Signed.Integer_32 (Sum1))))-((Sum1 / 10) * 10)))) ) ;

      Right_Digit := Mux (2);

      Middle_Digit := Mux (1);

      Left_Digit := Mux (0);

      Rlt_Node_State.Unit_Delay1 := Kp_Start;

      Rlt_Node_State.Unit_Delay := Kp_Clear;

      Rlt_Node_State.Keypad_Processing_steps_To_Cook_Temp_Inst_Init := ( not Rlt_Node_State.Unit_Delay2);

      Rlt_Node_State.Unit_Delay2 := (1 = Mode);
   end MicrowaveExec;



end Microwave;

--**********************************************************************
--   end of translation of specification: Microwave
--**********************************************************************/
