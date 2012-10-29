
-- This example comes from paper :
-- "Finding Loop Invariants for Programs over Arrays Using a Theorem Prover"
package WhileLoop_Array
  --#own A, B, C;
  --#initializes A, B, C;
is 
   subtype Index is Integer range 0..10;
   type H is array(Index) of Integer;
   A: H := H'(others=>0);
   B: H := H'(others=>0);
   C: H := H'(others=>0);
   
   procedure Partitioning (K: in Index);
   --#global in A; in out B, C;
   --# derives B from A, B, K &
   --#         C from A, C, K;
end WhileLoop_Array;

package body WhileLoop_Array is

   procedure Partitioning (K : in Index)
   is
      ai: Index;
      bi: Index;
      ci: Index;
   begin
      ai := 0;
      bi := 0;
      ci := 0;
      while ai < K loop
	 if A(ai) > 0 then
	    B(bi) := A(ai);
	    bi := bi + 1; 
	 else
	    C(ci) := A(ai);
	    ci := ci + 1;
	 end if;
	 ai := ai + 1;
      end loop;
   end Partitioning;
   
end WhileLoop_Array;
