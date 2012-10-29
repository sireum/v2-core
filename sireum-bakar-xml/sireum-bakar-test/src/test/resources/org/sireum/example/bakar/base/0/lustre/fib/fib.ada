--**********************************************************************
--   Lustre2C translation of specification: fib
--   Version 0.1; Build Date: Thu Oct 02 12:55:31 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/


with fib_Decls; 


   use type fib_Decls.rlt_int8;


   use type fib_Decls.rlt_uint8;


   use type fib_Decls.rlt_mod8;


   use type fib_Decls.rlt_int16;


   use type fib_Decls.rlt_uint16;


   use type fib_Decls.rlt_mod16;


   use type fib_Decls.rlt_int32;


   use type fib_Decls.rlt_uint32;


   use type fib_Decls.rlt_mod32;

package fib_Package is 



   procedure fib(
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out fib_Decls.rlt_fib_State_Type ; 
         out1 :  out fib_Decls.rlt_int32);

end fib_Package; 

--**********************************************************************
--   end of translation of specification: fib
--**********************************************************************/


--**********************************************************************
--   Lustre2C translation of specification: fib
--   Version 0.1; Build Date: Thu Oct 02 12:55:31 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/


package body fib_Package is 



   procedure fib(
         rlt_init_step :  in Boolean ; 
         rlt_node_state :  in out fib_Decls.rlt_fib_State_Type ; 
         out1 :  out fib_Decls.rlt_int32) is 

   begin
      if (rlt_init_step) then 
               rlt_node_state.unit_delay := 0; 

               rlt_node_state.unit_delay1 := 1; 

      else 
            null; 
      end if ; 

      out1 := (rlt_node_state.unit_delay+rlt_node_state.unit_delay1); 

      rlt_node_state.unit_delay := (rlt_node_state.unit_delay+rlt_node_state.unit_delay1); 

      rlt_node_state.unit_delay1 := rlt_node_state.unit_delay; 
   end fib;

end fib_Package; 

--**********************************************************************
--   end of translation of specification: fib
--**********************************************************************/
