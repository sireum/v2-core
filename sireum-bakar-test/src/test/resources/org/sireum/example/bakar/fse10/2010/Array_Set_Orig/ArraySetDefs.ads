with ArraySetUnsigned;
use type ArraySetUnsigned.Word;
use type ArraySetUnsigned.Long;
--#inherit ArraySetUnsigned;
package ArraySetDefs is
 ----------------------------------------------------------------------------
 -- Item Value TEK definitions

   subtype ID_Type is ArraySetUnsigned.Word;
   Null_ID : constant ID_Type := ID_Type'First;

   Max_Value_Length : constant ArraySetUnsigned.Word := 100;
   subtype Value_Length_Type is ArraySetUnsigned.Word range 0 .. Max_Value_Length;
   Null_Value_Length : constant Value_Length_Type := Value_Length_Type'First;

   Max_Value_Dwords : constant ArraySetUnsigned.Word := Max_Value_Length / 4;
   subtype Value_Index_Type is ArraySetUnsigned.Word range 0 .. Max_Value_Dwords - 1;
   type Value_Type is array (Value_Index_Type) of ArraySetUnsigned.Long;
   Null_Value_Entry : constant ArraySetUnsigned.Long := 0;
   Null_Value : constant Value_Type := Value_Type'(others => Null_Value_Entry);

   ----------------------------------------------------------------------------
   -- Define all possible responses for all messages
   subtype Response_Type is ArraySetUnsigned.Long range 0 .. 999;  -- TBD

   DB_Success : constant Response_Type := 0;
   Success    : constant Response_Type := 0;
   Failure    : constant Response_Type := 999;

   DB_Already_Exists : constant Response_Type := 21;
   DB_Does_Not_Exist : constant Response_Type := 23;
   DB_In_Use : constant Response_Type := 25;
   DB_Input_Check_Fail : constant Response_Type := 26;
   DB_No_Room : constant Response_Type := 30;
end ArraySetDefs;
