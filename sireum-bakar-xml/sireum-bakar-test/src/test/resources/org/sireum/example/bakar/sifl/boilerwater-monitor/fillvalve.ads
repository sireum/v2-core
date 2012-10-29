with Valve;
--# inherit Valve;
package FillValve
--# own out State;
is

  procedure SetTo(Setting : in     Valve.T);
  --# global out State;
  --# derives State from Setting;

end FillValve;
