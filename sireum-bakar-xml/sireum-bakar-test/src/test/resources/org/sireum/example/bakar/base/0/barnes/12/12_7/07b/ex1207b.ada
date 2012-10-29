package C
  --# own State;
  --# initializes State;
is
   procedure Calibrate(Raw : in Integer;
                       Cal : out Integer);
   --# global in out State;
   --# derives Cal, State from State, Raw;
end C;

with C;
--# inherit C;
--# main_program;
procedure Two_Press(Raw_1, Raw_2 : in Integer;
                    Cal_1, Cal_2 : out Integer)
  --# global in out C.State;
  --# derives Cal_1 from Raw_1, C.State &
  --#         Cal_2 from Raw_2, C.State &
  --#         C.State from Raw_1,Raw_2, C.State;
is
begin
   C.Calibrate(Raw_1,Cal_1);
   C.Calibrate(Raw_2,Cal_2);
end Two_Press;

