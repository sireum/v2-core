package Agg1_Pkg
is
  subtype Index is Integer range 1 .. 6;
  type AType is array (Index) of Integer;

  procedure Test(A : out AType; B : out AType);
  --# derives A from & 
  --#         B from;

end Agg1_Pkg;

package body Agg1_Pkg
is
  procedure Test(A : out AType; B : out AType)
  is
  begin
     A := AType'(2..4 => 11, others => 25);
     B := AType'(1..3 => 11, others => 26);

     A(1) := 11;
     A(5) := 26;
          
     B(4) := 11;
     B(6) := 25;
     
     --# assert A = B;
  end Test;
end Agg1_Pkg;