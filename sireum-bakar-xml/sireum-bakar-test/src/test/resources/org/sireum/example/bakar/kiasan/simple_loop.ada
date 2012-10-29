package simple_loop 
  --# own D : Integer;
  --# initializes D;
  is 
  
  procedure addN2D (N : in Integer);
  --# global in out D;
  --# derives D from D, N;
  --# pre n >= 0;
  --# post D = D~ + N;
  
end simple_loop;

package body simple_loop is
  D: Integer;
  
  procedure addN2D (N : in Integer)
  is
  begin
    for i in Integer range 1 .. N loop 
      D := D + 1;  
    end loop;
  end addN2D;
  
begin
  D := 0;
end simple_loop;