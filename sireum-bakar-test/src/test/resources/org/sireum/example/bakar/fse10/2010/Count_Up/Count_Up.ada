package Loops
is
  procedure Count_Up (N: in Integer; M: out Integer);
  --# derives M from N;
  --# pre N >= 0;
  --# post M = N;
end Loops;

package body Loops
is
  procedure Count_Up (N: in Integer; M: out Integer) is
    I : Integer;
  begin
    I := N;
    M := 0;
    while 0 < I loop
	    --# assert M = N - I
	    --#    and 0 < I and I <= N;
	    M := M + 1;
	    I := I - 1;
    end loop;
  end Count_Up;
end Loops;
