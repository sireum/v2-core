--**********************************************************************
--   Lustre2C translation of specification: fib
--   Version 0.1; Build Date: Thu Oct 02 12:55:31 2008
--   Written by Mike Whalen and Karl Hoech
--   Copyright 2008 Rockwell Collins, Inc. and the University of Minnesota.
--   All Rights Reserved.
--**********************************************************************/


package fib_Decls is 


   type rlt_int32 is range -(2**31) .. ((2**31) - 1); for rlt_int32'size use 32;
   type rlt_uint32 is range 0 .. ((2**32) - 1); for rlt_uint32'size use 32;
   type rlt_mod32 is mod (2**32); for rlt_mod32'size use 32;
   type rlt_int16 is range -(2**15) .. ((2**15) - 1); for rlt_int16'size use 16;
   type rlt_uint16 is range 0 .. ((2**16) - 1); for rlt_uint16'size use 16;
   type rlt_mod16 is mod (2**16); for rlt_mod16'size use 16;
   type rlt_int8 is range -(2**7) .. ((2**7) - 1); for rlt_int8'size use 8;
   type rlt_uint8 is range 0 .. ((2**8) - 1); for rlt_uint8'size use 8;
   type rlt_mod8 is mod (2**8); for rlt_mod8'size use 8;

   type rlt_fib_State_Type is 
   record 
      unit_delay : rlt_int32; 
      unit_delay1 : rlt_int32; 
   end record ; 


end fib_Decls; 
--**********************************************************************
--   end of translation of specification: fib
--**********************************************************************/
