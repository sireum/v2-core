

separate (Lift_Control)
procedure Traverse(Direction: in Building.Direction_Type) is
   Lift_Called: Boolean;
   Next_Stop: Building.Floor_Type;

   procedure Poll(Direction: in Building.Direction_Type;
                  Lift_Called: out Boolean;
                  Next_Stop: out Building.Floor_Type)
   --# global in Lift.State;
   --#        in out Buttons.Button_History;
   --# derives Lift_Called, Next_Stop, Buttons.Button_History
   --#               from Direction, Buttons.Button_History, Lift.State;
   is separate;

begin   -- Traverse
   loop
      Poll(Direction, Lift_Called, Next_Stop);
      exit when not Lift_Called;
      Lift.Move(Next_Stop);
      Building.Open_Door(Lift.Present_Floor);
      Building.Close_Door(Lift.Present_Floor);
      Buttons.Clear_Lift_Button(Lift.Present_Floor);
      Buttons.Clear_Floor_Button(Direction, Lift.Present_Floor);
   end loop;
   Buttons.Clear_Floor_Button(Building.Up, Lift.Present_Floor);
   Buttons.Clear_Floor_Button(Building.Down, Lift.Present_Floor);
end Traverse;
