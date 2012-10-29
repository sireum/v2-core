

with Building; use type Building.Floor_Type;
separate (Lift_Control.Traverse)
procedure Poll(Direction: in Building.Direction_Type;
               Lift_Called: out Boolean;
               Next_Stop: out Building.Floor_Type) is

   Requested: Boolean;
   Final_Floor, Inspected_Floor: Building.Floor_Type;
   Reverse_Direction: Building.Direction_Type;

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


