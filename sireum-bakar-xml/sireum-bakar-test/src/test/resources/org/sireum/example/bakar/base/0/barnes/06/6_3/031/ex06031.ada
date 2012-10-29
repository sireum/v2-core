-- Exercise 6.3.1:
-- Rewriting legal Ada as SPARK.
package P is

   type R is range 0..255;
   type T is array(R) of Integer;

   procedure Foo(A : in out T);
   --# derives A from A;

end P;

package body P is

   procedure Foo(A : in out T)
   is
   begin
      My_Loop:
      for I in R range 1..100 loop  -- Need to associate I with a type
         A(I) := A(I) + 1;
         if A(I) = 10 then
            exit;
         end if;
         A(I+1) := 0;
      end loop My_Loop;
   end Foo;
end P;
