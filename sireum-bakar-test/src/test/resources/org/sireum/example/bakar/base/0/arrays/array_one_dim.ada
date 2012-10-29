package Array_One_Dim
  --# own V, F, G;
  --# initializes V, F, G;
is
  I : constant := 3;
  F : Integer;

  subtype Index is Integer range 1..100;
  subtype subIndex is Index range 5..9;
  subtype highIndex is Index range 50..60;
  
  type Vector is array(Integer range <>) of Integer;
  subtype constrainedVector is Vector (Index);
  
  V : constrainedVector;
  G : Index;
    
  procedure dummy(X : in highIndex);
  --# global in out V;
  --# derives V from V, X;
  
  procedure dummy2(X : in highIndex);
  --# global in out V;
  --# derives V from V, X;
   
end Array_One_Dim;

package body Array_One_Dim is
  
  procedure dummy(X : in highIndex) is
    type Naturals is range 0..Integer'Last;
    subtype Positives is Naturals range 1..Naturals'Last;
    
    subtype locIndex is Index range 1..10;
    type localVector is array(Integer range <>) of Naturals;
    subtype localConsVector is localVector(locIndex);
    
    Vprime : constant localConsVector := 
      localConsVector'(others => Naturals'Last + I); -- can't use F on lhs or rhs here
      
    M : constant Naturals := Naturals'Last;
  begin
    -- clearly both lhs and rhs will raise an index constraint violations.  The 
    -- examiner will catch the lhs violation if the constant I is used instead
    -- but it can't seem to reason about the non-overlapping ranges in the rhs

    V(X + 10000) := Index(Naturals(Integer(Vprime(X)))) - I;
  end dummy;
 
  procedure dummy2(X : in highIndex) is
    subtype locIndex is Index range 1..10;
    subtype localConsVector is Vector(locIndex);
    Vprime : constant localConsVector := 
      localConsVector'(others => locIndex'Last + I); -- can't use F on lhs or rhs here
    locI : highIndex;
  begin
  
    locI := X + 1;
    -- clearly both lhs and rhs will raise an index constraint violations.  The 
    -- examiner will catch the lhs violation if the constant I is used instead
    -- but it can't seem to reason about the non-overlapping ranges in the rhs
    V(X + 10000) := Vprime(X) - locI;
  end dummy2;
 
begin
   F := 5;
   G := 10;
   V := constrainedVector'(1..(5-2) | 10 => F, -- F is effectively a constant 
                                               -- here. It can be used on the rhs 
                                               -- but not on lhs e.g. F + 6 => F 
                                               -- is illegal (even if G were
                                               -- an Index)
                           3 + 1 => F + 1,
                           subIndex => I, 
                           Index range 11..20 => 20,
                           others => 3);
end Array_One_Dim;

