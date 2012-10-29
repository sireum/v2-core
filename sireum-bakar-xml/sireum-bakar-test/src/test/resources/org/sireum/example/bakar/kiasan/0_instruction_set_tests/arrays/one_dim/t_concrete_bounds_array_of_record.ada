package ConcreteBoundsArrayOfRecords
--# own GA;
--# initializes GA;
is

  type Box is
  record
    Length : Integer;
    Width : Integer;
  end record;
  
  type Index is range -5 .. 5;
  type BoxArrayType is array(Index) of Box;

  GA : BoxArrayType := BoxArrayType'(others => Box'(1,1));
  
  procedure t_Init;
  --# global out GA;
  --# derives GA from ;
  
  procedure t_swap_elements_1(I, J : in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_swap_elements_2(I, J : in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_swap_elements_3(I, J : in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_swap_elements_4(I, J : in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;
  
  procedure t_swap_elements_5(I, J : in Index);
  --# global in out GA;
  --# derives GA from GA, I, J;  
    
end ConcreteBoundsArrayOfRecords;

--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------
--------------------------------------------------------------------------------

package body ConcreteBoundsArrayOfRecords is
  
  procedure t_Init is begin
    GA := BoxArrayType'(others => Box'(1,1));
  end t_Init;
  
  procedure t_swap_elements_1(I, J : in Index) 
  is
    Temp : Box; 
  begin
    Temp := GA(I);
    GA(I) := GA(J);
    GA(J) := Temp;
  end t_swap_elements_1;
  
  procedure t_swap_elements_2(I, J : in Index) 
  is
    Temp : Box; 
  begin
    if I /= J then
      Temp := GA(I);
      GA(I) := GA(J);
      GA(J) := Temp;
    end if;
  end t_swap_elements_2;
  
  procedure t_swap_elements_3(I, J : in Index) 
  is
    Temp1, Temp2 : Integer;
  begin
    if I /= J and (GA(I).Length /= GA(J).Length or GA(I).Width /= GA(J).Width) then
      Temp1 := GA(I).Length;
      Temp2 := GA(I).Width;
      GA(I).Length := GA(J).Length;
      GA(I).Width := GA(J).Width;
      GA(J).Length := Temp1;
      GA(J).Width := Temp2;
    end if;
  end t_swap_elements_3;

  procedure t_swap_elements_4(I, J : in Index) 
  is
    Temp : Box;
  begin
    if I /= J and (GA(I).Length /= GA(J).Length or GA(I).Width /= GA(J).Width) then
      Temp := GA(I);
      GA(I) := GA(J);
      GA(J) := Temp; 
    end if;
  end t_swap_elements_4;
    
  procedure t_swap_elements_5(I, J : in Index) 
  is
    Temp : Box; 
  begin
    if GA(I) /= GA(J) then
      Temp := GA(I);
      GA(I) := GA(J);
      GA(J) := Temp;
    end if;
  end t_swap_elements_5;  
  
end ConcreteBoundsArrayOfRecords;