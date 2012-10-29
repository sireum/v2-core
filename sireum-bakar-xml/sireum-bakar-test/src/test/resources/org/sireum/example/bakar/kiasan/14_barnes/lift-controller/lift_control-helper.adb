package body Lift_Control_Helper
is

   function Next_Floor(Current_Floor: Building.Floor_Type;
                       Direction: Building.Direction_Type)
                       return Building.Floor_Type
   is
      Next_Floor_Value: Building.Floor_Type;
   begin   -- Next_Floor;
      case Direction is
         when Building.Up =>
            Next_Floor_Value := Building.Floor_Type'Succ(Current_Floor);
         when Building.Down =>
            Next_Floor_Value := Building.Floor_Type'Pred(Current_Floor);
      end case;
      return Next_Floor_Value;
   end Next_Floor;
   
procedure Poll(Direction: in Building.Direction_Type;
               Lift_Called: out Boolean;
               Next_Stop: out Building.Floor_Type) 
   --# global in Lift.State;
   --#        in out Buttons.Button_History;
   --# derives Lift_Called, Next_Stop, Buttons.Button_History
   --#               from Direction, Buttons.Button_History, Lift.State;               
is

   Requested: Boolean;
   Final_Floor, Inspected_Floor: Building.Floor_Type;
   Reverse_Direction: Building.Direction_Type;
begin   -- Poll
   case Direction is
      when Building.Up =>
         Final_Floor := Building.Floor_Type'Last;
         Reverse_Direction := Building.Down;
      when Building.Down =>
         Final_Floor := Building.Floor_Type'First;
         Reverse_Direction := Building.Up;
   end case;
   Requested := False;
   Inspected_Floor := Lift.Present_Floor;
   loop
      exit when Inspected_Floor = Final_Floor;
      Inspected_Floor := Next_Floor(Inspected_Floor, Direction);
      Buttons.Inspect_Floor_Button(Direction,
                                   Inspected_Floor, Requested);
      exit when Requested;
      Buttons.Inspect_Lift_Button(Inspected_Floor, Requested);
      exit when Requested;
   end loop;
   if not Requested then
      loop
         exit when Inspected_Floor = Lift.Present_Floor;
         Buttons.Inspect_Floor_Button(Reverse_Direction,
                                      Inspected_Floor, Requested);
         exit when Requested;
         Inspected_Floor := Next_Floor(Inspected_Floor,
                                       Reverse_Direction);
      end loop;
   end if;
   Lift_Called := Requested;
   Next_Stop := Inspected_Floor;
end Poll;


procedure Traverse(Direction: in Building.Direction_Type) is
   Lift_Called: Boolean;
   Next_Stop: Building.Floor_Type;
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

end Lift_Control_Helper;