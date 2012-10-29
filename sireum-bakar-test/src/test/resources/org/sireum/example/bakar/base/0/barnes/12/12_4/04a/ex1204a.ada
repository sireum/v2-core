package Voltage
  --# own State;
  --# initializes State;
is
   procedure Read(Level : out Integer);
   --# global in out State;
   --# derives Level, State from State;
end Voltage;

package Current
  --# own State;
  --# initializes State;
is
   procedure Read(Level : out Integer);
   --# global in out State;
   --# derives Level, State from State;
end Current;

package Phase
  --# own State;
  --# initializes State;
is
   procedure Read(Level : out Integer);
   --# global in out State;
   --# derives Level, State from State;
end Phase;

--# inherit Voltage, Current, Phase;
package Power is

   procedure Read_Power(Level : out Integer);
   --# global in out Voltage.State, Current.State, Phase.State;
   --# derives
   --#       Level
   --#       from
   --#           Voltage.State,
   --#           Current.State,
   --#           Phase.State &
   --#       Voltage.State,
   --#       Current.State,
   --#       Phase.State
   --#       from
   --#           *;

end Power;





