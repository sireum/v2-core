
package ForLoop_FlipHalves
  --#own H;
  --#initializes H;
is
   type Index is range 1 .. 20;
   type Container is array(Index) of Integer;
   
   H: Container := Container'(others=>0);
   
   procedure FlipHalves(K: in Index; V: out Integer);
   --#global in out H;
   --#derives H from * &
   --#        V from H, K;
   
end ForLoop_FlipHalves;

package body ForLoop_FlipHalves
is
   
   procedure FlipHalves(K: in Index; V: out Integer)
   is
      T : Integer;
      M : Index;
   begin
      M := Index'Last/2;
      for I in Index range 1..10 loop
	 T := H(I);
	 H(I) := H(I+M);
	 H(I+M) := T;
      end loop;
      V := H(K);
   end FlipHalves;

end ForLoop_FlipHalves;
