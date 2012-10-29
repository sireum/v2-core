package PosArray
--# own Box, Box2;
--# initializes Box, Box2;
is

 subtype Index is Integer range 1..5;
 type Square is array (Index) of Integer;
 
 -- note that all positions are defined but we can still do an 'others'
 Box: Square := Square'(-1, -2, 7 ,3 , 2, others=>3);
 
 Box2: Square;
 
  procedure dummy(X : in Index);
  --# global in out Box;
  --# derives Box from Box, X;
  
end PosArray;


package body PosArray
is

   procedure dummy(X : in Index) is
   begin
      Box(X) := Box(X) - 1;
   end dummy;
  
begin
  Box2 := Square'(77, others => 5); -- 1st pos will be 77, all others will be 5
end PosArray;