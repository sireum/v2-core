package Example is
    procedure add ( x : in out Integer; y : in Integer ) ;
    --# derives x from x,y;    procedure foo ( A : in out Integer; B : in out Integer ) ;
end example;

package body example is

  procedure add (x : in out Integer; y : in Integer)
  is
    begin
     x := x + y;
  end add;
  
  procedure foo (A : in out Integer ; B : in out Integer )
    --# derives A from A 	
    --#	   & B from B;
  is
  M : Integer;
     begin
     M := 3;
     add(A,M);
     add(B,5);
     A := A + 2;
     B := B + 5;
  end foo;
end example;  
