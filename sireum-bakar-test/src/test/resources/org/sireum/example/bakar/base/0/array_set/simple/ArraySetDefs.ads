with ArraySetUnsigned;
use type ArraySetUnsigned.Word;
use type ArraySetUnsigned.Long;
--# inherit ArraySetUnsigned;
package ArraySetDefs is
 ----------------------------------------------------------------------------
 -- Item Value TEK definitions

  Max_Value_Length : constant ArraySetUnsigned.Word := 100;
  Max_Value_Dwords : constant ArraySetUnsigned.Word := Max_Value_Length / 4;

  subtype ID_Type is ArraySetUnsigned.Word;
  subtype Value_Type is Integer;
     
  Null_ID : constant ID_Type := ID_Type'First;
  Null_Value : constant Value_Type := 0;
  
  ----------------------------------------------------------------------------
  -- Define all possible responses for all messages
  -- NOTE: using an enumeration for the response type would ensure that
  --       they are unique
  subtype Response_Type is ArraySetUnsigned.Long range 0 .. 999;  -- TBD

  -- NOTE: Success and Failure are never used
  Success    : constant Response_Type := 0;
  Failure    : constant Response_Type := 999;
   
  DB_Success          : constant Response_Type := 0;
  DB_Already_Exists   : constant Response_Type := 21;
  DB_Does_Not_Exist   : constant Response_Type := 23;
  DB_In_Use           : constant Response_Type := 25;
  DB_Input_Check_Fail : constant Response_Type := 26;
  DB_No_Room          : constant Response_Type := 30;
end ArraySetDefs;
