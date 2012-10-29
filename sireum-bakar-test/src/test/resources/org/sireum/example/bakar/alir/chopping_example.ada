package chopping_example is
    
    procedure add ( x : in out Integer; y : in Integer ) ;
    --# derives x from x,y;
    procedure foo ( A : in out Integer; B : in out Integer ) ;
    --# derives A from A
    --# & B from B;
end example;

package body chopping_example is
    
    procedure add (x : in out Integer; y : in Integer)
    is
        begin
        x := x + y;
    end add;
    
    procedure foo (A : in out Integer ; B : in out Integer )
    is
        M : Integer;
        N : Integer;
        begin
        M := 3;
        N := 5;
        add(A,M);
        add(B,N);
        A := A + 2;
        B := B + 5;
    end foo;
end example;
