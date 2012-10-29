package ArrayOfRecords 
--# own oArray;
--# initializes oArray;
is

  type Object is
    record
      X: Integer;
      Y: Integer;
    end record;

  dummyObject : constant Object := Object'(0, 0);
  
  type Index_Range is range 1..10;
  type ObjectArray is array (Index_Range) of Object;
  
  oArray : ObjectArray;
  
  procedure Swap(Q: in out ObjectArray; theIndex: in Index_Range);
  --# derives Q from Q, theIndex;

end ArrayOfRecords;

package body ArrayOfRecords is

   procedure Swap(Q: in out ObjectArray; theIndex: in Index_Range) is
     temp1 : Integer;
     temp2 : Integer;
   begin
     temp1 := Q(theIndex).X;
     temp2 := Q(theIndex).Y;
     
     Q(theIndex).X := temp2;
     Q(theIndex).Y := temp1;
   end Swap;

begin
  
  oArray := ObjectArray'(others => dummyObject);
  
end ArrayOfRecords;

