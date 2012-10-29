--**********************************************************************
--   Lustre2C translation of specification: microwave
--   Version 0.1; Build Date: Tue Oct  7 15:15:58 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/


with microwave_Decls; 


   use type microwave_Decls.rlt_int8;

   use type microwave_Decls.rlt_uint8;

   use type microwave_Decls.rlt_mod8;

   use type microwave_Decls.rlt_int16;

   use type microwave_Decls.rlt_uint16;

   use type microwave_Decls.rlt_mod16;

   use type microwave_Decls.rlt_int32;

   use type microwave_Decls.rlt_uint32;

   use type microwave_Decls.rlt_mod32;
package microwave_Package is 


   procedure mode_logic_setup_enter(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_4_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_4_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_4_inports_clear :  out Boolean ; 
         rlt_state_4_inports_start :  out Boolean ; 
         rlt_state_4_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_4_inports_door_closed :  out Boolean ; 
         rlt_state_4_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_4_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_enter(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_2_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_2_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_2_inports_clear :  out Boolean ; 
         rlt_state_2_inports_start :  out Boolean ; 
         rlt_state_2_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_2_inports_door_closed :  out Boolean ; 
         rlt_state_2_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_setup_exit(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_2_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_2_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_2_inports_clear :  out Boolean ; 
         rlt_state_2_inports_start :  out Boolean ; 
         rlt_state_2_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_2_inports_door_closed :  out Boolean ; 
         rlt_state_2_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_setup_eval(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_1_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_1_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_1_inports_clear :  out Boolean ; 
         rlt_state_1_inports_start :  out Boolean ; 
         rlt_state_1_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_1_inports_door_closed :  out Boolean ; 
         rlt_state_1_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_cooking_enter(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_3_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_3_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_3_inports_clear :  out Boolean ; 
         rlt_state_3_inports_start :  out Boolean ; 
         rlt_state_3_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_3_inports_door_closed :  out Boolean ; 
         rlt_state_3_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_suspended_enter(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_3_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_3_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_3_inports_clear :  out Boolean ; 
         rlt_state_3_inports_start :  out Boolean ; 
         rlt_state_3_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_3_inports_door_closed :  out Boolean ; 
         rlt_state_3_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_junc7_graph_1(
         rlt_init_state_id_current_event :  in microwave_Decls.rlt_int32; 
         rlt_init_state_id_states_root :  in microwave_Decls.rlt_int32; 
         rlt_init_state_id_inports_clear :  in Boolean ; 
         rlt_init_state_id_inports_start :  in Boolean ; 
         rlt_init_state_id_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_init_state_id_inports_door_closed :  in Boolean ; 
         rlt_init_state_id_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_init_state_id_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_at_junc7_input :  in Boolean ; 
         rlt_final_state_id_current_event :  out microwave_Decls.rlt_int32; 
         rlt_final_state_id_states_root :  out microwave_Decls.rlt_int32; 
         rlt_final_state_id_inports_clear :  out Boolean ; 
         rlt_final_state_id_inports_start :  out Boolean ; 
         rlt_final_state_id_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_final_state_id_inports_door_closed :  out Boolean ; 
         rlt_final_state_id_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_final_state_id_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_enter(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_3_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_3_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_3_inports_clear :  out Boolean ; 
         rlt_state_3_inports_start :  out Boolean ; 
         rlt_state_3_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_3_inports_door_closed :  out Boolean ; 
         rlt_state_3_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_suspended_exit(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_2_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_2_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_2_inports_clear :  out Boolean ; 
         rlt_state_2_inports_start :  out Boolean ; 
         rlt_state_2_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_2_inports_door_closed :  out Boolean ; 
         rlt_state_2_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_cooking_exit(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_2_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_2_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_2_inports_clear :  out Boolean ; 
         rlt_state_2_inports_start :  out Boolean ; 
         rlt_state_2_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_2_inports_door_closed :  out Boolean ; 
         rlt_state_2_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_exit(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_4_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_4_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_4_inports_clear :  out Boolean ; 
         rlt_state_4_inports_start :  out Boolean ; 
         rlt_state_4_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_4_inports_door_closed :  out Boolean ; 
         rlt_state_4_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_4_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_suspended_eval(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_1_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_1_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_1_inports_clear :  out Boolean ; 
         rlt_state_1_inports_start :  out Boolean ; 
         rlt_state_1_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_1_inports_door_closed :  out Boolean ; 
         rlt_state_1_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_cooking_eval(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_1_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_1_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_1_inports_clear :  out Boolean ; 
         rlt_state_1_inports_start :  out Boolean ; 
         rlt_state_1_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_1_inports_door_closed :  out Boolean ; 
         rlt_state_1_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_running_eval(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_12_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_12_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_12_inports_clear :  out Boolean ; 
         rlt_state_12_inports_start :  out Boolean ; 
         rlt_state_12_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_12_inports_door_closed :  out Boolean ; 
         rlt_state_12_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_12_outports_steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure mode_logic_eval(
         rlt_state_0_current_event :  in microwave_Decls.rlt_int32; 
         rlt_state_0_states_root :  in microwave_Decls.rlt_int32; 
         rlt_state_0_inports_clear :  in Boolean ; 
         rlt_state_0_inports_start :  in Boolean ; 
         rlt_state_0_inports_steps_to_cook :  in microwave_Decls.rlt_uint16; 
         rlt_state_0_inports_door_closed :  in Boolean ; 
         rlt_state_0_outports_mode :  in microwave_Decls.rlt_uint8; 
         rlt_state_0_outports_steps_remaining :  in microwave_Decls.rlt_uint16; 
         rlt_state_8_current_event :  out microwave_Decls.rlt_int32; 
         rlt_state_8_states_root :  out microwave_Decls.rlt_int32; 
         rlt_state_8_inports_clear :  out Boolean ; 
         rlt_state_8_inports_start :  out Boolean ; 
         rlt_state_8_inports_steps_to_cook :  out microwave_Decls.rlt_uint16; 
         rlt_state_8_inports_door_closed :  out Boolean ; 
         rlt_state_8_outports_mode :  out microwave_Decls.rlt_uint8; 
         rlt_state_8_outports_steps_remaining :  out microwave_Decls.rlt_uint16);


   procedure mode_logic_main(
         start :  in Boolean ; 
         clear :  in Boolean ; 
         steps_to_cook :  in microwave_Decls.rlt_uint16; 
         door_closed :  in Boolean ; 
         wakeup :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_mode_logic_main_State_Type ; 
         mode :  out microwave_Decls.rlt_uint8; 
         steps_remaining :  out microwave_Decls.rlt_uint16);


   procedure mode_logic(
         start :  in Boolean ; 
         clear :  in Boolean ; 
         steps_to_cook :  in microwave_Decls.rlt_uint16; 
         door_closed :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_mode_logic_State_Type ; 
         mode :  out microwave_Decls.rlt_uint8; 
         steps_remaining :  out microwave_Decls.rlt_uint16);

   procedure switch9_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure seconds_to_tens(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16);

   procedure switch1_port0_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port2_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port0_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port2_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure middle_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8);

   procedure switch_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure left_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8);

   procedure switch1_port0_reactis_internal_subsys3(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port2_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch14_port0_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port2_reactis_internal_subsys3(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure right_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8);

   procedure switch_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port2_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure digits_to_time(
         digits_t_0 :  in microwave_Decls.rlt_uint8; 
         digits_t_1 :  in microwave_Decls.rlt_uint8; 
         digits_t_2 :  in microwave_Decls.rlt_uint8; 
         time :  out microwave_Decls.rlt_uint16);

   procedure steps_to_seconds(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16);

   procedure seconds_to_minutes(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16);

   procedure time_on_display(
         steps_remaining :  in microwave_Decls.rlt_uint16; 
         steps_per_second :  in microwave_Decls.rlt_uint16; 
         display_time_t_0 :  out microwave_Decls.rlt_uint8; 
         display_time_t_1 :  out microwave_Decls.rlt_uint8; 
         display_time_t_2 :  out microwave_Decls.rlt_uint8);

   procedure is_mode_setup1(
         mode :  in microwave_Decls.rlt_uint8; 
         is_setup :  out Boolean );

   procedure switch8_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch7_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch1_port0_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch6_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch5_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch4_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch3_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch9_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch2_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);


   procedure le_detector9(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector9_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector8(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector8_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector7(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector7_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector6(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector6_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector5(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector5_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector4(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector4_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector3(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector3_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector2(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector2_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector1(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector1_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector0(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector0_State_Type ; 
         le_detected :  out Boolean );

   procedure switch8_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch7_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch6_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch5_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch4_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch3_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);

   procedure switch2_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8);


   procedure process_keypad_inputs(
         kp_0 :  in Boolean ; 
         kp_1 :  in Boolean ; 
         kp_2 :  in Boolean ; 
         kp_3 :  in Boolean ; 
         kp_4 :  in Boolean ; 
         kp_5 :  in Boolean ; 
         kp_6 :  in Boolean ; 
         kp_7 :  in Boolean ; 
         kp_8 :  in Boolean ; 
         kp_9 :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_process_keypad_inputs_State_Type ; 
         keypad_digit :  out microwave_Decls.rlt_uint8);


   procedure keypad_processing(
         kp_clear :  in Boolean ; 
         kp_0 :  in Boolean ; 
         kp_1 :  in Boolean ; 
         kp_2 :  in Boolean ; 
         kp_3 :  in Boolean ; 
         kp_4 :  in Boolean ; 
         kp_5 :  in Boolean ; 
         kp_6 :  in Boolean ; 
         kp_7 :  in Boolean ; 
         kp_8 :  in Boolean ; 
         kp_9 :  in Boolean ; 
         steps_per_second :  in microwave_Decls.rlt_uint16; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_keypad_processing_State_Type ; 
         steps_to_cook :  out microwave_Decls.rlt_uint16);


   procedure le_detector12(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector12_State_Type ; 
         le_detected :  out Boolean );


   procedure le_detector11(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector11_State_Type ; 
         le_detected :  out Boolean );


   procedure microwave_inner(
         kp_start :  in Boolean ; 
         kp_clear :  in Boolean ; 
         kp_0 :  in Boolean ; 
         kp_1 :  in Boolean ; 
         kp_2 :  in Boolean ; 
         kp_3 :  in Boolean ; 
         kp_4 :  in Boolean ; 
         kp_5 :  in Boolean ; 
         kp_6 :  in Boolean ; 
         kp_7 :  in Boolean ; 
         kp_8 :  in Boolean ; 
         kp_9 :  in Boolean ; 
         door_closed :  in Boolean ; 
         steps_per_second :  in microwave_Decls.rlt_uint16; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_microwave_inner_State_Type ; 
         left_digit1 :  out microwave_Decls.rlt_uint8; 
         middle_digit1 :  out microwave_Decls.rlt_uint8; 
         right_digit1 :  out microwave_Decls.rlt_uint8; 
         mode :  out microwave_Decls.rlt_uint8);


   procedure microwave(
         kp_start :  in Boolean ; 
         kp_clear :  in Boolean ; 
         kp_0 :  in Boolean ; 
         kp_1 :  in Boolean ; 
         kp_2 :  in Boolean ; 
         kp_3 :  in Boolean ; 
         kp_4 :  in Boolean ; 
         kp_5 :  in Boolean ; 
         kp_6 :  in Boolean ; 
         kp_7 :  in Boolean ; 
         kp_8 :  in Boolean ; 
         kp_9 :  in Boolean ; 
         door_closed :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_microwave_State_Type ; 
         left_digit1 :  out microwave_Decls.rlt_uint8; 
         middle_digit1 :  out microwave_Decls.rlt_uint8; 
         right_digit1 :  out microwave_Decls.rlt_uint8; 
         mode :  out microwave_Decls.rlt_uint8);

end microwave_Package; 

--**********************************************************************
--   end of translation of specification: microwave
--**********************************************************************/
