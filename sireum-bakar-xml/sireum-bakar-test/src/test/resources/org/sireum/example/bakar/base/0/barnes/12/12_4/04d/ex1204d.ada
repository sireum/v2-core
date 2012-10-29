-- Pressure depends on Temperature
package Temperature
  --# own State;
  --# initializes State;
is
   function Value return Integer;
   --# global State;

   procedure Calculate;
   --# global in out State;
   --# derives State from State;
end Temperature;

--# inherit Temperature;
package Pressure
  --# own State;
  --# initializes State;
is
   function Value return Integer;
   --# global State;

   procedure Calculate;
   --# global in out State; in Temperature.State;
   --# derives State from State, Temperature.State;
end Pressure;

