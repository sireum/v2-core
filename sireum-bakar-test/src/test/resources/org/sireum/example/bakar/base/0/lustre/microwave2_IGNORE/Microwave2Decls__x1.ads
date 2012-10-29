--**********************************************************************
--   Lustre2ada translation of specification: Microwave
--   Version 0.1; Build Date: Mon Aug 03 16:48:01 2009
--   Copyright (C) 2008,2009 Rockwell Collins, Inc.
--   All Rights Reserved.
--**********************************************************************/


with MicrowaveTypes;
--# inherit MicrowaveTypes;
package microwave_Decls is

   Microwave_Mode_Logic_Init : constant MicrowaveTypes.Microwave_Mode_Logic_Type  := MicrowaveTypes.Microwave_Mode_Logic_Type'(
         Outports => MicrowaveTypes.Rlt_Record_1'(
            Mode => 0,
            Steps_Remaining => 0 ) ,
         States => MicrowaveTypes.Rlt_Record_2'(
            Running => 0,
            Microwave_Mode_Logic => 0 )  ) ;

end microwave_Decls;

--**********************************************************************
--   end of translation of specification: Microwave
--**********************************************************************/
