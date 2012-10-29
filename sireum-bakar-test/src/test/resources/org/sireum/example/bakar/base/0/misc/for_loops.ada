package for_loops 
--# own A;
--# initializes A;
is
   aFloatConst : constant := 3.0 + 7.3;
   aIntConst : constant := 1 + 3;
   type Index_Range is  range 1..100;
   type Vector is array(Index_Range) of Integer;
   A : Vector := Vector'(others => 1);
     
   procedure Stupid(Z : in Index_Range);
   --# global in out A;
   --# derives A from *, Z;
    
end for_loops;


package body for_loops is

   procedure Stupid(Z : in Index_Range) is
   begin
     
     for I in Index_Range loop
       A(I) := 1;
     end loop;
     
     for I in reverse Index_Range loop
       A(I) := 1;
     end loop;
     
     for I in Index_Range range 1 .. 3 loop
       A(I) := 1;
     end loop;
     
     for I in reverse Index_Range range 1 .. Z loop
       A(I) := 1;
     end loop;
     
     -- note that Z could be <= Index_Range'First + 10
     for I in Index_Range range Index_Range'First + 10 .. Z loop
       A(I) := A(I);
     end loop;
     
     -- note that Z could be <= Index_Range'First + 10
     for I in reverse Index_Range range Index_Range'First + 10 .. Z loop
       A(I) := A(I);
     end loop;
     
     -- why whould you ever do the following ????
     for I in Index_Range range A'Range loop
       A(I) := A(I);
     end loop;
     
     for I in reverse Index_Range range A'Range loop
       A(I) := A(I);
     end loop;
     
     for I in Index_Range range A'Range(1) loop
       A(I) := A(I);
     end loop;
     
     for I in reverse Index_Range range A'Range(1) loop
       A(I) := A(I);
     end loop;
     
   end Stupid;
   
 end for_loops;