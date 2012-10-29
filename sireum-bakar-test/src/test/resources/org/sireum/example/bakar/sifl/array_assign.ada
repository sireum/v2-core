package ArrayAssignment
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  
  procedure Test(I1, I2: in Index; A1, A2: in Integer; Y : in out KeyTable);
  --# derives Y from I1, I2, A1, A2, Y;
  

end ArrayAssignment;

package body ArrayAssignment
is
   
  procedure Test(I1, I2: in Index; A1, A2: in Integer; Y : in out KeyTable)
  is
  begin
     Y(I1) := A1;
     Y(I2) := A2;   
  end Test;

end ArrayAssignment;
