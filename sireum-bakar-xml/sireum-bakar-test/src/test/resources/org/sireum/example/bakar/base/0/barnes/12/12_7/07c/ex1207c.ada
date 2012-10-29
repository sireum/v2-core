package C
  --# own State, Test_Point;
  --# initializes State,Test_Point;
is
   procedure Calibrate(Raw : in Integer;
                       Cal : out Integer);
   --# global in State; in out Test_Point;
   --# derives Cal from State, Raw &
   --#         Test_Point from Test_Point,State, Raw;
end C;

with C;
--# inherit C;
--# main_program;
procedure Two_Press(Raw_1, Raw_2 : in Integer;
                    Cal_1, Cal_2 : out Integer)
  --# global in C.State; in out C.Test_Point;
  --# derives Cal_1 from Raw_1, C.State &
  --#         Cal_2 from Raw_2, C.State &
  --#         C.Test_Point from Raw_1,Raw_2, C.State, C.Test_Point;
is
begin
   C.Calibrate(Raw_1,Cal_1);
   C.Calibrate(Raw_2,Cal_2);
end Two_Press;

