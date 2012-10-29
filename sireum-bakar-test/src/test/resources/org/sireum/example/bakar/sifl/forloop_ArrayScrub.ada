package ForLoop_ArrayScrub
  --# own A;
  --# initializes A;
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  A: KeyTable := KeyTable'(others => 1);
  
  procedure ArrayScrub(K: in Index; X : out Integer);
  --# global in out A;
  --# derives X from A, K &
  --#         A from A;
  
end ForLoop_ArrayScrub;

package body ForLoop_ArrayScrub
is
   
  procedure ArrayScrub(K: in Index; X : out Integer)
  is
  begin
     for I in Index loop
	if A(I) mod 2 = 0
	then 
	   A(I) := 0;
	end if;
     end loop;
     X := A(K);
  end ArrayScrub;

end ForLoop_ArrayScrub;
