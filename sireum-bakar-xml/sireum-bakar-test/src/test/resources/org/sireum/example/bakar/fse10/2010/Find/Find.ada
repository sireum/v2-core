package Find_Package
is
   type Index is range 1..10;
   type AType is array (Index) of Integer;
   -- subtype Index is Integer range 1 .. 10;
   -- type Table is array(Index) of Integer;

   function Find(A : AType; X : Integer) return Index;
   --# pre for some I in Index => ( A(I) = X );
   --# return Z => (A(Z) = X and
   --#    (for all I in Index range Index'First .. (Z-1) =>
   --#        (A(I) /= X)));

end Find_Package;

package body Find_Package
is
   function Find (A: AType; X : Integer) return Index
   is
      Z : Index := Index'First;
   begin
      while A(Z) /= X and Z < Index'Last loop
         --# assert Z < Index'Last
         --#    and (for all  I in Index range Index'First .. Z  => (A(I) /= X))
         --#    and (for some I in Index range Z+1 .. Index'Last => (A(I)  = X ));
         --  Replacing Z+1 by Index'First in the last conjunct results in
         --  fewer VCs remaining undischarged by the Spark tools, but IMHO, it
         --  yields VCs that should be easier to prove manually.
         Z := Z + 1;
      end loop;
      return Z;
   end Find;

end Find_Package;
