package Random_Numbers
  --# own Seed;
is

   procedure Start_Seed(New_Seed : in Integer);
   --# global out Seed;
   --# derives Seed from New_Seed;

   procedure Random(X : out Float);
   --# global in out Seed;
   --# derives X, Seed from Seed;

end Random_Numbers;

package body Random_Numbers
is
   Seed : Integer;
   Seed_Max : constant Integer := 14489;

   procedure Start_Seed(New_Seed : in Integer)
   is
   begin
      Seed := New_Seed mod Seed_Max;
   end Start_Seed;

   procedure Random(X : out Float) is
   begin
      Seed := (Seed * 6481) mod Seed_Max;
      X := Float(Seed) / Float(Seed_Max);
   end Random;

end Random_Numbers;

