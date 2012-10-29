package ArrayCondAssign
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  
  procedure Test(I1, I2: in Index; A1, A2, C: in Integer; Y : in out KeyTable);
  --# derives Y from I1, I2, A1, A2, C, Y;
  

end ArrayCondAssign;

package body ArrayCondAssign
is
   
  procedure Test(I1, I2: in Index; A1, A2, C: in Integer; Y : in out KeyTable)
  is
  begin
     if C > 0 
     then
	Y(I1) := A1;
     else 
	Y(I1) := A2;  
     end if;
     Y(I2) := A2;    
  end Test;

end ArrayCondAssign;
