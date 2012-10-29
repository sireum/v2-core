--**********************************************************************
--   Lustre2C translation of specification: microwave
--   Version 0.1; Build Date: Tue Oct  7 15:15:58 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/

package microwave_Decls is 

   type rlt_int32 is range -(2**31) .. ((2**31) - 1); for rlt_int32'size use 32;
   type rlt_uint32 is range 0 .. ((2**32) - 1); for rlt_uint32'size use 32;
   type rlt_mod32 is mod (2**32); for rlt_mod32'size use 32;
   type rlt_int16 is range -(2**15) .. ((2**15) - 1); for rlt_int16'size use 16;
   type rlt_uint16 is range 0 .. ((2**16) - 1); for rlt_uint16'size use 16;
   type rlt_mod16 is mod (2**16); for rlt_mod16'size use 16;
   type rlt_int8 is range -(2**7) .. ((2**7) - 1); for rlt_int8'size use 8;
   type rlt_uint8 is range 0 .. ((2**8) - 1); for rlt_uint8'size use 8;
   type rlt_mod8 is mod (2**8); for rlt_mod8'size use 8;

   type rlt_mode_logic_main_State_Type is 
   record 
      rlt_pre1 : Boolean ; 
      rlt_pre : Boolean ; 
      rlt_pre4 : rlt_uint16; 
      rlt_pre3 : rlt_uint8; 
      rlt_pre2_states_root : rlt_int32; 
      rlt_pre2_inports_steps_to_cook : rlt_uint16; 
      rlt_pre2_inports_start : Boolean ; 
      rlt_pre2_inports_door_closed : Boolean ; 
      rlt_pre2_current_event : rlt_int32; 
      rlt_pre2_inports_clear : Boolean ; 
   end record ; 

   type rlt_mode_logic_State_Type is 
   record 
      anon_eq : rlt_mode_logic_main_State_Type ; 
   end record ; 

   type rlt_le_detector9_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector8_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector7_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector6_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector5_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector4_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector3_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector2_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector1_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector0_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_process_keypad_inputs_State_Type is 
   record 
      le_detector9_le_detected_inst : rlt_le_detector9_State_Type ; 
      le_detector8_le_detected_inst : rlt_le_detector8_State_Type ; 
      le_detector7_le_detected_inst : rlt_le_detector7_State_Type ; 
      le_detector6_le_detected_inst : rlt_le_detector6_State_Type ; 
      le_detector5_le_detected_inst : rlt_le_detector5_State_Type ; 
      le_detector4_le_detected_inst : rlt_le_detector4_State_Type ; 
      le_detector3_le_detected_inst : rlt_le_detector3_State_Type ; 
      le_detector2_le_detected_inst : rlt_le_detector2_State_Type ; 
      le_detector1_le_detected_inst : rlt_le_detector1_State_Type ; 
      le_detector0_le_detected_inst : rlt_le_detector0_State_Type ; 
   end record ; 

   type rlt_keypad_processing_State_Type is 
   record 
      unit_delay_right_digit : rlt_uint8; 
      unit_delay_middle_digit : rlt_uint8; 
      unit_delay_left_digit : rlt_uint8; 
      process_keypad_inputs_keypad_digit_inst : rlt_process_keypad_inputs_State_Type ; 
   end record ; 

   type rlt_le_detector12_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_le_detector11_State_Type is 
   record 
      unit_delay : Boolean ; 
   end record ; 

   type rlt_microwave_inner_State_Type is 
   record 
      is_mode_setup1_is_setup : Boolean ; 
      keypad_processing_steps_to_cook : rlt_uint16; 
      le_detector1_le_detected : Boolean ; 
      le_detector12_le_detected : Boolean ; 
      time_on_display_display_time_t_0 : rlt_uint8; 
      time_on_display_display_time_t_1 : rlt_uint8; 
      time_on_display_display_time_t_2 : rlt_uint8; 
      mode_logic_mode : rlt_uint8; 
      mode_logic_steps_remaining : rlt_uint16; 
      unit_delay2 : Boolean ; 
      keypad_processing_steps_to_cook_temp_inst_init_step : Boolean ; 
      anon_eq1 : rlt_mode_logic_State_Type ; 
      le_detector1_le_detected_inst : rlt_le_detector11_State_Type ; 
      le_detector12_le_detected_inst : rlt_le_detector12_State_Type ; 
      keypad_processing_steps_to_cook_temp_inst : rlt_keypad_processing_State_Type ; 
   end record ; 

   type rlt_microwave_State_Type is 
   record 
      anon_eq : rlt_microwave_inner_State_Type ; 
   end record ; 

end microwave_Decls; 
--**********************************************************************
--   end of translation of specification: microwave
--**********************************************************************/
