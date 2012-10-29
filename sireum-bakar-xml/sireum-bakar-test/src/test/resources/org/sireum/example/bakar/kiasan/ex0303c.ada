package ch3 is
  procedure Fault_Integrator(Fault_Found : in Boolean;
                             Trip : in out Boolean;
                             Counter : in out Integer);
  --# derives Trip from Fault_Found, Trip, Counter &
  --#         Counter from Fault_Found, Counter;
  
  procedure Fault_Integrator_bad(Fault_Found : in Boolean;
                             Trip : in out Boolean;
                             Counter : in out Integer);
  --# derives Trip from Fault_Found, Trip, Counter &
  --#         Counter from Fault_Found, Counter;  
end ch3;

package body ch3 is
  -- Kiasan for Spark is not currently handling constants
  
  -- Upper_Limit : constant Integer := 3_500;
  -- Lower_Limit : constant Integer := -20;
  -- Up_Rate     : constant Integer := 5;
  -- Down_Rate   : constant Integer := 3;

  procedure Fault_Integrator(Fault_Found : in Boolean;
                             Trip : in out Boolean;
                             Counter : in out Integer)
  is
    Upper_Limit : Integer;
    Lower_Limit : Integer;
    Up_Rate : Integer;
    Down_Rate : Integer;
  begin
    Upper_Limit := 3_500;
    Lower_Limit := -20;
    Up_Rate := 5;
    Down_Rate := 3;
    
    if Fault_Found then
      Counter := Counter + Up_Rate;
      if Counter >= Upper_Limit then
        Trip := True; Counter := Upper_Limit;
      end if;
    else
      Counter := Counter - Down_Rate;
      if Counter <= Lower_Limit then
        Trip := False; Counter := Lower_Limit;
      end if;
    end if;
  end Fault_Integrator;
  
  procedure Fault_Integrator_bad(Fault_Found : in Boolean;
                             Trip : in out Boolean;
                             Counter : in out Integer)
  is
    Upper_Limit : Integer;
    Lower_Limit : Integer;
    Up_Rate : Integer;
    Down_Rate : Integer;
  begin
    Upper_Limit := 3_500;
    Lower_Limit := -20;
    Up_Rate := 5;
    Down_Rate := 3;
    
    if Fault_Found then
      Counter := Counter + Up_Rate;
      if Counter >= Upper_Limit then
        Trip := True; Counter := Upper_Limit;
      end if;
    else
      Counter := Counter - Down_Rate;
      if Fault_Found then
        Trip := False; Counter := Lower_Limit;
      end if;
    end if;
  end Fault_Integrator_bad;  
end ch3;
