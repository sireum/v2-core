--**********************************************************************
--   Lustre2C translation of specification: microwave
--   Version 0.1; Build Date: Tue Oct  7 15:15:58 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/


package body microwave_Package is 


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
         rlt_state_4_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      if ((rlt_state_0_states_root /= 4)) then 
               rlt_state_1_states_root := 4; 

               rlt_state_2_outports_mode := 1; 

               rlt_state_3_outports_steps_remaining := rlt_state_0_inports_steps_to_cook; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

               rlt_state_3_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 

      end if ; 

      rlt_state_4_current_event := rlt_state_0_current_event; 

      rlt_state_4_states_root := rlt_state_1_states_root; 

      rlt_state_4_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_4_inports_start := rlt_state_0_inports_start; 

      rlt_state_4_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_4_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_4_outports_mode := rlt_state_2_outports_mode; 

      rlt_state_4_outports_steps_remaining := rlt_state_3_outports_steps_remaining; 
   end mode_logic_setup_enter;


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
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_check_entry_state_consistency_0 : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_clear : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_start : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      mode_logic_setup_enter(rlt_state_0_current_event, 
         rlt_state_0_states_root, 
         rlt_state_0_inports_clear, 
         rlt_state_0_inports_start, 
         rlt_state_0_inports_steps_to_cook, 
         rlt_state_0_inports_door_closed, 
         rlt_state_0_outports_mode, 
         rlt_state_0_outports_steps_remaining, 
         rlt_mode_logic_setup_enter_rlt_state_4_current_event, 
         rlt_mode_logic_setup_enter_rlt_state_4_states_root, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_clear, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_start, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_mode, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining) ; 

      rlt_check_entry_state_consistency_0 := (((rlt_mode_logic_setup_enter_rlt_state_4_states_root >= 1) and 
            (rlt_mode_logic_setup_enter_rlt_state_4_states_root <= 3)) or 
            (rlt_mode_logic_setup_enter_rlt_state_4_states_root = 4)); 

      rlt_state_2_current_event := rlt_mode_logic_setup_enter_rlt_state_4_current_event; 

      rlt_state_2_states_root := rlt_mode_logic_setup_enter_rlt_state_4_states_root; 

      rlt_state_2_inports_clear := rlt_mode_logic_setup_enter_rlt_state_4_inports_clear; 

      rlt_state_2_inports_start := rlt_mode_logic_setup_enter_rlt_state_4_inports_start; 

      rlt_state_2_inports_steps_to_cook := rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook; 

      rlt_state_2_inports_door_closed := rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed; 

      rlt_state_2_outports_mode := rlt_mode_logic_setup_enter_rlt_state_4_outports_mode; 

      rlt_state_2_outports_steps_remaining := rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining; 
   end mode_logic_enter;


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
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 

   begin
      if ((rlt_state_0_states_root = 4)) then 
            rlt_state_1_states_root := 0; 
      else 
            rlt_state_1_states_root := rlt_state_0_states_root; 
      end if ; 

      rlt_state_2_current_event := rlt_state_0_current_event; 

      rlt_state_2_states_root := rlt_state_1_states_root; 

      rlt_state_2_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_2_inports_start := rlt_state_0_inports_start; 

      rlt_state_2_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_2_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_2_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_setup_exit;


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
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

   begin
      rlt_state_1_current_event := rlt_state_0_current_event; 

      rlt_state_1_states_root := rlt_state_0_states_root; 

      rlt_state_1_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_1_inports_start := rlt_state_0_inports_start; 

      rlt_state_1_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_1_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_1_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_setup_eval;


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
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 

   begin
      if ((rlt_state_0_states_root /= 2)) then 
               rlt_state_1_states_root := 2; 

               rlt_state_2_outports_mode := 2; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

      end if ; 

      rlt_state_3_current_event := rlt_state_0_current_event; 

      rlt_state_3_states_root := rlt_state_1_states_root; 

      rlt_state_3_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_3_inports_start := rlt_state_0_inports_start; 

      rlt_state_3_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_3_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_3_outports_mode := rlt_state_2_outports_mode; 

      rlt_state_3_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_cooking_enter;


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
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 

   begin
      if ((rlt_state_0_states_root /= 3)) then 
               rlt_state_1_states_root := 3; 

               rlt_state_2_outports_mode := 3; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

      end if ; 

      rlt_state_3_current_event := rlt_state_0_current_event; 

      rlt_state_3_states_root := rlt_state_1_states_root; 

      rlt_state_3_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_3_inports_start := rlt_state_0_inports_start; 

      rlt_state_3_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_3_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_3_outports_mode := rlt_state_2_outports_mode; 

      rlt_state_3_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_suspended_enter;


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
         rlt_final_state_id_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_trans16_fired : Boolean ; 
      rlt_trans15_fired : Boolean ; 
      rlt_max_recursion_depth_exceeded_0 : Boolean ; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear : Boolean ; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start : Boolean ; 
      logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      de_logic_running_suspended_enter_rlt_state_3_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      gic_running_suspended_enter_rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start : Boolean ; 
      de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_enter_rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      rlt_max_recursion_depth_exceeded_0 := true; 

      rlt_trans15_fired := (rlt_at_junc7_input and 
            rlt_init_state_id_inports_door_closed); 

      mode_logic_running_suspended_enter(rlt_init_state_id_current_event, 
         rlt_init_state_id_states_root, 
         rlt_init_state_id_inports_clear, 
         rlt_init_state_id_inports_start, 
         rlt_init_state_id_inports_steps_to_cook, 
         rlt_init_state_id_inports_door_closed, 
         rlt_init_state_id_outports_mode, 
         rlt_init_state_id_outports_steps_remaining, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start, 
         logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook, 
         de_logic_running_suspended_enter_rlt_state_3_inports_door_closed, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode, 
         gic_running_suspended_enter_rlt_state_3_outports_steps_remaining) ; 

      rlt_trans16_fired := (rlt_at_junc7_input and 
            ( not rlt_trans15_fired)); 

      mode_logic_running_cooking_enter(rlt_init_state_id_current_event, 
         rlt_init_state_id_states_root, 
         rlt_init_state_id_inports_clear, 
         rlt_init_state_id_inports_start, 
         rlt_init_state_id_inports_steps_to_cook, 
         rlt_init_state_id_inports_door_closed, 
         rlt_init_state_id_outports_mode, 
         rlt_init_state_id_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start, 
         de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook, 
         mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode, 
         logic_running_cooking_enter_rlt_state_3_outports_steps_remaining) ; 

      if (rlt_trans15_fired) then 
               rlt_final_state_id_outports_mode := rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode; 

               rlt_final_state_id_inports_door_closed := mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed; 

               rlt_final_state_id_current_event := rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event; 

               rlt_final_state_id_inports_clear := rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear; 

               rlt_final_state_id_states_root := rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root; 

               rlt_final_state_id_inports_steps_to_cook := de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook; 

               rlt_final_state_id_inports_start := rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start; 

               rlt_final_state_id_outports_steps_remaining := logic_running_cooking_enter_rlt_state_3_outports_steps_remaining; 

      else 
               if (rlt_trans16_fired) then 
                        rlt_final_state_id_outports_mode := rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode; 

                        rlt_final_state_id_inports_door_closed := de_logic_running_suspended_enter_rlt_state_3_inports_door_closed; 

                        rlt_final_state_id_current_event := rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event; 

                        rlt_final_state_id_inports_clear := rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear; 

                        rlt_final_state_id_states_root := rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root; 

                        rlt_final_state_id_inports_steps_to_cook := logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook; 

                        rlt_final_state_id_inports_start := rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start; 

                        rlt_final_state_id_outports_steps_remaining := gic_running_suspended_enter_rlt_state_3_outports_steps_remaining; 

               else 
                        rlt_final_state_id_outports_mode := rlt_init_state_id_outports_mode; 

                        rlt_final_state_id_inports_door_closed := rlt_init_state_id_inports_door_closed; 

                        rlt_final_state_id_current_event := rlt_init_state_id_current_event; 

                        rlt_final_state_id_inports_clear := rlt_init_state_id_inports_clear; 

                        rlt_final_state_id_states_root := rlt_init_state_id_states_root; 

                        rlt_final_state_id_inports_steps_to_cook := rlt_init_state_id_inports_steps_to_cook; 

                        rlt_final_state_id_inports_start := rlt_init_state_id_inports_start; 

                        rlt_final_state_id_outports_steps_remaining := rlt_init_state_id_outports_steps_remaining; 

               end if ; 

      end if ; 
   end mode_logic_running_junc7_graph_1;


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
         rlt_state_3_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_inports_clear : Boolean ; 
      rlt_state_2_inports_start : Boolean ; 
      rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_2_inports_door_closed : Boolean ; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_complete_0 : Boolean ; 
      rlt_check_entry_state_consistency_0 : Boolean ; 
      ode_logic_running_junc7_graph_1_rlt_final_state_id_current_event : microwave_Decls.rlt_int32; 
      mode_logic_running_junc7_graph_1_rlt_final_state_id_states_root : microwave_Decls.rlt_int32; 
      ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_clear : Boolean ; 
      ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_start : Boolean ; 
      c_running_junc7_graph_1_rlt_final_state_id_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      gic_running_junc7_graph_1_rlt_final_state_id_inports_door_closed : Boolean ; 
      ode_logic_running_junc7_graph_1_rlt_final_state_id_outports_mode : microwave_Decls.rlt_uint8; 
      unning_junc7_graph_1_rlt_final_state_id_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      mode_logic_running_junc7_graph_1(rlt_state_0_current_event, 
         rlt_state_1_states_root, 
         rlt_state_0_inports_clear, 
         rlt_state_0_inports_start, 
         rlt_state_0_inports_steps_to_cook, 
         rlt_state_0_inports_door_closed, 
         rlt_state_0_outports_mode, 
         rlt_state_0_outports_steps_remaining, 
         ( not ((rlt_state_0_states_root >= 1) and 
         (rlt_state_0_states_root <= 3))), 
         ode_logic_running_junc7_graph_1_rlt_final_state_id_current_event, 
         mode_logic_running_junc7_graph_1_rlt_final_state_id_states_root, 
         ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_clear, 
         ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_start, 
         c_running_junc7_graph_1_rlt_final_state_id_inports_steps_to_cook, 
         gic_running_junc7_graph_1_rlt_final_state_id_inports_door_closed, 
         ode_logic_running_junc7_graph_1_rlt_final_state_id_outports_mode, 
         unning_junc7_graph_1_rlt_final_state_id_outports_steps_remaining) ; 

      if (( not ((rlt_state_0_states_root >= 1) and 
         (rlt_state_0_states_root <= 3)))) then 
               rlt_state_1_states_root := 1; 

               rlt_state_2_current_event := ode_logic_running_junc7_graph_1_rlt_final_state_id_current_event; 

               rlt_state_2_states_root := mode_logic_running_junc7_graph_1_rlt_final_state_id_states_root; 

               rlt_state_2_inports_clear := ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_clear; 

               rlt_state_2_inports_start := ode_logic_running_junc7_graph_1_rlt_final_state_id_inports_start; 

               rlt_state_2_inports_steps_to_cook := c_running_junc7_graph_1_rlt_final_state_id_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := gic_running_junc7_graph_1_rlt_final_state_id_inports_door_closed; 

               rlt_state_2_outports_mode := ode_logic_running_junc7_graph_1_rlt_final_state_id_outports_mode; 

               rlt_state_2_outports_steps_remaining := unning_junc7_graph_1_rlt_final_state_id_outports_steps_remaining; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_2_current_event := rlt_state_0_current_event; 

               rlt_state_2_states_root := rlt_state_1_states_root; 

               rlt_state_2_inports_clear := rlt_state_0_inports_clear; 

               rlt_state_2_inports_start := rlt_state_0_inports_start; 

               rlt_state_2_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := rlt_state_0_inports_door_closed; 

               rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

               rlt_state_2_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 

      end if ; 

      rlt_complete_0 := ( not ((rlt_state_0_states_root >= 1) and 
            (rlt_state_0_states_root <= 3))); 

      rlt_check_entry_state_consistency_0 := ((rlt_state_2_states_root = 2) or 
            (rlt_state_2_states_root = 3)); 

      rlt_state_3_current_event := rlt_state_2_current_event; 

      rlt_state_3_states_root := rlt_state_2_states_root; 

      rlt_state_3_inports_clear := rlt_state_2_inports_clear; 

      rlt_state_3_inports_start := rlt_state_2_inports_start; 

      rlt_state_3_inports_steps_to_cook := rlt_state_2_inports_steps_to_cook; 

      rlt_state_3_inports_door_closed := rlt_state_2_inports_door_closed; 

      rlt_state_3_outports_mode := rlt_state_2_outports_mode; 

      rlt_state_3_outports_steps_remaining := rlt_state_2_outports_steps_remaining; 
   end mode_logic_running_enter;


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
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 

   begin
      if ((rlt_state_0_states_root = 3)) then 
            rlt_state_1_states_root := 1; 
      else 
            rlt_state_1_states_root := rlt_state_0_states_root; 
      end if ; 

      rlt_state_2_current_event := rlt_state_0_current_event; 

      rlt_state_2_states_root := rlt_state_1_states_root; 

      rlt_state_2_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_2_inports_start := rlt_state_0_inports_start; 

      rlt_state_2_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_2_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_2_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_suspended_exit;


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
         rlt_state_2_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_states_root : microwave_Decls.rlt_int32; 

   begin
      if ((rlt_state_0_states_root = 2)) then 
            rlt_state_1_states_root := 1; 
      else 
            rlt_state_1_states_root := rlt_state_0_states_root; 
      end if ; 

      rlt_state_2_current_event := rlt_state_0_current_event; 

      rlt_state_2_states_root := rlt_state_1_states_root; 

      rlt_state_2_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_2_inports_start := rlt_state_0_inports_start; 

      rlt_state_2_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_2_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_2_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_cooking_exit;


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
         rlt_state_4_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_1_inports_clear : Boolean ; 
      rlt_state_1_inports_start : Boolean ; 
      rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_1_inports_door_closed : Boolean ; 
      rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_inports_clear : Boolean ; 
      rlt_state_2_inports_start : Boolean ; 
      rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_2_inports_door_closed : Boolean ; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start : Boolean ; 
      e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start : Boolean ; 
      ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_exit_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      mode_logic_running_cooking_exit(rlt_state_0_current_event, 
         rlt_state_0_states_root, 
         rlt_state_0_inports_clear, 
         rlt_state_0_inports_start, 
         rlt_state_0_inports_steps_to_cook, 
         rlt_state_0_inports_door_closed, 
         rlt_state_0_outports_mode, 
         rlt_state_0_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start, 
         ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook, 
         mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode, 
         logic_running_cooking_exit_rlt_state_2_outports_steps_remaining) ; 

      if (((rlt_state_0_states_root >= 1) and 
         (rlt_state_0_states_root <= 3))) then 
               rlt_state_1_states_root := rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root; 

               rlt_state_1_inports_start := rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start; 

               rlt_state_1_inports_door_closed := mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed; 

               rlt_state_1_outports_steps_remaining := logic_running_cooking_exit_rlt_state_2_outports_steps_remaining; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_1_inports_start := rlt_state_0_inports_start; 

               rlt_state_1_inports_door_closed := rlt_state_0_inports_door_closed; 

               rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 

      end if ; 

      mode_logic_running_suspended_exit(rlt_state_1_current_event, 
         rlt_state_1_states_root, 
         rlt_state_1_inports_clear, 
         rlt_state_1_inports_start, 
         rlt_state_1_inports_steps_to_cook, 
         rlt_state_1_inports_door_closed, 
         rlt_state_1_outports_mode, 
         rlt_state_1_outports_steps_remaining, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start, 
         e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook, 
         ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode, 
         ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining) ; 

      if (((rlt_state_0_states_root >= 1) and 
         (rlt_state_0_states_root <= 3))) then 
               rlt_state_1_current_event := rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event; 

               rlt_state_1_inports_clear := rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear; 

               rlt_state_1_inports_steps_to_cook := ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook; 

               rlt_state_1_outports_mode := rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode; 

               rlt_state_2_current_event := rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event; 

               rlt_state_2_states_root := rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root; 

               rlt_state_2_inports_clear := rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear; 

               rlt_state_2_inports_start := rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start; 

               rlt_state_2_inports_steps_to_cook := e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed; 

               rlt_state_2_outports_mode := rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode; 

               rlt_state_2_outports_steps_remaining := ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining; 

               rlt_state_3_states_root := 0; 

      else 
               rlt_state_1_current_event := rlt_state_0_current_event; 

               rlt_state_1_inports_clear := rlt_state_0_inports_clear; 

               rlt_state_1_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

               rlt_state_1_outports_mode := rlt_state_0_outports_mode; 

               rlt_state_2_current_event := rlt_state_1_current_event; 

               rlt_state_2_states_root := rlt_state_1_states_root; 

               rlt_state_2_inports_clear := rlt_state_1_inports_clear; 

               rlt_state_2_inports_start := rlt_state_1_inports_start; 

               rlt_state_2_inports_steps_to_cook := rlt_state_1_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := rlt_state_1_inports_door_closed; 

               rlt_state_2_outports_mode := rlt_state_1_outports_mode; 

               rlt_state_2_outports_steps_remaining := rlt_state_1_outports_steps_remaining; 

               rlt_state_3_states_root := rlt_state_2_states_root; 

      end if ; 

      rlt_state_4_current_event := rlt_state_2_current_event; 

      rlt_state_4_states_root := rlt_state_3_states_root; 

      rlt_state_4_inports_clear := rlt_state_2_inports_clear; 

      rlt_state_4_inports_start := rlt_state_2_inports_start; 

      rlt_state_4_inports_steps_to_cook := rlt_state_2_inports_steps_to_cook; 

      rlt_state_4_inports_door_closed := rlt_state_2_inports_door_closed; 

      rlt_state_4_outports_mode := rlt_state_2_outports_mode; 

      rlt_state_4_outports_steps_remaining := rlt_state_2_outports_steps_remaining; 
   end mode_logic_running_exit;


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
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

   begin
      rlt_state_1_current_event := rlt_state_0_current_event; 

      rlt_state_1_states_root := rlt_state_0_states_root; 

      rlt_state_1_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_1_inports_start := rlt_state_0_inports_start; 

      rlt_state_1_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_1_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_1_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_suspended_eval;


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
         rlt_state_1_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

   begin
      rlt_state_1_current_event := rlt_state_0_current_event; 

      rlt_state_1_states_root := rlt_state_0_states_root; 

      rlt_state_1_inports_clear := rlt_state_0_inports_clear; 

      rlt_state_1_inports_start := rlt_state_0_inports_start; 

      rlt_state_1_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

      rlt_state_1_inports_door_closed := rlt_state_0_inports_door_closed; 

      rlt_state_1_outports_mode := rlt_state_0_outports_mode; 

      rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
   end mode_logic_running_cooking_eval;


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
         rlt_state_12_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_fired_0 : Boolean ; 
      rlt_fired_1 : Boolean ; 
      rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_1_inports_clear : Boolean ; 
      rlt_state_1_inports_start : Boolean ; 
      rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_1_inports_door_closed : Boolean ; 
      rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_inports_clear : Boolean ; 
      rlt_state_2_inports_start : Boolean ; 
      rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_2_inports_door_closed : Boolean ; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_fired_2 : Boolean ; 
      rlt_complete_1 : Boolean ; 
      rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_state_3_inports_clear : Boolean ; 
      rlt_state_3_inports_start : Boolean ; 
      rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_3_inports_door_closed : Boolean ; 
      rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_state_4_inports_clear : Boolean ; 
      rlt_state_4_inports_start : Boolean ; 
      rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_4_inports_door_closed : Boolean ; 
      rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_fired_3 : Boolean ; 
      rlt_complete_2 : Boolean ; 
      rlt_state_5_current_event : microwave_Decls.rlt_int32; 
      rlt_state_5_states_root : microwave_Decls.rlt_int32; 
      rlt_state_5_inports_clear : Boolean ; 
      rlt_state_5_inports_start : Boolean ; 
      rlt_state_5_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_5_inports_door_closed : Boolean ; 
      rlt_state_5_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_5_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_6_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_7_current_event : microwave_Decls.rlt_int32; 
      rlt_state_7_states_root : microwave_Decls.rlt_int32; 
      rlt_state_7_inports_clear : Boolean ; 
      rlt_state_7_inports_start : Boolean ; 
      rlt_state_7_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_7_inports_door_closed : Boolean ; 
      rlt_state_7_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_7_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_fired_4 : Boolean ; 
      rlt_complete_3 : Boolean ; 
      rlt_state_8_current_event : microwave_Decls.rlt_int32; 
      rlt_state_8_states_root : microwave_Decls.rlt_int32; 
      rlt_state_8_inports_clear : Boolean ; 
      rlt_state_8_inports_start : Boolean ; 
      rlt_state_8_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_8_inports_door_closed : Boolean ; 
      rlt_state_8_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_8_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_9_current_event : microwave_Decls.rlt_int32; 
      rlt_state_9_states_root : microwave_Decls.rlt_int32; 
      rlt_state_9_inports_clear : Boolean ; 
      rlt_state_9_inports_start : Boolean ; 
      rlt_state_9_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_9_inports_door_closed : Boolean ; 
      rlt_state_9_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_9_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_10_current_event : microwave_Decls.rlt_int32; 
      rlt_state_10_states_root : microwave_Decls.rlt_int32; 
      rlt_state_10_inports_clear : Boolean ; 
      rlt_state_10_inports_start : Boolean ; 
      rlt_state_10_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_10_inports_door_closed : Boolean ; 
      rlt_state_10_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_10_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_11_current_event : microwave_Decls.rlt_int32; 
      rlt_state_11_states_root : microwave_Decls.rlt_int32; 
      rlt_state_11_inports_clear : Boolean ; 
      rlt_state_11_inports_start : Boolean ; 
      rlt_state_11_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_11_inports_door_closed : Boolean ; 
      rlt_state_11_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_11_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_suspended_eval_rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_eval_rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_clear : Boolean ; 
      rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_start : Boolean ; 
      e_logic_running_suspended_eval_rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      ode_logic_running_suspended_eval_rlt_state_1_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_suspended_eval_rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      ogic_running_suspended_eval_rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_eval_rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_eval_rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_start : Boolean ; 
      ode_logic_running_cooking_eval_rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_eval_rlt_state_1_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_eval_rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_eval_rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_enter_rlt_state_31_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_31_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_start : Boolean ; 
      e_logic_running_cooking_enter_rlt_state_31_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      ode_logic_running_cooking_enter_rlt_state_31_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_31_outports_mode : microwave_Decls.rlt_uint8; 
      ogic_running_cooking_enter_rlt_state_31_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start : Boolean ; 
      e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start : Boolean ; 
      de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_enter_rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_exit_rlt_state_21_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_21_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_start : Boolean ; 
      de_logic_running_cooking_exit_rlt_state_21_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_exit_rlt_state_21_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_21_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_exit_rlt_state_21_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear : Boolean ; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start : Boolean ; 
      logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      de_logic_running_suspended_enter_rlt_state_3_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      gic_running_suspended_enter_rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start : Boolean ; 
      ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      logic_running_cooking_exit_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_enter_rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_clear : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_start : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_exit_rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_exit_rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_clear : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_start : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      rlt_fired_0 := ((rlt_state_0_states_root = 3) and 
            rlt_state_0_inports_clear); 

      rlt_fired_1 := (rlt_fired_0 and 
            (rlt_state_0_states_root = 3)); 

      mode_logic_running_exit(rlt_state_0_current_event, 
         rlt_state_0_states_root, 
         rlt_state_0_inports_clear, 
         rlt_state_0_inports_start, 
         rlt_state_0_inports_steps_to_cook, 
         rlt_state_0_inports_door_closed, 
         rlt_state_0_outports_mode, 
         rlt_state_0_outports_steps_remaining, 
         rlt_mode_logic_running_exit_rlt_state_4_current_event, 
         rlt_mode_logic_running_exit_rlt_state_4_states_root, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_clear, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_start, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed, 
         rlt_mode_logic_running_exit_rlt_state_4_outports_mode, 
         rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining) ; 

      if (rlt_fired_1) then 
               rlt_state_1_states_root := rlt_mode_logic_running_exit_rlt_state_4_states_root; 

               rlt_state_1_inports_start := rlt_mode_logic_running_exit_rlt_state_4_inports_start; 

               rlt_state_1_inports_door_closed := rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed; 

               rlt_state_1_outports_steps_remaining := rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining; 

      else 
               rlt_state_1_states_root := rlt_state_0_states_root; 

               rlt_state_1_inports_start := rlt_state_0_inports_start; 

               rlt_state_1_inports_door_closed := rlt_state_0_inports_door_closed; 

               rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 

      end if ; 

      mode_logic_setup_enter(rlt_state_1_current_event, 
         rlt_state_1_states_root, 
         rlt_state_1_inports_clear, 
         rlt_state_1_inports_start, 
         rlt_state_1_inports_steps_to_cook, 
         rlt_state_1_inports_door_closed, 
         rlt_state_1_outports_mode, 
         rlt_state_1_outports_steps_remaining, 
         rlt_mode_logic_setup_enter_rlt_state_4_current_event, 
         rlt_mode_logic_setup_enter_rlt_state_4_states_root, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_clear, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_start, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_mode, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining) ; 

      if (rlt_fired_1) then 
               rlt_state_1_current_event := rlt_mode_logic_running_exit_rlt_state_4_current_event; 

               rlt_state_1_inports_clear := rlt_mode_logic_running_exit_rlt_state_4_inports_clear; 

               rlt_state_1_inports_steps_to_cook := rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook; 

               rlt_state_1_outports_mode := rlt_mode_logic_running_exit_rlt_state_4_outports_mode; 

               rlt_state_2_current_event := rlt_mode_logic_setup_enter_rlt_state_4_current_event; 

               rlt_state_2_states_root := rlt_mode_logic_setup_enter_rlt_state_4_states_root; 

               rlt_state_2_inports_clear := rlt_mode_logic_setup_enter_rlt_state_4_inports_clear; 

               rlt_state_2_inports_start := rlt_mode_logic_setup_enter_rlt_state_4_inports_start; 

               rlt_state_2_inports_steps_to_cook := rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed; 

               rlt_state_2_outports_mode := rlt_mode_logic_setup_enter_rlt_state_4_outports_mode; 

               rlt_state_2_outports_steps_remaining := rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining; 

      else 
               rlt_state_1_current_event := rlt_state_0_current_event; 

               rlt_state_1_inports_clear := rlt_state_0_inports_clear; 

               rlt_state_1_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

               rlt_state_1_outports_mode := rlt_state_0_outports_mode; 

               rlt_state_2_current_event := rlt_state_1_current_event; 

               rlt_state_2_states_root := rlt_state_1_states_root; 

               rlt_state_2_inports_clear := rlt_state_1_inports_clear; 

               rlt_state_2_inports_start := rlt_state_1_inports_start; 

               rlt_state_2_inports_steps_to_cook := rlt_state_1_inports_steps_to_cook; 

               rlt_state_2_inports_door_closed := rlt_state_1_inports_door_closed; 

               rlt_state_2_outports_mode := rlt_state_1_outports_mode; 

               rlt_state_2_outports_steps_remaining := rlt_state_1_outports_steps_remaining; 

      end if ; 

      rlt_fired_2 := ((rlt_state_2_states_root = 2) and 
            ((rlt_state_2_inports_clear or 
            ( not rlt_state_2_inports_door_closed)) and 
            ( not rlt_fired_1))); 

      rlt_complete_1 := (rlt_fired_2 or 
            rlt_fired_1); 

      mode_logic_running_cooking_exit(rlt_state_2_current_event, 
         rlt_state_2_states_root, 
         rlt_state_2_inports_clear, 
         rlt_state_2_inports_start, 
         rlt_state_2_inports_steps_to_cook, 
         rlt_state_2_inports_door_closed, 
         rlt_state_2_outports_mode, 
         rlt_state_2_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start, 
         ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook, 
         mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode, 
         logic_running_cooking_exit_rlt_state_2_outports_steps_remaining) ; 

      if (rlt_fired_2) then 
               rlt_state_3_states_root := rlt_mode_logic_running_cooking_exit_rlt_state_2_states_root; 

               rlt_state_3_inports_start := rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_start; 

               rlt_state_3_inports_door_closed := mode_logic_running_cooking_exit_rlt_state_2_inports_door_closed; 

               rlt_state_3_outports_steps_remaining := logic_running_cooking_exit_rlt_state_2_outports_steps_remaining; 

      else 
               rlt_state_3_states_root := rlt_state_2_states_root; 

               rlt_state_3_inports_start := rlt_state_2_inports_start; 

               rlt_state_3_inports_door_closed := rlt_state_2_inports_door_closed; 

               rlt_state_3_outports_steps_remaining := rlt_state_2_outports_steps_remaining; 

      end if ; 

      mode_logic_running_suspended_enter(rlt_state_3_current_event, 
         rlt_state_3_states_root, 
         rlt_state_3_inports_clear, 
         rlt_state_3_inports_start, 
         rlt_state_3_inports_steps_to_cook, 
         rlt_state_3_inports_door_closed, 
         rlt_state_3_outports_mode, 
         rlt_state_3_outports_steps_remaining, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start, 
         logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook, 
         de_logic_running_suspended_enter_rlt_state_3_inports_door_closed, 
         rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode, 
         gic_running_suspended_enter_rlt_state_3_outports_steps_remaining) ; 

      if (rlt_fired_2) then 
               rlt_state_3_current_event := rlt_mode_logic_running_cooking_exit_rlt_state_2_current_event; 

               rlt_state_3_inports_clear := rlt_mode_logic_running_cooking_exit_rlt_state_2_inports_clear; 

               rlt_state_3_inports_steps_to_cook := ode_logic_running_cooking_exit_rlt_state_2_inports_steps_to_cook; 

               rlt_state_3_outports_mode := rlt_mode_logic_running_cooking_exit_rlt_state_2_outports_mode; 

               rlt_state_4_current_event := rlt_mode_logic_running_suspended_enter_rlt_state_3_current_event; 

               rlt_state_4_states_root := rlt_mode_logic_running_suspended_enter_rlt_state_3_states_root; 

               rlt_state_4_inports_clear := rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_clear; 

               rlt_state_4_inports_start := rlt_mode_logic_running_suspended_enter_rlt_state_3_inports_start; 

               rlt_state_4_inports_steps_to_cook := logic_running_suspended_enter_rlt_state_3_inports_steps_to_cook; 

               rlt_state_4_inports_door_closed := de_logic_running_suspended_enter_rlt_state_3_inports_door_closed; 

               rlt_state_4_outports_mode := rlt_mode_logic_running_suspended_enter_rlt_state_3_outports_mode; 

               rlt_state_4_outports_steps_remaining := gic_running_suspended_enter_rlt_state_3_outports_steps_remaining; 

      else 
               rlt_state_3_current_event := rlt_state_2_current_event; 

               rlt_state_3_inports_clear := rlt_state_2_inports_clear; 

               rlt_state_3_inports_steps_to_cook := rlt_state_2_inports_steps_to_cook; 

               rlt_state_3_outports_mode := rlt_state_2_outports_mode; 

               rlt_state_4_current_event := rlt_state_3_current_event; 

               rlt_state_4_states_root := rlt_state_3_states_root; 

               rlt_state_4_inports_clear := rlt_state_3_inports_clear; 

               rlt_state_4_inports_start := rlt_state_3_inports_start; 

               rlt_state_4_inports_steps_to_cook := rlt_state_3_inports_steps_to_cook; 

               rlt_state_4_inports_door_closed := rlt_state_3_inports_door_closed; 

               rlt_state_4_outports_mode := rlt_state_3_outports_mode; 

               rlt_state_4_outports_steps_remaining := rlt_state_3_outports_steps_remaining; 

      end if ; 

      rlt_fired_3 := ((rlt_state_4_outports_steps_remaining > 0) and 
            ((rlt_state_4_states_root = 2) and 
            ( not rlt_complete_1))); 

      rlt_complete_2 := (rlt_fired_3 or 
            rlt_complete_1); 

      mode_logic_running_cooking_exit(rlt_state_4_current_event, 
         rlt_state_4_states_root, 
         rlt_state_4_inports_clear, 
         rlt_state_4_inports_start, 
         rlt_state_4_inports_steps_to_cook, 
         rlt_state_4_inports_door_closed, 
         rlt_state_4_outports_mode, 
         rlt_state_4_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_exit_rlt_state_21_current_event, 
         rlt_mode_logic_running_cooking_exit_rlt_state_21_states_root, 
         rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_clear, 
         rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_start, 
         de_logic_running_cooking_exit_rlt_state_21_inports_steps_to_cook, 
         mode_logic_running_cooking_exit_rlt_state_21_inports_door_closed, 
         rlt_mode_logic_running_cooking_exit_rlt_state_21_outports_mode, 
         logic_running_cooking_exit_rlt_state_21_outports_steps_remaining) ; 

      if (rlt_fired_3) then 
               rlt_state_5_states_root := rlt_mode_logic_running_cooking_exit_rlt_state_21_states_root; 

               rlt_state_5_inports_start := rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_start; 

               rlt_state_5_inports_door_closed := mode_logic_running_cooking_exit_rlt_state_21_inports_door_closed; 

               rlt_state_5_outports_steps_remaining := logic_running_cooking_exit_rlt_state_21_outports_steps_remaining; 

               rlt_state_6_outports_steps_remaining := (rlt_state_5_outports_steps_remaining-1); 

      else 
               rlt_state_5_states_root := rlt_state_4_states_root; 

               rlt_state_5_inports_start := rlt_state_4_inports_start; 

               rlt_state_5_inports_door_closed := rlt_state_4_inports_door_closed; 

               rlt_state_5_outports_steps_remaining := rlt_state_4_outports_steps_remaining; 

               rlt_state_6_outports_steps_remaining := rlt_state_5_outports_steps_remaining; 

      end if ; 

      mode_logic_running_cooking_enter(rlt_state_5_current_event, 
         rlt_state_5_states_root, 
         rlt_state_5_inports_clear, 
         rlt_state_5_inports_start, 
         rlt_state_5_inports_steps_to_cook, 
         rlt_state_5_inports_door_closed, 
         rlt_state_5_outports_mode, 
         rlt_state_6_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start, 
         de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook, 
         mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed, 
         rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode, 
         logic_running_cooking_enter_rlt_state_3_outports_steps_remaining) ; 

      if (rlt_fired_3) then 
               rlt_state_5_current_event := rlt_mode_logic_running_cooking_exit_rlt_state_21_current_event; 

               rlt_state_5_inports_clear := rlt_mode_logic_running_cooking_exit_rlt_state_21_inports_clear; 

               rlt_state_5_inports_steps_to_cook := de_logic_running_cooking_exit_rlt_state_21_inports_steps_to_cook; 

               rlt_state_5_outports_mode := rlt_mode_logic_running_cooking_exit_rlt_state_21_outports_mode; 

               rlt_state_7_current_event := rlt_mode_logic_running_cooking_enter_rlt_state_3_current_event; 

               rlt_state_7_states_root := rlt_mode_logic_running_cooking_enter_rlt_state_3_states_root; 

               rlt_state_7_inports_clear := rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_clear; 

               rlt_state_7_inports_start := rlt_mode_logic_running_cooking_enter_rlt_state_3_inports_start; 

               rlt_state_7_inports_steps_to_cook := de_logic_running_cooking_enter_rlt_state_3_inports_steps_to_cook; 

               rlt_state_7_inports_door_closed := mode_logic_running_cooking_enter_rlt_state_3_inports_door_closed; 

               rlt_state_7_outports_mode := rlt_mode_logic_running_cooking_enter_rlt_state_3_outports_mode; 

               rlt_state_7_outports_steps_remaining := logic_running_cooking_enter_rlt_state_3_outports_steps_remaining; 

      else 
               rlt_state_5_current_event := rlt_state_4_current_event; 

               rlt_state_5_inports_clear := rlt_state_4_inports_clear; 

               rlt_state_5_inports_steps_to_cook := rlt_state_4_inports_steps_to_cook; 

               rlt_state_5_outports_mode := rlt_state_4_outports_mode; 

               rlt_state_7_current_event := rlt_state_5_current_event; 

               rlt_state_7_states_root := rlt_state_5_states_root; 

               rlt_state_7_inports_clear := rlt_state_5_inports_clear; 

               rlt_state_7_inports_start := rlt_state_5_inports_start; 

               rlt_state_7_inports_steps_to_cook := rlt_state_5_inports_steps_to_cook; 

               rlt_state_7_inports_door_closed := rlt_state_5_inports_door_closed; 

               rlt_state_7_outports_mode := rlt_state_5_outports_mode; 

               rlt_state_7_outports_steps_remaining := rlt_state_6_outports_steps_remaining; 

      end if ; 

      rlt_fired_4 := ((rlt_state_7_states_root = 3) and 
            ((rlt_state_7_inports_start and 
            rlt_state_7_inports_door_closed) and 
            ( not rlt_complete_2))); 

      rlt_complete_3 := (rlt_fired_4 or 
            rlt_complete_2); 

      mode_logic_running_suspended_exit(rlt_state_7_current_event, 
         rlt_state_7_states_root, 
         rlt_state_7_inports_clear, 
         rlt_state_7_inports_start, 
         rlt_state_7_inports_steps_to_cook, 
         rlt_state_7_inports_door_closed, 
         rlt_state_7_outports_mode, 
         rlt_state_7_outports_steps_remaining, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start, 
         e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook, 
         ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode, 
         ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining) ; 

      if (rlt_fired_4) then 
               rlt_state_8_states_root := rlt_mode_logic_running_suspended_exit_rlt_state_2_states_root; 

               rlt_state_8_inports_start := rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_start; 

               rlt_state_8_inports_door_closed := ode_logic_running_suspended_exit_rlt_state_2_inports_door_closed; 

               rlt_state_8_outports_steps_remaining := ogic_running_suspended_exit_rlt_state_2_outports_steps_remaining; 

      else 
               rlt_state_8_states_root := rlt_state_7_states_root; 

               rlt_state_8_inports_start := rlt_state_7_inports_start; 

               rlt_state_8_inports_door_closed := rlt_state_7_inports_door_closed; 

               rlt_state_8_outports_steps_remaining := rlt_state_7_outports_steps_remaining; 

      end if ; 

      mode_logic_running_cooking_enter(rlt_state_8_current_event, 
         rlt_state_8_states_root, 
         rlt_state_8_inports_clear, 
         rlt_state_8_inports_start, 
         rlt_state_8_inports_steps_to_cook, 
         rlt_state_8_inports_door_closed, 
         rlt_state_8_outports_mode, 
         rlt_state_8_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_enter_rlt_state_31_current_event, 
         rlt_mode_logic_running_cooking_enter_rlt_state_31_states_root, 
         rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_clear, 
         rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_start, 
         e_logic_running_cooking_enter_rlt_state_31_inports_steps_to_cook, 
         ode_logic_running_cooking_enter_rlt_state_31_inports_door_closed, 
         rlt_mode_logic_running_cooking_enter_rlt_state_31_outports_mode, 
         ogic_running_cooking_enter_rlt_state_31_outports_steps_remaining) ; 

      if (rlt_fired_4) then 
               rlt_state_8_current_event := rlt_mode_logic_running_suspended_exit_rlt_state_2_current_event; 

               rlt_state_8_inports_clear := rlt_mode_logic_running_suspended_exit_rlt_state_2_inports_clear; 

               rlt_state_8_inports_steps_to_cook := e_logic_running_suspended_exit_rlt_state_2_inports_steps_to_cook; 

               rlt_state_8_outports_mode := rlt_mode_logic_running_suspended_exit_rlt_state_2_outports_mode; 

               rlt_state_9_current_event := rlt_mode_logic_running_cooking_enter_rlt_state_31_current_event; 

               rlt_state_9_states_root := rlt_mode_logic_running_cooking_enter_rlt_state_31_states_root; 

               rlt_state_9_inports_clear := rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_clear; 

               rlt_state_9_inports_start := rlt_mode_logic_running_cooking_enter_rlt_state_31_inports_start; 

               rlt_state_9_inports_steps_to_cook := e_logic_running_cooking_enter_rlt_state_31_inports_steps_to_cook; 

               rlt_state_9_inports_door_closed := ode_logic_running_cooking_enter_rlt_state_31_inports_door_closed; 

               rlt_state_9_outports_mode := rlt_mode_logic_running_cooking_enter_rlt_state_31_outports_mode; 

               rlt_state_9_outports_steps_remaining := ogic_running_cooking_enter_rlt_state_31_outports_steps_remaining; 

      else 
               rlt_state_8_current_event := rlt_state_7_current_event; 

               rlt_state_8_inports_clear := rlt_state_7_inports_clear; 

               rlt_state_8_inports_steps_to_cook := rlt_state_7_inports_steps_to_cook; 

               rlt_state_8_outports_mode := rlt_state_7_outports_mode; 

               rlt_state_9_current_event := rlt_state_8_current_event; 

               rlt_state_9_states_root := rlt_state_8_states_root; 

               rlt_state_9_inports_clear := rlt_state_8_inports_clear; 

               rlt_state_9_inports_start := rlt_state_8_inports_start; 

               rlt_state_9_inports_steps_to_cook := rlt_state_8_inports_steps_to_cook; 

               rlt_state_9_inports_door_closed := rlt_state_8_inports_door_closed; 

               rlt_state_9_outports_mode := rlt_state_8_outports_mode; 

               rlt_state_9_outports_steps_remaining := rlt_state_8_outports_steps_remaining; 

      end if ; 

      mode_logic_running_cooking_eval(rlt_state_9_current_event, 
         rlt_state_9_states_root, 
         rlt_state_9_inports_clear, 
         rlt_state_9_inports_start, 
         rlt_state_9_inports_steps_to_cook, 
         rlt_state_9_inports_door_closed, 
         rlt_state_9_outports_mode, 
         rlt_state_9_outports_steps_remaining, 
         rlt_mode_logic_running_cooking_eval_rlt_state_1_current_event, 
         rlt_mode_logic_running_cooking_eval_rlt_state_1_states_root, 
         rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_clear, 
         rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_start, 
         ode_logic_running_cooking_eval_rlt_state_1_inports_steps_to_cook, 
         mode_logic_running_cooking_eval_rlt_state_1_inports_door_closed, 
         rlt_mode_logic_running_cooking_eval_rlt_state_1_outports_mode, 
         logic_running_cooking_eval_rlt_state_1_outports_steps_remaining) ; 

      if ((( not rlt_complete_3) and 
         (rlt_state_9_states_root = 2))) then 
               rlt_state_10_current_event := rlt_mode_logic_running_cooking_eval_rlt_state_1_current_event; 

               rlt_state_10_states_root := rlt_mode_logic_running_cooking_eval_rlt_state_1_states_root; 

               rlt_state_10_inports_clear := rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_clear; 

               rlt_state_10_inports_start := rlt_mode_logic_running_cooking_eval_rlt_state_1_inports_start; 

               rlt_state_10_inports_steps_to_cook := ode_logic_running_cooking_eval_rlt_state_1_inports_steps_to_cook; 

               rlt_state_10_inports_door_closed := mode_logic_running_cooking_eval_rlt_state_1_inports_door_closed; 

               rlt_state_10_outports_mode := rlt_mode_logic_running_cooking_eval_rlt_state_1_outports_mode; 

               rlt_state_10_outports_steps_remaining := logic_running_cooking_eval_rlt_state_1_outports_steps_remaining; 

      else 
               rlt_state_10_current_event := rlt_state_9_current_event; 

               rlt_state_10_states_root := rlt_state_9_states_root; 

               rlt_state_10_inports_clear := rlt_state_9_inports_clear; 

               rlt_state_10_inports_start := rlt_state_9_inports_start; 

               rlt_state_10_inports_steps_to_cook := rlt_state_9_inports_steps_to_cook; 

               rlt_state_10_inports_door_closed := rlt_state_9_inports_door_closed; 

               rlt_state_10_outports_mode := rlt_state_9_outports_mode; 

               rlt_state_10_outports_steps_remaining := rlt_state_9_outports_steps_remaining; 

      end if ; 

      mode_logic_running_suspended_eval(rlt_state_10_current_event, 
         rlt_state_10_states_root, 
         rlt_state_10_inports_clear, 
         rlt_state_10_inports_start, 
         rlt_state_10_inports_steps_to_cook, 
         rlt_state_10_inports_door_closed, 
         rlt_state_10_outports_mode, 
         rlt_state_10_outports_steps_remaining, 
         rlt_mode_logic_running_suspended_eval_rlt_state_1_current_event, 
         rlt_mode_logic_running_suspended_eval_rlt_state_1_states_root, 
         rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_clear, 
         rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_start, 
         e_logic_running_suspended_eval_rlt_state_1_inports_steps_to_cook, 
         ode_logic_running_suspended_eval_rlt_state_1_inports_door_closed, 
         rlt_mode_logic_running_suspended_eval_rlt_state_1_outports_mode, 
         ogic_running_suspended_eval_rlt_state_1_outports_steps_remaining) ; 

      if ((( not rlt_complete_3) and 
         (rlt_state_9_states_root = 3))) then 
               rlt_state_11_current_event := rlt_mode_logic_running_suspended_eval_rlt_state_1_current_event; 

               rlt_state_11_states_root := rlt_mode_logic_running_suspended_eval_rlt_state_1_states_root; 

               rlt_state_11_inports_clear := rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_clear; 

               rlt_state_11_inports_start := rlt_mode_logic_running_suspended_eval_rlt_state_1_inports_start; 

               rlt_state_11_inports_steps_to_cook := e_logic_running_suspended_eval_rlt_state_1_inports_steps_to_cook; 

               rlt_state_11_inports_door_closed := ode_logic_running_suspended_eval_rlt_state_1_inports_door_closed; 

               rlt_state_11_outports_mode := rlt_mode_logic_running_suspended_eval_rlt_state_1_outports_mode; 

               rlt_state_11_outports_steps_remaining := ogic_running_suspended_eval_rlt_state_1_outports_steps_remaining; 

      else 
               rlt_state_11_current_event := rlt_state_10_current_event; 

               rlt_state_11_states_root := rlt_state_10_states_root; 

               rlt_state_11_inports_clear := rlt_state_10_inports_clear; 

               rlt_state_11_inports_start := rlt_state_10_inports_start; 

               rlt_state_11_inports_steps_to_cook := rlt_state_10_inports_steps_to_cook; 

               rlt_state_11_inports_door_closed := rlt_state_10_inports_door_closed; 

               rlt_state_11_outports_mode := rlt_state_10_outports_mode; 

               rlt_state_11_outports_steps_remaining := rlt_state_10_outports_steps_remaining; 

      end if ; 

      rlt_state_12_current_event := rlt_state_11_current_event; 

      rlt_state_12_states_root := rlt_state_11_states_root; 

      rlt_state_12_inports_clear := rlt_state_11_inports_clear; 

      rlt_state_12_inports_start := rlt_state_11_inports_start; 

      rlt_state_12_inports_steps_to_cook := rlt_state_11_inports_steps_to_cook; 

      rlt_state_12_inports_door_closed := rlt_state_11_inports_door_closed; 

      rlt_state_12_outports_mode := rlt_state_11_outports_mode; 

      rlt_state_12_outports_steps_remaining := rlt_state_11_outports_steps_remaining; 
   end mode_logic_running_eval;


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
         rlt_state_8_outports_steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_fired_0 : Boolean ; 
      rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_fired_1 : Boolean ; 
      rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_state_2_inports_clear : Boolean ; 
      rlt_state_2_inports_start : Boolean ; 
      rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_2_inports_door_closed : Boolean ; 
      rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_state_3_inports_clear : Boolean ; 
      rlt_state_3_inports_start : Boolean ; 
      rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_3_inports_door_closed : Boolean ; 
      rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_fired_2 : Boolean ; 
      rlt_fired_3 : Boolean ; 
      rlt_complete_1 : Boolean ; 
      rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_state_4_inports_clear : Boolean ; 
      rlt_state_4_inports_start : Boolean ; 
      rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_4_inports_door_closed : Boolean ; 
      rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_5_current_event : microwave_Decls.rlt_int32; 
      rlt_state_5_states_root : microwave_Decls.rlt_int32; 
      rlt_state_5_inports_clear : Boolean ; 
      rlt_state_5_inports_start : Boolean ; 
      rlt_state_5_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_5_inports_door_closed : Boolean ; 
      rlt_state_5_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_5_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_6_current_event : microwave_Decls.rlt_int32; 
      rlt_state_6_states_root : microwave_Decls.rlt_int32; 
      rlt_state_6_inports_clear : Boolean ; 
      rlt_state_6_inports_start : Boolean ; 
      rlt_state_6_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_6_inports_door_closed : Boolean ; 
      rlt_state_6_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_6_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_7_current_event : microwave_Decls.rlt_int32; 
      rlt_state_7_states_root : microwave_Decls.rlt_int32; 
      rlt_state_7_inports_clear : Boolean ; 
      rlt_state_7_inports_start : Boolean ; 
      rlt_state_7_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_7_inports_door_closed : Boolean ; 
      rlt_state_7_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_7_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_eval_rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_eval_rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_eval_rlt_state_1_inports_clear : Boolean ; 
      rlt_mode_logic_setup_eval_rlt_state_1_inports_start : Boolean ; 
      rlt_mode_logic_setup_eval_rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_eval_rlt_state_1_inports_door_closed : Boolean ; 
      rlt_mode_logic_setup_eval_rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_setup_eval_rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_eval_rlt_state_12_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_eval_rlt_state_12_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_eval_rlt_state_12_inports_clear : Boolean ; 
      rlt_mode_logic_running_eval_rlt_state_12_inports_start : Boolean ; 
      rlt_mode_logic_running_eval_rlt_state_12_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_eval_rlt_state_12_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_eval_rlt_state_12_outports_mode : microwave_Decls.rlt_uint8; 
      lt_mode_logic_running_eval_rlt_state_12_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_enter_rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_clear : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_start : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed : Boolean ; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_exit_rlt_state_4_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_exit_rlt_state_4_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_clear : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_start : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_exit_rlt_state_4_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_enter_rlt_state_3_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_enter_rlt_state_3_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_running_enter_rlt_state_3_inports_clear : Boolean ; 
      rlt_mode_logic_running_enter_rlt_state_3_inports_start : Boolean ; 
      rlt_mode_logic_running_enter_rlt_state_3_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_running_enter_rlt_state_3_inports_door_closed : Boolean ; 
      rlt_mode_logic_running_enter_rlt_state_3_outports_mode : microwave_Decls.rlt_uint8; 
      lt_mode_logic_running_enter_rlt_state_3_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_exit_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_exit_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_setup_exit_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_setup_exit_rlt_state_2_inports_start : Boolean ; 
      rlt_mode_logic_setup_exit_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_setup_exit_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_setup_exit_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_setup_exit_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      rlt_fired_0 := (rlt_state_0_states_root = 4); 

      if (rlt_fired_0) then 
            rlt_state_1_outports_steps_remaining := rlt_state_0_inports_steps_to_cook; 
      else 
            rlt_state_1_outports_steps_remaining := rlt_state_0_outports_steps_remaining; 
      end if ; 

      rlt_fired_1 := ((rlt_state_0_inports_start and 
            (rlt_state_1_outports_steps_remaining > 0)) and 
            (rlt_fired_0 and 
            (rlt_state_0_states_root = 4))); 

      mode_logic_setup_exit(rlt_state_0_current_event, 
         rlt_state_0_states_root, 
         rlt_state_0_inports_clear, 
         rlt_state_0_inports_start, 
         rlt_state_0_inports_steps_to_cook, 
         rlt_state_0_inports_door_closed, 
         rlt_state_0_outports_mode, 
         rlt_state_1_outports_steps_remaining, 
         rlt_mode_logic_setup_exit_rlt_state_2_current_event, 
         rlt_mode_logic_setup_exit_rlt_state_2_states_root, 
         rlt_mode_logic_setup_exit_rlt_state_2_inports_clear, 
         rlt_mode_logic_setup_exit_rlt_state_2_inports_start, 
         rlt_mode_logic_setup_exit_rlt_state_2_inports_steps_to_cook, 
         rlt_mode_logic_setup_exit_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_setup_exit_rlt_state_2_outports_mode, 
         rlt_mode_logic_setup_exit_rlt_state_2_outports_steps_remaining) ; 

      if (rlt_fired_1) then 
               rlt_state_2_states_root := rlt_mode_logic_setup_exit_rlt_state_2_states_root; 

               rlt_state_2_inports_start := rlt_mode_logic_setup_exit_rlt_state_2_inports_start; 

               rlt_state_2_inports_door_closed := rlt_mode_logic_setup_exit_rlt_state_2_inports_door_closed; 

               rlt_state_2_outports_steps_remaining := rlt_mode_logic_setup_exit_rlt_state_2_outports_steps_remaining; 

      else 
               rlt_state_2_states_root := rlt_state_0_states_root; 

               rlt_state_2_inports_start := rlt_state_0_inports_start; 

               rlt_state_2_inports_door_closed := rlt_state_0_inports_door_closed; 

               rlt_state_2_outports_steps_remaining := rlt_state_1_outports_steps_remaining; 

      end if ; 

      mode_logic_running_enter(rlt_state_2_current_event, 
         rlt_state_2_states_root, 
         rlt_state_2_inports_clear, 
         rlt_state_2_inports_start, 
         rlt_state_2_inports_steps_to_cook, 
         rlt_state_2_inports_door_closed, 
         rlt_state_2_outports_mode, 
         rlt_state_2_outports_steps_remaining, 
         rlt_mode_logic_running_enter_rlt_state_3_current_event, 
         rlt_mode_logic_running_enter_rlt_state_3_states_root, 
         rlt_mode_logic_running_enter_rlt_state_3_inports_clear, 
         rlt_mode_logic_running_enter_rlt_state_3_inports_start, 
         rlt_mode_logic_running_enter_rlt_state_3_inports_steps_to_cook, 
         rlt_mode_logic_running_enter_rlt_state_3_inports_door_closed, 
         rlt_mode_logic_running_enter_rlt_state_3_outports_mode, 
         lt_mode_logic_running_enter_rlt_state_3_outports_steps_remaining) ; 

      if (rlt_fired_1) then 
               rlt_state_2_current_event := rlt_mode_logic_setup_exit_rlt_state_2_current_event; 

               rlt_state_2_inports_clear := rlt_mode_logic_setup_exit_rlt_state_2_inports_clear; 

               rlt_state_2_inports_steps_to_cook := rlt_mode_logic_setup_exit_rlt_state_2_inports_steps_to_cook; 

               rlt_state_2_outports_mode := rlt_mode_logic_setup_exit_rlt_state_2_outports_mode; 

               rlt_state_3_current_event := rlt_mode_logic_running_enter_rlt_state_3_current_event; 

               rlt_state_3_states_root := rlt_mode_logic_running_enter_rlt_state_3_states_root; 

               rlt_state_3_inports_clear := rlt_mode_logic_running_enter_rlt_state_3_inports_clear; 

               rlt_state_3_inports_start := rlt_mode_logic_running_enter_rlt_state_3_inports_start; 

               rlt_state_3_inports_steps_to_cook := rlt_mode_logic_running_enter_rlt_state_3_inports_steps_to_cook; 

               rlt_state_3_inports_door_closed := rlt_mode_logic_running_enter_rlt_state_3_inports_door_closed; 

               rlt_state_3_outports_mode := rlt_mode_logic_running_enter_rlt_state_3_outports_mode; 

               rlt_state_3_outports_steps_remaining := lt_mode_logic_running_enter_rlt_state_3_outports_steps_remaining; 

      else 
               rlt_state_2_current_event := rlt_state_0_current_event; 

               rlt_state_2_inports_clear := rlt_state_0_inports_clear; 

               rlt_state_2_inports_steps_to_cook := rlt_state_0_inports_steps_to_cook; 

               rlt_state_2_outports_mode := rlt_state_0_outports_mode; 

               rlt_state_3_current_event := rlt_state_2_current_event; 

               rlt_state_3_states_root := rlt_state_2_states_root; 

               rlt_state_3_inports_clear := rlt_state_2_inports_clear; 

               rlt_state_3_inports_start := rlt_state_2_inports_start; 

               rlt_state_3_inports_steps_to_cook := rlt_state_2_inports_steps_to_cook; 

               rlt_state_3_inports_door_closed := rlt_state_2_inports_door_closed; 

               rlt_state_3_outports_mode := rlt_state_2_outports_mode; 

               rlt_state_3_outports_steps_remaining := rlt_state_2_outports_steps_remaining; 

      end if ; 

      rlt_fired_2 := ((rlt_state_3_outports_steps_remaining <= 0) and 
            (((rlt_state_3_states_root >= 1) and 
            (rlt_state_3_states_root <= 3)) and 
            ( not rlt_fired_1))); 

      rlt_fired_3 := (rlt_fired_2 and 
            (((rlt_state_3_states_root >= 1) and 
            (rlt_state_3_states_root <= 3)) and 
            ( not rlt_fired_1))); 

      rlt_complete_1 := (rlt_fired_3 or 
            rlt_fired_1); 

      mode_logic_running_exit(rlt_state_3_current_event, 
         rlt_state_3_states_root, 
         rlt_state_3_inports_clear, 
         rlt_state_3_inports_start, 
         rlt_state_3_inports_steps_to_cook, 
         rlt_state_3_inports_door_closed, 
         rlt_state_3_outports_mode, 
         rlt_state_3_outports_steps_remaining, 
         rlt_mode_logic_running_exit_rlt_state_4_current_event, 
         rlt_mode_logic_running_exit_rlt_state_4_states_root, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_clear, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_start, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook, 
         rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed, 
         rlt_mode_logic_running_exit_rlt_state_4_outports_mode, 
         rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining) ; 

      if (rlt_fired_3) then 
               rlt_state_4_states_root := rlt_mode_logic_running_exit_rlt_state_4_states_root; 

               rlt_state_4_inports_start := rlt_mode_logic_running_exit_rlt_state_4_inports_start; 

               rlt_state_4_inports_door_closed := rlt_mode_logic_running_exit_rlt_state_4_inports_door_closed; 

               rlt_state_4_outports_steps_remaining := rlt_mode_logic_running_exit_rlt_state_4_outports_steps_remaining; 

      else 
               rlt_state_4_states_root := rlt_state_3_states_root; 

               rlt_state_4_inports_start := rlt_state_3_inports_start; 

               rlt_state_4_inports_door_closed := rlt_state_3_inports_door_closed; 

               rlt_state_4_outports_steps_remaining := rlt_state_3_outports_steps_remaining; 

      end if ; 

      mode_logic_setup_enter(rlt_state_4_current_event, 
         rlt_state_4_states_root, 
         rlt_state_4_inports_clear, 
         rlt_state_4_inports_start, 
         rlt_state_4_inports_steps_to_cook, 
         rlt_state_4_inports_door_closed, 
         rlt_state_4_outports_mode, 
         rlt_state_4_outports_steps_remaining, 
         rlt_mode_logic_setup_enter_rlt_state_4_current_event, 
         rlt_mode_logic_setup_enter_rlt_state_4_states_root, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_clear, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_start, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook, 
         rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_mode, 
         rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining) ; 

      if (rlt_fired_3) then 
               rlt_state_4_current_event := rlt_mode_logic_running_exit_rlt_state_4_current_event; 

               rlt_state_4_inports_clear := rlt_mode_logic_running_exit_rlt_state_4_inports_clear; 

               rlt_state_4_inports_steps_to_cook := rlt_mode_logic_running_exit_rlt_state_4_inports_steps_to_cook; 

               rlt_state_4_outports_mode := rlt_mode_logic_running_exit_rlt_state_4_outports_mode; 

               rlt_state_5_current_event := rlt_mode_logic_setup_enter_rlt_state_4_current_event; 

               rlt_state_5_states_root := rlt_mode_logic_setup_enter_rlt_state_4_states_root; 

               rlt_state_5_inports_clear := rlt_mode_logic_setup_enter_rlt_state_4_inports_clear; 

               rlt_state_5_inports_start := rlt_mode_logic_setup_enter_rlt_state_4_inports_start; 

               rlt_state_5_inports_steps_to_cook := rlt_mode_logic_setup_enter_rlt_state_4_inports_steps_to_cook; 

               rlt_state_5_inports_door_closed := rlt_mode_logic_setup_enter_rlt_state_4_inports_door_closed; 

               rlt_state_5_outports_mode := rlt_mode_logic_setup_enter_rlt_state_4_outports_mode; 

               rlt_state_5_outports_steps_remaining := rlt_mode_logic_setup_enter_rlt_state_4_outports_steps_remaining; 

      else 
               rlt_state_4_current_event := rlt_state_3_current_event; 

               rlt_state_4_inports_clear := rlt_state_3_inports_clear; 

               rlt_state_4_inports_steps_to_cook := rlt_state_3_inports_steps_to_cook; 

               rlt_state_4_outports_mode := rlt_state_3_outports_mode; 

               rlt_state_5_current_event := rlt_state_4_current_event; 

               rlt_state_5_states_root := rlt_state_4_states_root; 

               rlt_state_5_inports_clear := rlt_state_4_inports_clear; 

               rlt_state_5_inports_start := rlt_state_4_inports_start; 

               rlt_state_5_inports_steps_to_cook := rlt_state_4_inports_steps_to_cook; 

               rlt_state_5_inports_door_closed := rlt_state_4_inports_door_closed; 

               rlt_state_5_outports_mode := rlt_state_4_outports_mode; 

               rlt_state_5_outports_steps_remaining := rlt_state_4_outports_steps_remaining; 

      end if ; 

      mode_logic_running_eval(rlt_state_5_current_event, 
         rlt_state_5_states_root, 
         rlt_state_5_inports_clear, 
         rlt_state_5_inports_start, 
         rlt_state_5_inports_steps_to_cook, 
         rlt_state_5_inports_door_closed, 
         rlt_state_5_outports_mode, 
         rlt_state_5_outports_steps_remaining, 
         rlt_mode_logic_running_eval_rlt_state_12_current_event, 
         rlt_mode_logic_running_eval_rlt_state_12_states_root, 
         rlt_mode_logic_running_eval_rlt_state_12_inports_clear, 
         rlt_mode_logic_running_eval_rlt_state_12_inports_start, 
         rlt_mode_logic_running_eval_rlt_state_12_inports_steps_to_cook, 
         rlt_mode_logic_running_eval_rlt_state_12_inports_door_closed, 
         rlt_mode_logic_running_eval_rlt_state_12_outports_mode, 
         lt_mode_logic_running_eval_rlt_state_12_outports_steps_remaining) ; 

      if ((( not rlt_complete_1) and 
         ((rlt_state_5_states_root >= 1) and 
         (rlt_state_5_states_root <= 3)))) then 
               rlt_state_6_current_event := rlt_mode_logic_running_eval_rlt_state_12_current_event; 

               rlt_state_6_states_root := rlt_mode_logic_running_eval_rlt_state_12_states_root; 

               rlt_state_6_inports_clear := rlt_mode_logic_running_eval_rlt_state_12_inports_clear; 

               rlt_state_6_inports_start := rlt_mode_logic_running_eval_rlt_state_12_inports_start; 

               rlt_state_6_inports_steps_to_cook := rlt_mode_logic_running_eval_rlt_state_12_inports_steps_to_cook; 

               rlt_state_6_inports_door_closed := rlt_mode_logic_running_eval_rlt_state_12_inports_door_closed; 

               rlt_state_6_outports_mode := rlt_mode_logic_running_eval_rlt_state_12_outports_mode; 

               rlt_state_6_outports_steps_remaining := lt_mode_logic_running_eval_rlt_state_12_outports_steps_remaining; 

      else 
               rlt_state_6_current_event := rlt_state_5_current_event; 

               rlt_state_6_states_root := rlt_state_5_states_root; 

               rlt_state_6_inports_clear := rlt_state_5_inports_clear; 

               rlt_state_6_inports_start := rlt_state_5_inports_start; 

               rlt_state_6_inports_steps_to_cook := rlt_state_5_inports_steps_to_cook; 

               rlt_state_6_inports_door_closed := rlt_state_5_inports_door_closed; 

               rlt_state_6_outports_mode := rlt_state_5_outports_mode; 

               rlt_state_6_outports_steps_remaining := rlt_state_5_outports_steps_remaining; 

      end if ; 

      mode_logic_setup_eval(rlt_state_6_current_event, 
         rlt_state_6_states_root, 
         rlt_state_6_inports_clear, 
         rlt_state_6_inports_start, 
         rlt_state_6_inports_steps_to_cook, 
         rlt_state_6_inports_door_closed, 
         rlt_state_6_outports_mode, 
         rlt_state_6_outports_steps_remaining, 
         rlt_mode_logic_setup_eval_rlt_state_1_current_event, 
         rlt_mode_logic_setup_eval_rlt_state_1_states_root, 
         rlt_mode_logic_setup_eval_rlt_state_1_inports_clear, 
         rlt_mode_logic_setup_eval_rlt_state_1_inports_start, 
         rlt_mode_logic_setup_eval_rlt_state_1_inports_steps_to_cook, 
         rlt_mode_logic_setup_eval_rlt_state_1_inports_door_closed, 
         rlt_mode_logic_setup_eval_rlt_state_1_outports_mode, 
         rlt_mode_logic_setup_eval_rlt_state_1_outports_steps_remaining) ; 

      if ((( not rlt_complete_1) and 
         (rlt_state_5_states_root = 4))) then 
               rlt_state_7_current_event := rlt_mode_logic_setup_eval_rlt_state_1_current_event; 

               rlt_state_7_states_root := rlt_mode_logic_setup_eval_rlt_state_1_states_root; 

               rlt_state_7_inports_clear := rlt_mode_logic_setup_eval_rlt_state_1_inports_clear; 

               rlt_state_7_inports_start := rlt_mode_logic_setup_eval_rlt_state_1_inports_start; 

               rlt_state_7_inports_steps_to_cook := rlt_mode_logic_setup_eval_rlt_state_1_inports_steps_to_cook; 

               rlt_state_7_inports_door_closed := rlt_mode_logic_setup_eval_rlt_state_1_inports_door_closed; 

               rlt_state_7_outports_mode := rlt_mode_logic_setup_eval_rlt_state_1_outports_mode; 

               rlt_state_7_outports_steps_remaining := rlt_mode_logic_setup_eval_rlt_state_1_outports_steps_remaining; 

      else 
               rlt_state_7_current_event := rlt_state_6_current_event; 

               rlt_state_7_states_root := rlt_state_6_states_root; 

               rlt_state_7_inports_clear := rlt_state_6_inports_clear; 

               rlt_state_7_inports_start := rlt_state_6_inports_start; 

               rlt_state_7_inports_steps_to_cook := rlt_state_6_inports_steps_to_cook; 

               rlt_state_7_inports_door_closed := rlt_state_6_inports_door_closed; 

               rlt_state_7_outports_mode := rlt_state_6_outports_mode; 

               rlt_state_7_outports_steps_remaining := rlt_state_6_outports_steps_remaining; 

      end if ; 

      rlt_state_8_current_event := rlt_state_7_current_event; 

      rlt_state_8_states_root := rlt_state_7_states_root; 

      rlt_state_8_inports_clear := rlt_state_7_inports_clear; 

      rlt_state_8_inports_start := rlt_state_7_inports_start; 

      rlt_state_8_inports_steps_to_cook := rlt_state_7_inports_steps_to_cook; 

      rlt_state_8_inports_door_closed := rlt_state_7_inports_door_closed; 

      rlt_state_8_outports_mode := rlt_state_7_outports_mode; 

      rlt_state_8_outports_steps_remaining := rlt_state_7_outports_steps_remaining; 
   end mode_logic_eval;



   procedure mode_logic_main(
         start :  in Boolean ; 
         clear :  in Boolean ; 
         steps_to_cook :  in microwave_Decls.rlt_uint16; 
         door_closed :  in Boolean ; 
         wakeup :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_mode_logic_main_State_Type ; 
         mode :  out microwave_Decls.rlt_uint8; 
         steps_remaining :  out microwave_Decls.rlt_uint16) is 

      rlt_evt_init_step : Boolean ; 
      begin_state_states_root : microwave_Decls.rlt_int32; 
      begin_state_outports_mode : microwave_Decls.rlt_uint8; 
      begin_state_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_state_1_current_event : microwave_Decls.rlt_int32; 
      rlt_state_1_states_root : microwave_Decls.rlt_int32; 
      rlt_state_1_inports_clear : Boolean ; 
      rlt_state_1_inports_start : Boolean ; 
      rlt_state_1_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_state_1_inports_door_closed : Boolean ; 
      rlt_state_1_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_state_1_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_eval_rlt_state_8_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_eval_rlt_state_8_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_eval_rlt_state_8_inports_clear : Boolean ; 
      rlt_mode_logic_eval_rlt_state_8_inports_start : Boolean ; 
      rlt_mode_logic_eval_rlt_state_8_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_eval_rlt_state_8_inports_door_closed : Boolean ; 
      rlt_mode_logic_eval_rlt_state_8_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_eval_rlt_state_8_outports_steps_remaining : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_enter_rlt_state_2_current_event : microwave_Decls.rlt_int32; 
      rlt_mode_logic_enter_rlt_state_2_states_root : microwave_Decls.rlt_int32; 
      rlt_mode_logic_enter_rlt_state_2_inports_clear : Boolean ; 
      rlt_mode_logic_enter_rlt_state_2_inports_start : Boolean ; 
      rlt_mode_logic_enter_rlt_state_2_inports_steps_to_cook : microwave_Decls.rlt_uint16; 
      rlt_mode_logic_enter_rlt_state_2_inports_door_closed : Boolean ; 
      rlt_mode_logic_enter_rlt_state_2_outports_mode : microwave_Decls.rlt_uint8; 
      rlt_mode_logic_enter_rlt_state_2_outports_steps_remaining : microwave_Decls.rlt_uint16; 

   begin
      if (rlt_init_step) then 
               rlt_evt_init_step := true; 

               begin_state_outports_steps_remaining := 0; 

               begin_state_outports_mode := 0; 

               begin_state_states_root := 0; 

      else 
               rlt_evt_init_step := (( not rlt_node_state.rlt_pre) and 
                     rlt_node_state.rlt_pre1); 

               begin_state_outports_steps_remaining := rlt_node_state.rlt_pre4; 

               begin_state_outports_mode := rlt_node_state.rlt_pre3; 

               begin_state_states_root := rlt_node_state.rlt_pre2_states_root; 

      end if ; 

      mode_logic_eval(1, 
         begin_state_states_root, 
         clear, 
         start, 
         steps_to_cook, 
         door_closed, 
         begin_state_outports_mode, 
         begin_state_outports_steps_remaining, 
         rlt_mode_logic_eval_rlt_state_8_current_event, 
         rlt_mode_logic_eval_rlt_state_8_states_root, 
         rlt_mode_logic_eval_rlt_state_8_inports_clear, 
         rlt_mode_logic_eval_rlt_state_8_inports_start, 
         rlt_mode_logic_eval_rlt_state_8_inports_steps_to_cook, 
         rlt_mode_logic_eval_rlt_state_8_inports_door_closed, 
         rlt_mode_logic_eval_rlt_state_8_outports_mode, 
         rlt_mode_logic_eval_rlt_state_8_outports_steps_remaining) ; 

      mode_logic_enter(1, 
         begin_state_states_root, 
         clear, 
         start, 
         steps_to_cook, 
         door_closed, 
         begin_state_outports_mode, 
         begin_state_outports_steps_remaining, 
         rlt_mode_logic_enter_rlt_state_2_current_event, 
         rlt_mode_logic_enter_rlt_state_2_states_root, 
         rlt_mode_logic_enter_rlt_state_2_inports_clear, 
         rlt_mode_logic_enter_rlt_state_2_inports_start, 
         rlt_mode_logic_enter_rlt_state_2_inports_steps_to_cook, 
         rlt_mode_logic_enter_rlt_state_2_inports_door_closed, 
         rlt_mode_logic_enter_rlt_state_2_outports_mode, 
         rlt_mode_logic_enter_rlt_state_2_outports_steps_remaining) ; 

      if (wakeup) then 
               if (rlt_evt_init_step) then 
                        rlt_state_1_inports_steps_to_cook := rlt_mode_logic_enter_rlt_state_2_inports_steps_to_cook; 

                        rlt_state_1_inports_door_closed := rlt_mode_logic_enter_rlt_state_2_inports_door_closed; 

                        rlt_state_1_states_root := rlt_mode_logic_enter_rlt_state_2_states_root; 

                        rlt_state_1_outports_steps_remaining := rlt_mode_logic_enter_rlt_state_2_outports_steps_remaining; 

                        rlt_state_1_outports_mode := rlt_mode_logic_enter_rlt_state_2_outports_mode; 

                        rlt_state_1_current_event := rlt_mode_logic_enter_rlt_state_2_current_event; 

                        rlt_state_1_inports_clear := rlt_mode_logic_enter_rlt_state_2_inports_clear; 

                        rlt_state_1_inports_start := rlt_mode_logic_enter_rlt_state_2_inports_start; 

               else 
                        rlt_state_1_inports_steps_to_cook := rlt_mode_logic_eval_rlt_state_8_inports_steps_to_cook; 

                        rlt_state_1_inports_door_closed := rlt_mode_logic_eval_rlt_state_8_inports_door_closed; 

                        rlt_state_1_states_root := rlt_mode_logic_eval_rlt_state_8_states_root; 

                        rlt_state_1_outports_steps_remaining := rlt_mode_logic_eval_rlt_state_8_outports_steps_remaining; 

                        rlt_state_1_outports_mode := rlt_mode_logic_eval_rlt_state_8_outports_mode; 

                        rlt_state_1_current_event := rlt_mode_logic_eval_rlt_state_8_current_event; 

                        rlt_state_1_inports_clear := rlt_mode_logic_eval_rlt_state_8_inports_clear; 

                        rlt_state_1_inports_start := rlt_mode_logic_eval_rlt_state_8_inports_start; 

               end if ; 

      else 
               rlt_state_1_inports_steps_to_cook := steps_to_cook; 

               rlt_state_1_inports_door_closed := door_closed; 

               rlt_state_1_states_root := begin_state_states_root; 

               rlt_state_1_outports_steps_remaining := begin_state_outports_steps_remaining; 

               rlt_state_1_outports_mode := begin_state_outports_mode; 

               rlt_state_1_current_event := 0; 

               rlt_state_1_inports_clear := clear; 

               rlt_state_1_inports_start := start; 

      end if ; 

      steps_remaining := rlt_state_1_outports_steps_remaining; 

      mode := rlt_state_1_outports_mode; 

      rlt_node_state.rlt_pre1 := rlt_evt_init_step; 

      rlt_node_state.rlt_pre := wakeup; 

      rlt_node_state.rlt_pre4 := rlt_state_1_outports_steps_remaining; 

      rlt_node_state.rlt_pre3 := rlt_state_1_outports_mode; 

      rlt_node_state.rlt_pre2_states_root := rlt_state_1_states_root; 

      rlt_node_state.rlt_pre2_inports_steps_to_cook := rlt_state_1_inports_steps_to_cook; 

      rlt_node_state.rlt_pre2_inports_start := rlt_state_1_inports_start; 

      rlt_node_state.rlt_pre2_inports_door_closed := rlt_state_1_inports_door_closed; 

      rlt_node_state.rlt_pre2_current_event := rlt_state_1_current_event; 

      rlt_node_state.rlt_pre2_inports_clear := rlt_state_1_inports_clear; 
   end mode_logic_main;



   procedure mode_logic(
         start :  in Boolean ; 
         clear :  in Boolean ; 
         steps_to_cook :  in microwave_Decls.rlt_uint16; 
         door_closed :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_mode_logic_State_Type ; 
         mode :  out microwave_Decls.rlt_uint8; 
         steps_remaining :  out microwave_Decls.rlt_uint16) is 

   begin
      mode_logic_main(start, 
         clear, 
         steps_to_cook, 
         door_closed, 
         true, 
         rlt_init_step, 
         rlt_node_state.anon_eq, 
         mode, 
         steps_remaining) ; 
   end mode_logic;


   procedure switch9_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch9_port2_reactis_internal_subsys;


   procedure seconds_to_tens(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16) is 

      divide : microwave_Decls.rlt_uint16; 
      divide2 : microwave_Decls.rlt_uint16; 
      sum1 : microwave_Decls.rlt_uint16; 

   begin
      divide := (dividend / divisor); 

      divide2 := (divide * divisor); 

      sum1 := (dividend-divide2); 

      quotient := divide; 

      remainder := sum1; 
   end seconds_to_tens;


   procedure switch1_port0_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port0_reactis_internal_subsys1;


   procedure switch14_port2_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port2_reactis_internal_subsys1;


   procedure switch14_port0_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port0_reactis_internal_subsys1;


   procedure switch1_port2_reactis_internal_subsys1(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port2_reactis_internal_subsys1;


   procedure middle_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8) is 

      switch1_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch1_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      relational_operator2 : Boolean ; 
      switch1 : microwave_Decls.rlt_uint8; 
      switch14 : microwave_Decls.rlt_uint8; 

   begin
      switch1_port0_reactis_internal_subsys1(0, 
         switch1_port0_reactis_internal_subsys_out) ; 

      relational_operator2 := (keypad_digit <= 9); 

      switch14_port2_reactis_internal_subsys1(previous_value, 
         switch14_port2_reactis_internal_subsys_out) ; 

      switch14_port0_reactis_internal_subsys1(value, 
         switch14_port0_reactis_internal_subsys_out) ; 

      if (relational_operator2) then 
            switch14 := switch14_port0_reactis_internal_subsys_out; 
      else 
            switch14 := switch14_port2_reactis_internal_subsys_out; 
      end if ; 

      switch1_port2_reactis_internal_subsys1(switch14, 
         switch1_port2_reactis_internal_subsys_out) ; 

      if (clear_value) then 
            switch1 := switch1_port0_reactis_internal_subsys_out; 
      else 
            switch1 := switch1_port2_reactis_internal_subsys_out; 
      end if ; 

      digit := switch1; 
   end middle_digit;


   procedure switch_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch_port0_reactis_internal_subsys;


   procedure switch1_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port2_reactis_internal_subsys;


   procedure switch1_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port0_reactis_internal_subsys;


   procedure switch14_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port2_reactis_internal_subsys;


   procedure switch14_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port0_reactis_internal_subsys;


   procedure left_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8) is 

      switch1_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch1_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      relational_operator2 : Boolean ; 
      switch1 : microwave_Decls.rlt_uint8; 
      switch14 : microwave_Decls.rlt_uint8; 

   begin
      switch1_port0_reactis_internal_subsys(0, 
         switch1_port0_reactis_internal_subsys_out) ; 

      relational_operator2 := (keypad_digit <= 9); 

      switch14_port2_reactis_internal_subsys(previous_value, 
         switch14_port2_reactis_internal_subsys_out) ; 

      switch14_port0_reactis_internal_subsys(value, 
         switch14_port0_reactis_internal_subsys_out) ; 

      if (relational_operator2) then 
            switch14 := switch14_port0_reactis_internal_subsys_out; 
      else 
            switch14 := switch14_port2_reactis_internal_subsys_out; 
      end if ; 

      switch1_port2_reactis_internal_subsys(switch14, 
         switch1_port2_reactis_internal_subsys_out) ; 

      if (clear_value) then 
            switch1 := switch1_port0_reactis_internal_subsys_out; 
      else 
            switch1 := switch1_port2_reactis_internal_subsys_out; 
      end if ; 

      digit := switch1; 
   end left_digit;


   procedure switch1_port0_reactis_internal_subsys3(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port0_reactis_internal_subsys3;


   procedure switch14_port2_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port2_reactis_internal_subsys2;


   procedure switch14_port0_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch14_port0_reactis_internal_subsys2;


   procedure switch1_port2_reactis_internal_subsys3(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port2_reactis_internal_subsys3;


   procedure right_digit(
         keypad_digit :  in microwave_Decls.rlt_uint8; 
         value :  in microwave_Decls.rlt_uint8; 
         previous_value :  in microwave_Decls.rlt_uint8; 
         clear_value :  in Boolean ; 
         digit :  out microwave_Decls.rlt_uint8) is 

      switch1_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch1_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch14_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      relational_operator2 : Boolean ; 
      switch1 : microwave_Decls.rlt_uint8; 
      switch14 : microwave_Decls.rlt_uint8; 

   begin
      switch1_port0_reactis_internal_subsys3(0, 
         switch1_port0_reactis_internal_subsys_out) ; 

      relational_operator2 := (keypad_digit <= 9); 

      switch14_port2_reactis_internal_subsys2(previous_value, 
         switch14_port2_reactis_internal_subsys_out) ; 

      switch14_port0_reactis_internal_subsys2(value, 
         switch14_port0_reactis_internal_subsys_out) ; 

      if (relational_operator2) then 
            switch14 := switch14_port0_reactis_internal_subsys_out; 
      else 
            switch14 := switch14_port2_reactis_internal_subsys_out; 
      end if ; 

      switch1_port2_reactis_internal_subsys3(switch14, 
         switch1_port2_reactis_internal_subsys_out) ; 

      if (clear_value) then 
            switch1 := switch1_port0_reactis_internal_subsys_out; 
      else 
            switch1 := switch1_port2_reactis_internal_subsys_out; 
      end if ; 

      digit := switch1; 
   end right_digit;


   procedure switch_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch_port2_reactis_internal_subsys;


   procedure switch1_port2_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port2_reactis_internal_subsys2;


   procedure digits_to_time(
         digits_t_0 :  in microwave_Decls.rlt_uint8; 
         digits_t_1 :  in microwave_Decls.rlt_uint8; 
         digits_t_2 :  in microwave_Decls.rlt_uint8; 
         time :  out microwave_Decls.rlt_uint16) is 

      data_type_conversion : microwave_Decls.rlt_uint16; 
      data_type_conversion1 : microwave_Decls.rlt_uint16; 
      data_type_conversion2 : microwave_Decls.rlt_uint16; 
      product1 : microwave_Decls.rlt_uint16; 
      product2 : microwave_Decls.rlt_uint16; 
      sum : microwave_Decls.rlt_uint16; 

   begin
      data_type_conversion2 := (microwave_Decls.rlt_uint16 (digits_t_2)); 

      data_type_conversion := (microwave_Decls.rlt_uint16 (digits_t_0)); 

      product2 := (data_type_conversion * 60); 

      data_type_conversion1 := (microwave_Decls.rlt_uint16 (digits_t_1)); 

      product1 := (data_type_conversion1 * 10); 

      sum := ((data_type_conversion2+product1)+product2); 

      time := sum; 
   end digits_to_time;


   procedure steps_to_seconds(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16) is 

      divide : microwave_Decls.rlt_uint16; 
      divide2 : microwave_Decls.rlt_uint16; 
      sum1 : microwave_Decls.rlt_uint16; 

   begin
      divide := (dividend / divisor); 

      divide2 := (divide * divisor); 

      sum1 := (dividend-divide2); 

      quotient := divide; 

      remainder := sum1; 
   end steps_to_seconds;


   procedure seconds_to_minutes(
         dividend :  in microwave_Decls.rlt_uint16; 
         divisor :  in microwave_Decls.rlt_uint16; 
         quotient :  out microwave_Decls.rlt_uint16; 
         remainder :  out microwave_Decls.rlt_uint16) is 

      divide : microwave_Decls.rlt_uint16; 
      divide2 : microwave_Decls.rlt_uint16; 
      sum1 : microwave_Decls.rlt_uint16; 

   begin
      divide := (dividend / divisor); 

      divide2 := (divide * divisor); 

      sum1 := (dividend-divide2); 

      quotient := divide; 

      remainder := sum1; 
   end seconds_to_minutes;


   procedure time_on_display(
         steps_remaining :  in microwave_Decls.rlt_uint16; 
         steps_per_second :  in microwave_Decls.rlt_uint16; 
         display_time_t_0 :  out microwave_Decls.rlt_uint8; 
         display_time_t_1 :  out microwave_Decls.rlt_uint8; 
         display_time_t_2 :  out microwave_Decls.rlt_uint8) is 

      seconds_to_minutes_quotient : microwave_Decls.rlt_uint16; 
      seconds_to_minutes_remainder : microwave_Decls.rlt_uint16; 
      seconds_to_tens_quotient : microwave_Decls.rlt_uint16; 
      seconds_to_tens_remainder : microwave_Decls.rlt_uint16; 
      steps_to_seconds_quotient : microwave_Decls.rlt_uint16; 
      steps_to_seconds_remainder : microwave_Decls.rlt_uint16; 
      data_type_conversion : microwave_Decls.rlt_uint8; 
      data_type_conversion1 : microwave_Decls.rlt_uint8; 
      data_type_conversion2 : microwave_Decls.rlt_uint8; 

   begin
      steps_to_seconds(steps_remaining, 
         steps_per_second, 
         steps_to_seconds_quotient, 
         steps_to_seconds_remainder) ; 

      seconds_to_minutes(steps_to_seconds_quotient, 
         60, 
         seconds_to_minutes_quotient, 
         seconds_to_minutes_remainder) ; 

      seconds_to_tens(seconds_to_minutes_remainder, 
         10, 
         seconds_to_tens_quotient, 
         seconds_to_tens_remainder) ; 

      data_type_conversion := (microwave_Decls.rlt_uint8 (seconds_to_minutes_quotient)); 

      data_type_conversion1 := (microwave_Decls.rlt_uint8 (seconds_to_tens_quotient)); 

      data_type_conversion2 := (microwave_Decls.rlt_uint8 (seconds_to_tens_remainder)); 

      display_time_t_0 := data_type_conversion; 

      display_time_t_1 := data_type_conversion1; 

      display_time_t_2 := data_type_conversion2; 
   end time_on_display;


   procedure is_mode_setup1(
         mode :  in microwave_Decls.rlt_uint8; 
         is_setup :  out Boolean ) is 

      relational_operator2 : Boolean ; 

   begin
      relational_operator2 := (1 = mode); 

      is_setup := relational_operator2; 
   end is_mode_setup1;


   procedure switch8_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch8_port2_reactis_internal_subsys;


   procedure switch7_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch7_port2_reactis_internal_subsys;


   procedure switch1_port0_reactis_internal_subsys2(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch1_port0_reactis_internal_subsys2;


   procedure switch6_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch6_port2_reactis_internal_subsys;


   procedure switch5_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch5_port2_reactis_internal_subsys;


   procedure switch4_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch4_port2_reactis_internal_subsys;


   procedure switch3_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch3_port2_reactis_internal_subsys;


   procedure switch9_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch9_port0_reactis_internal_subsys;


   procedure switch2_port2_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch2_port2_reactis_internal_subsys;



   procedure le_detector9(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector9_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector9;



   procedure le_detector8(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector8_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector8;



   procedure le_detector7(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector7_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector7;



   procedure le_detector6(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector6_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector6;



   procedure le_detector5(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector5_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector5;



   procedure le_detector4(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector4_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector4;



   procedure le_detector3(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector3_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector3;



   procedure le_detector2(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector2_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector2;



   procedure le_detector1(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector1_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector1;



   procedure le_detector0(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector0_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector0;


   procedure switch8_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch8_port0_reactis_internal_subsys;


   procedure switch7_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch7_port0_reactis_internal_subsys;


   procedure switch6_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch6_port0_reactis_internal_subsys;


   procedure switch5_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch5_port0_reactis_internal_subsys;


   procedure switch4_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch4_port0_reactis_internal_subsys;


   procedure switch3_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch3_port0_reactis_internal_subsys;


   procedure switch2_port0_reactis_internal_subsys(
         nonkey_in :  in microwave_Decls.rlt_uint8; 
         nonkey_out :  out microwave_Decls.rlt_uint8) is 

   begin
      nonkey_out := nonkey_in; 
   end switch2_port0_reactis_internal_subsys;



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
         keypad_digit :  out microwave_Decls.rlt_uint8) is 

      le_detector0_le_detected : Boolean ; 
      le_detector1_le_detected : Boolean ; 
      le_detector2_le_detected : Boolean ; 
      le_detector3_le_detected : Boolean ; 
      le_detector4_le_detected : Boolean ; 
      le_detector5_le_detected : Boolean ; 
      le_detector6_le_detected : Boolean ; 
      le_detector7_le_detected : Boolean ; 
      le_detector8_le_detected : Boolean ; 
      le_detector9_le_detected : Boolean ; 
      switch_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch1_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch1_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch2_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch2_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch3_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch3_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch4_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch4_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch5_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch5_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch6_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch6_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch7_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch7_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch8_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch8_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch9_port0_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch9_port2_reactis_internal_subsys_out : microwave_Decls.rlt_uint8; 
      switch : microwave_Decls.rlt_uint8; 
      switch1 : microwave_Decls.rlt_uint8; 
      switch2 : microwave_Decls.rlt_uint8; 
      switch3 : microwave_Decls.rlt_uint8; 
      switch4 : microwave_Decls.rlt_uint8; 
      switch5 : microwave_Decls.rlt_uint8; 
      switch6 : microwave_Decls.rlt_uint8; 
      switch7 : microwave_Decls.rlt_uint8; 
      switch8 : microwave_Decls.rlt_uint8; 
      switch9 : microwave_Decls.rlt_uint8; 

   begin
      le_detector0(kp_0, 
         rlt_init_step, 
         rlt_node_state.le_detector0_le_detected_inst, 
         le_detector0_le_detected) ; 

      le_detector1(kp_1, 
         rlt_init_step, 
         rlt_node_state.le_detector1_le_detected_inst, 
         le_detector1_le_detected) ; 

      le_detector2(kp_2, 
         rlt_init_step, 
         rlt_node_state.le_detector2_le_detected_inst, 
         le_detector2_le_detected) ; 

      le_detector3(kp_4, 
         rlt_init_step, 
         rlt_node_state.le_detector3_le_detected_inst, 
         le_detector3_le_detected) ; 

      le_detector4(kp_3, 
         rlt_init_step, 
         rlt_node_state.le_detector4_le_detected_inst, 
         le_detector4_le_detected) ; 

      le_detector5(kp_5, 
         rlt_init_step, 
         rlt_node_state.le_detector5_le_detected_inst, 
         le_detector5_le_detected) ; 

      le_detector6(kp_7, 
         rlt_init_step, 
         rlt_node_state.le_detector6_le_detected_inst, 
         le_detector6_le_detected) ; 

      le_detector7(kp_6, 
         rlt_init_step, 
         rlt_node_state.le_detector7_le_detected_inst, 
         le_detector7_le_detected) ; 

      le_detector8(kp_8, 
         rlt_init_step, 
         rlt_node_state.le_detector8_le_detected_inst, 
         le_detector8_le_detected) ; 

      le_detector9(kp_9, 
         rlt_init_step, 
         rlt_node_state.le_detector9_le_detected_inst, 
         le_detector9_le_detected) ; 

      switch_port0_reactis_internal_subsys(0, 
         switch_port0_reactis_internal_subsys_out) ; 

      switch9_port2_reactis_internal_subsys(10, 
         switch9_port2_reactis_internal_subsys_out) ; 

      switch9_port0_reactis_internal_subsys(9, 
         switch9_port0_reactis_internal_subsys_out) ; 

      if (le_detector9_le_detected) then 
            switch9 := switch9_port0_reactis_internal_subsys_out; 
      else 
            switch9 := switch9_port2_reactis_internal_subsys_out; 
      end if ; 

      switch8_port2_reactis_internal_subsys(switch9, 
         switch8_port2_reactis_internal_subsys_out) ; 

      switch8_port0_reactis_internal_subsys(8, 
         switch8_port0_reactis_internal_subsys_out) ; 

      if (le_detector8_le_detected) then 
            switch8 := switch8_port0_reactis_internal_subsys_out; 
      else 
            switch8 := switch8_port2_reactis_internal_subsys_out; 
      end if ; 

      switch7_port2_reactis_internal_subsys(switch8, 
         switch7_port2_reactis_internal_subsys_out) ; 

      switch7_port0_reactis_internal_subsys(7, 
         switch7_port0_reactis_internal_subsys_out) ; 

      if (le_detector6_le_detected) then 
            switch7 := switch7_port0_reactis_internal_subsys_out; 
      else 
            switch7 := switch7_port2_reactis_internal_subsys_out; 
      end if ; 

      switch6_port2_reactis_internal_subsys(switch7, 
         switch6_port2_reactis_internal_subsys_out) ; 

      switch6_port0_reactis_internal_subsys(6, 
         switch6_port0_reactis_internal_subsys_out) ; 

      if (le_detector7_le_detected) then 
            switch6 := switch6_port0_reactis_internal_subsys_out; 
      else 
            switch6 := switch6_port2_reactis_internal_subsys_out; 
      end if ; 

      switch5_port2_reactis_internal_subsys(switch6, 
         switch5_port2_reactis_internal_subsys_out) ; 

      switch5_port0_reactis_internal_subsys(5, 
         switch5_port0_reactis_internal_subsys_out) ; 

      if (le_detector5_le_detected) then 
            switch5 := switch5_port0_reactis_internal_subsys_out; 
      else 
            switch5 := switch5_port2_reactis_internal_subsys_out; 
      end if ; 

      switch4_port2_reactis_internal_subsys(switch5, 
         switch4_port2_reactis_internal_subsys_out) ; 

      switch4_port0_reactis_internal_subsys(4, 
         switch4_port0_reactis_internal_subsys_out) ; 

      if (le_detector3_le_detected) then 
            switch4 := switch4_port0_reactis_internal_subsys_out; 
      else 
            switch4 := switch4_port2_reactis_internal_subsys_out; 
      end if ; 

      switch3_port2_reactis_internal_subsys(switch4, 
         switch3_port2_reactis_internal_subsys_out) ; 

      switch3_port0_reactis_internal_subsys(3, 
         switch3_port0_reactis_internal_subsys_out) ; 

      if (le_detector4_le_detected) then 
            switch3 := switch3_port0_reactis_internal_subsys_out; 
      else 
            switch3 := switch3_port2_reactis_internal_subsys_out; 
      end if ; 

      switch2_port2_reactis_internal_subsys(switch3, 
         switch2_port2_reactis_internal_subsys_out) ; 

      switch2_port0_reactis_internal_subsys(2, 
         switch2_port0_reactis_internal_subsys_out) ; 

      if (le_detector2_le_detected) then 
            switch2 := switch2_port0_reactis_internal_subsys_out; 
      else 
            switch2 := switch2_port2_reactis_internal_subsys_out; 
      end if ; 

      switch1_port2_reactis_internal_subsys2(switch2, 
         switch1_port2_reactis_internal_subsys_out) ; 

      switch1_port0_reactis_internal_subsys2(1, 
         switch1_port0_reactis_internal_subsys_out) ; 

      if (le_detector1_le_detected) then 
            switch1 := switch1_port0_reactis_internal_subsys_out; 
      else 
            switch1 := switch1_port2_reactis_internal_subsys_out; 
      end if ; 

      switch_port2_reactis_internal_subsys(switch1, 
         switch_port2_reactis_internal_subsys_out) ; 

      if (le_detector0_le_detected) then 
            switch := switch_port0_reactis_internal_subsys_out; 
      else 
            switch := switch_port2_reactis_internal_subsys_out; 
      end if ; 

      keypad_digit := switch; 
   end process_keypad_inputs;



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
         steps_to_cook :  out microwave_Decls.rlt_uint16) is 

      digits_to_time_time : microwave_Decls.rlt_uint16; 
      left_digit_digit : microwave_Decls.rlt_uint8; 
      middle_digit_digit : microwave_Decls.rlt_uint8; 
      process_keypad_inputs_keypad_digit : microwave_Decls.rlt_uint8; 
      right_digit_digit : microwave_Decls.rlt_uint8; 
      product : microwave_Decls.rlt_uint16; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay_right_digit := 0; 

               rlt_node_state.unit_delay_middle_digit := 0; 

               rlt_node_state.unit_delay_left_digit := 0; 

      else 
            null; 
      end if ; 

      process_keypad_inputs(kp_0, 
         kp_1, 
         kp_2, 
         kp_3, 
         kp_4, 
         kp_5, 
         kp_6, 
         kp_7, 
         kp_8, 
         kp_9, 
         rlt_init_step, 
         rlt_node_state.process_keypad_inputs_keypad_digit_inst, 
         process_keypad_inputs_keypad_digit) ; 

      right_digit(process_keypad_inputs_keypad_digit, 
         process_keypad_inputs_keypad_digit, 
         rlt_node_state.unit_delay_right_digit, 
         kp_clear, 
         right_digit_digit) ; 

      middle_digit(process_keypad_inputs_keypad_digit, 
         rlt_node_state.unit_delay_right_digit, 
         rlt_node_state.unit_delay_middle_digit, 
         kp_clear, 
         middle_digit_digit) ; 

      left_digit(process_keypad_inputs_keypad_digit, 
         rlt_node_state.unit_delay_middle_digit, 
         rlt_node_state.unit_delay_left_digit, 
         kp_clear, 
         left_digit_digit) ; 

      digits_to_time(left_digit_digit, 
         middle_digit_digit, 
         right_digit_digit, 
         digits_to_time_time) ; 

      product := (digits_to_time_time * steps_per_second); 

      steps_to_cook := product; 

      rlt_node_state.unit_delay_right_digit := right_digit_digit; 

      rlt_node_state.unit_delay_middle_digit := middle_digit_digit; 

      rlt_node_state.unit_delay_left_digit := left_digit_digit; 
   end keypad_processing;



   procedure le_detector12(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector12_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector12;



   procedure le_detector11(
         nonkey_in :  in Boolean ; 
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out microwave_Decls.rlt_le_detector11_State_Type ; 
         le_detected :  out Boolean ) is 

      logical_operator1 : Boolean ; 
      logical_operator2 : Boolean ; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := false; 

      else 
            null; 
      end if ; 

      logical_operator2 := ( not rlt_node_state.unit_delay); 

      logical_operator1 := (nonkey_in and 
            logical_operator2); 

      le_detected := logical_operator1; 

      rlt_node_state.unit_delay := nonkey_in; 
   end le_detector11;



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
         mode :  out microwave_Decls.rlt_uint8) is 

      keypad_processing_steps_to_cook_temp : microwave_Decls.rlt_uint16; 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay2 := true; 

               rlt_node_state.keypad_processing_steps_to_cook_temp_inst_init_step := true; 

      else 
            null; 
      end if ; 

      if (rlt_node_state.unit_delay2) then 
               keypad_processing(kp_clear, 
                  kp_0, 
                  kp_1, 
                  kp_2, 
                  kp_3, 
                  kp_4, 
                  kp_5, 
                  kp_6, 
                  kp_7, 
                  kp_8, 
                  kp_9, 
                  steps_per_second, 
                  rlt_node_state.keypad_processing_steps_to_cook_temp_inst_init_step, 
                  rlt_node_state.keypad_processing_steps_to_cook_temp_inst, 
                  keypad_processing_steps_to_cook_temp) ; 

               rlt_node_state.keypad_processing_steps_to_cook := keypad_processing_steps_to_cook_temp; 

      else 
               if (rlt_node_state.keypad_processing_steps_to_cook_temp_inst_init_step) then 
                        keypad_processing_steps_to_cook_temp := 0; 

               else 
                     null; 
               end if ; 

               rlt_node_state.keypad_processing_steps_to_cook := 0; 

      end if ; 

      le_detector12(kp_start, 
         rlt_init_step, 
         rlt_node_state.le_detector12_le_detected_inst, 
         rlt_node_state.le_detector12_le_detected) ; 

      le_detector11(kp_clear, 
         rlt_init_step, 
         rlt_node_state.le_detector1_le_detected_inst, 
         rlt_node_state.le_detector1_le_detected) ; 

      mode_logic(rlt_node_state.le_detector12_le_detected, 
         rlt_node_state.le_detector1_le_detected, 
         rlt_node_state.keypad_processing_steps_to_cook, 
         door_closed, 
         rlt_init_step, 
         rlt_node_state.anon_eq1, 
         rlt_node_state.mode_logic_mode, 
         rlt_node_state.mode_logic_steps_remaining) ; 

      time_on_display(rlt_node_state.mode_logic_steps_remaining, 
         steps_per_second, 
         rlt_node_state.time_on_display_display_time_t_0, 
         rlt_node_state.time_on_display_display_time_t_1, 
         rlt_node_state.time_on_display_display_time_t_2) ; 

      is_mode_setup1(rlt_node_state.mode_logic_mode, 
         rlt_node_state.is_mode_setup1_is_setup) ; 

      mode := rlt_node_state.mode_logic_mode; 

      left_digit1 := rlt_node_state.time_on_display_display_time_t_0; 

      middle_digit1 := rlt_node_state.time_on_display_display_time_t_1; 

      right_digit1 := rlt_node_state.time_on_display_display_time_t_2; 

      rlt_node_state.unit_delay2 := rlt_node_state.is_mode_setup1_is_setup; 

      rlt_node_state.keypad_processing_steps_to_cook_temp_inst_init_step := ( not rlt_node_state.unit_delay2); 
   end microwave_inner;



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
         mode :  out microwave_Decls.rlt_uint8) is 

      microwave_inner_left_digit : microwave_Decls.rlt_uint8; 
      microwave_inner_middle_digit : microwave_Decls.rlt_uint8; 
      microwave_inner_right_digit : microwave_Decls.rlt_uint8; 
      microwave_inner_mode : microwave_Decls.rlt_uint8; 

   begin
      microwave_inner(kp_start, 
         kp_clear, 
         kp_0, 
         kp_1, 
         kp_2, 
         kp_3, 
         kp_4, 
         kp_5, 
         kp_6, 
         kp_7, 
         kp_8, 
         kp_9, 
         door_closed, 
         1, 
         rlt_init_step, 
         rlt_node_state.anon_eq, 
         microwave_inner_left_digit, 
         microwave_inner_middle_digit, 
         microwave_inner_right_digit, 
         microwave_inner_mode) ; 

      left_digit1 := microwave_inner_left_digit; 

      middle_digit1 := microwave_inner_middle_digit; 

      right_digit1 := microwave_inner_right_digit; 

      mode := microwave_inner_mode; 
   end microwave;

end microwave_Package; 

--**********************************************************************
--   end of translation of specification: microwave
--**********************************************************************/
