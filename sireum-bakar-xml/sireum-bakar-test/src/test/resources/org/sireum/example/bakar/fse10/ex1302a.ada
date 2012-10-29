package Odometer
  --# own Trip, Total : Integer;
is
   procedure Zero_Trip;
   --# global out Trip;
   --# derives Trip from;

   function Read_Trip return Integer;
   --# global in Trip;

   function Read_Total return Integer;
   --# global in Total;

   procedure Inc;
   --# global in out Trip, Total;
   --# derives Trip from Trip & Total from Total;

private -- cannot use private subprograms in SPARK currently

   procedure Set_Trip(T : Integer);
   --# global out Trip;
   --# derives Trip from T;

   procedure Set_Total(T : Integer);
   --# global out Total;
   --# derives Total from T;
end Odometer;

--# inherit Odometer;
package Odometer.Test is
   procedure Zero_Totals;
   --# global out Odometer.Trip, Odometer.Total;
   --# derives Odometer.Trip, Odometer.Total from;

   procedure Add(X : Integer);
   --# global in out Odometer.Trip, Odometer.Total;
   --# derives Odometer.Trip from *, X &
   --#         Odometer.Total from *, X;
end Odometer.Test;

package body Odometer.Test is
   procedure Zero_Totals is
   begin
      Odometer.Set_Trip(0);
      Odometer.Set_Total(0);
   end Zero_Totals;

   procedure Add(X : Integer) is
   begin
      Odometer.Set_Trip(Odometer.Read_Trip + X);
      Odometer.Set_Total(Odometer.Read_Total + X);
   end Add;
end Odometer.Test;


