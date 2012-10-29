-- tests aggregates, store, and lookup on one dimensional constrained arrays

package ConcreteBounds 
--# own GA;
--# initializes GA;
is
  
  type Index is range -5 .. 5;
  type ArrayType is array(Index) of Integer;
  
  -- Kiasan will ignore the aggregate, however if GA is constant then we need
  -- to fill in the region map
  GA : ArrayType := ArrayType'(others => 0);

                             
  function t_aggregate_others return ArrayType;
  
  function t_aggregate_regioned return ArrayType;



  -- Concrete Array Lookup with Concrete Index
  function t_CA_L_CI_1 return Integer;
  
  -- Concrete Array Lookup with Concrete Index
  function t_CA_L_CI_2 return Integer;
  --# global GA;
  
  
  
  -- Concrete Array Lookup with Symbolic Index
  function t_CA_L_SI_1(I : Index) return Integer;
  --# global GA;
  
  -- Concrete Array Lookup with Symbolic Index
  function t_CA_L_SI_2(I : Index) return Integer;
  --# global GA;

  -- Concrete Array Lookup with Symbolic Index
  function t_CA_L_SI_3(I : Index) return Integer;
  --# global GA;


  -- Concrete Array Store with Concrete Index
  procedure t_CA_S_CI_1(Val : in Integer);
  --# global in out GA;
  --# derives GA from GA, Val;

  -- Concrete Array Store with Concrete Index
  procedure t_CA_S_CI_2(Val : in Integer; I : in Index);
  --# global out GA;
  --# derives GA from I, Val;
 
  -- Concrete Array Store with Concrete Index
  procedure t_CA_S_CI_3(Val : in Integer; I : in Index);
  --# global out GA;
  --# derives GA from I, Val;
   
  -- Concrete Array Store with Symbolic Index
  procedure t_CA_S_SI(I : in Index; Val : in Integer);
  --# global in out GA;
  --# derives GA from GA, I, Val;

  
  procedure t_CA_Swap_Elements_1(I, J: in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_CA_Swap_Elements_2(I, J: in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_CA_Swap_Elements_3(I, J: in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;

  procedure t_CA_Swap_Elements_weak(T : in out ArrayType; I, J: in Index);
  --# derives T from T, I, J;

  function t_CopyOnWrite return ArrayType;
  
end ConcreteBounds;

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

package body ConcreteBounds
is
  function t_aggregate_others return ArrayType is begin
    return ArrayType'(others => -1);
  end t_aggregate_others;
  
  function t_aggregate_regioned return ArrayType is begin
    return ArrayType'(Index'First + 2 .. Index'First + 5 => 0,
                       Index'First + 7 .. Index'Last - 2 => 1,
                                                  others => 2);
  end t_aggregate_regioned;

  -----------------------------------------------------------------------------
  -- LAC
  -- Concrete-Bounded array lookup with a concrete index c
  -- (with concrete regions)
  -----------------------------------------------------------------------------
    
  -- LAC_CM
  function t_CA_L_CI_1 return Integer is
    A : ArrayType;
  begin
    A := t_aggregate_others; -- get a concrete array
    return A(Index'First + 1);
  end t_CA_L_CI_1;


  -- LAC_CM
  function t_CA_L_CI_2 return Integer is
  begin
    return GA(Index'First + 1);
  end t_CA_L_CI_2;
  
  
  
  -----------------------------------------------------------------------------
  -- LAS
  -- Concrete-Bounded array lookup with a symbolic index \alpha
  -- (with concrete regions)
  -----------------------------------------------------------------------------

  -- LAS_SM1
  -- LAS_SM2  
  -- LAS_NEW
  -- LAS_IOB_L (infeasible)
  -- LAS_IOB_H (infeasible)
  function t_CA_L_SI_1(I : Index) return Integer is  begin
    return GA(I);
  end t_CA_L_SI_1;

  -- LAS_SM1
  -- LAS_SM2  
  -- LAS_NEW
  -- LAS_IOB_L (infeasible)
  -- LAS_IOB_H
  function t_CA_L_SI_2(I : Index) return Integer is  begin
    return GA(I + 1);
  end t_CA_L_SI_2;

  -- LAS_SM1
  -- LAS_SM2  
  -- LAS_NEW
  -- LAS_IOB_L 
  -- LAS_IOB_H (infeasible)
  function t_CA_L_SI_3(I : Index) return Integer is  begin
    return GA(I - 1);
  end t_CA_L_SI_3;
  
  
  

  -----------------------------------------------------------------------------
  -- SAC
  -- Concrete-Bounded array store with a concrete index c
  -- (with concrete regions)
  -----------------------------------------------------------------------------
  
  -- SAC_CM
  procedure t_CA_S_CI_1(Val : in Integer) is begin
    GA(1) := Val;
  end t_CA_S_CI_1;
  
  -- SAS_NEW (for GA(I) := -99)
  -- SAC_SM
  -- SAC_NEW
  procedure t_CA_S_CI_2(Val : in Integer; I : in Index) is begin
    GA := ArrayType'(others => 1);
    GA(I) := -99;
    GA(1) := Val; -- should be two cases
  end t_CA_S_CI_2;

  procedure t_CA_S_CI_3(Val : in Integer; I : in Index) is begin
    GA := ArrayType'(others => 8);
    GA(1) := Val; 
    GA(I) := -99; -- should be two cases
  end t_CA_S_CI_3;

  procedure t_CA_Swap_Elements_1(I, J: in Index) is
    Temp : Integer; 
  begin
    Temp := GA(I);
    GA(I) := GA(J);
    GA(J) := Temp;
  end t_CA_Swap_Elements_1;
  
  procedure t_CA_Swap_Elements_2(I, J: in Index) is
    Temp : Integer; 
  begin
    if I /= J then
      Temp := GA(I);
      GA(I) := GA(J);
      GA(J) := Temp;
    end if;
  end t_CA_Swap_Elements_2;
  
  procedure t_CA_Swap_Elements_3(I, J: in Index) is
    Temp : Integer; 
  begin
    if GA(I) /= GA(J) then
      Temp := GA(I);
      GA(I) := GA(J);
      GA(J) := Temp;
    end if;
  end t_CA_Swap_Elements_3;

  procedure t_CA_Swap_Elements_weak(T : in out ArrayType; I, J: in Index) is
    Temp : Integer; 
  begin
    Temp := T(I);
    T(I) := T(J);
    T(J) := Temp;
  end t_CA_Swap_Elements_weak;


  
  -----------------------------------------------------------------------------
  -- SAS
  -- Concrete-Bounded array store with a symbolic index \alpha
  -- (with concrete regions)
  -----------------------------------------------------------------------------
  
  procedure t_CA_S_SI(I : in Index; Val : in Integer) is begin
    GA(I) := Val;
  end t_CA_S_SI;
  


  function helper_CopyOnWrite(A : ArrayType) return ArrayType is 
    APrime : ArrayType;
  begin
    APrime := A;
    APrime(1) := 0;
    return APrime;
  end helper_CopyOnWrite; 
  
  function t_CopyOnWrite return ArrayType is 
    A : ArrayType; 
  begin
    A := ArrayType'(others => 1);
    return helper_CopyOnWrite(A);
  end t_CopyOnWrite; 
  
end ConcreteBounds;