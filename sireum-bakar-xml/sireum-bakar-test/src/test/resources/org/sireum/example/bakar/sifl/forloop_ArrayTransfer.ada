package ForLoop_ArrayTransfer
  --#own A, B;
  --#initializes A, B;
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  A: KeyTable := KeyTable'(others=>1);
  B: KeyTable := KeyTable'(others=>1);
  
  procedure ArrayTransfer(K: in Index; X : out Integer);
  --# global in B; in out A;
  --# derives X from A, B, K &
  --#         A from A, B;

end ForLoop_ArrayTransfer;

package body ForLoop_ArrayTransfer
is
   
  procedure ArrayTransfer(K: in Index; X : out Integer)
  is
  begin
     for I in Index loop
       A(I) := B(I);
     end loop;
     X := A(K);
     
  end ArrayTransfer;

end ForLoop_ArrayTransfer;
