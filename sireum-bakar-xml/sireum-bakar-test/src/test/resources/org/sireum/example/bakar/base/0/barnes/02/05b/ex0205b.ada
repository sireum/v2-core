package Random_Numbers
  --# own Seed;
  --# initializes Seed;
is

   procedure Random(X : out Float);
   --# global in out Seed;
   --# derives X, Seed from Seed;

end Random_Numbers;

package body Random_Numbers
is
   Seed : Integer;
   Seed_Max : constant Integer := 14489;

   procedure Random(X : out Float) is
   begin
      Seed := (Seed * 6481) mod Seed_Max;
      X := Float(Seed) / Float(Seed_Max);
   end Random;

begin
   Seed := 12345;
end Random_Numbers;

