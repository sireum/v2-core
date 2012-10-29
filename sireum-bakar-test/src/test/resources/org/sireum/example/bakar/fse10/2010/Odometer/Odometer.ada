package Odometer
  --# own Trip, Total : Integer;
is
   procedure Zero_Trip;
   --# global out Trip;
   --# derives Trip from;
   --# post Trip = 0;

   function Read_Trip return Integer;
   --# global in Trip;
   --# return Trip;

   function Read_Total return Integer;
   --# global in Total;
   --# return Total;

   procedure Inc;
   --# global in out Trip, Total;
   --# derives Trip from Trip & Total from Total;
   --# pre  Trip + 1 < Integer'Last and
   --#      Total + 1 < Integer'Last;
   --# post Trip = Trip~ + 1 and
   --#      Total = Total~ + 1;

private

   procedure Set_Trip(T : Integer);
   --# global out Trip;
   --# derives Trip from T;
   --# post Trip = T;

   procedure Set_Total(T : Integer);
   --# global out Total;
   --# derives Total from T;
   --# post Total = T;

end Odometer;

package body Odometer is
   Trip : Integer;
   Total : Integer;

   procedure Zero_Trip is
   begin
      Trip := 0;
   end Zero_Trip;

   function Read_Trip return Integer is
   begin
      return Trip;
   end Read_Trip;

   function Read_Total return Integer is
   begin
      return Total;
   end Read_Total;

   procedure Inc is
   begin
      Trip := Trip + 1;
      Total := Total + 1;
   end Inc;

   procedure Set_Trip (T: Integer) is
   begin
      Trip := T;
   end Set_Trip;

   procedure Set_Total (T: Integer) is
   begin
      Total := T;
   end Set_Total;

end Odometer;
