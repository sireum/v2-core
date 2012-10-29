package SymbolicBounds is
  
  type ArrayType is array(Integer range <>) of Integer;

  -- Symbolic Array Lookup with Concrete Index
  function t_SA_L_CI(A : ArrayType) return Integer;
  
  -- Symbolic Array Lookup with Symbolic Index
  function t_SA_L_SI(A : ArrayType; I : Integer) return Integer;
    
end SymbolicBounds;

package body SymbolicBounds
is
  function t_SA_L_CI(A : ArrayType) return Integer is begin
    return A(1);
  end t_SA_L_CI;

  function t_SA_L_SI(A : ArrayType; I : Integer) return Integer is begin
    return A(I);
  end t_SA_L_SI;
  
end SymbolicBounds;