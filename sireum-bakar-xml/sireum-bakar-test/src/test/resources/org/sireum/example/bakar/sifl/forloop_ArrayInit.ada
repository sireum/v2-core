package ForLoop_ArrayInit
  --# own A;
  --# initializes A;
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  A: KeyTable := KeyTable'(others=>1);
  
  procedure ArrayInit(K: in Index; X : out Integer);
  --# global in out A;
  --# derives X from A, K &
  --#         A from A; 

end ForLoop_ArrayInit;

package body ForLoop_ArrayInit
is
   
   procedure ArrayInit(K: in Index; X : out Integer)
   is
   begin
     for I in Index loop
       A(I) :=0;
     end loop;
     X := A(K);
  end ArrayInit;

end ForLoop_ArrayInit;
