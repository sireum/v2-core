package ForLoop_ArrayPartitionedTransfer
  --# own A, B, C;
  --# initializes A, B, C; 
is
  type Index is range 0..9;
  type KeyTable is array (Index) of Integer;
  A: KeyTable :=  KeyTable'(others=>1);
  B: KeyTable :=  KeyTable'(others=>1);
  C: KeyTable :=  KeyTable'(others=>1);

  procedure ArrayPartitionedTransfer(J, K: in Index; X : out Integer);
  --# global in out A; in B; in C;
  --# derives X from A, B, C, K, J &
  --#         A from A, B, C, K;

end ForLoop_ArrayPartitionedTransfer;

package body ForLoop_ArrayPartitionedTransfer
is
   
  procedure ArrayPartitionedTransfer(J, K: in Index; X : out Integer)
  is
  begin
     for I in Index range A'First..K loop
	A(I) := B(I);
     end loop;
     
     for I in Index range K+1..A'Last loop
	A(I) := C(I-k);
     end loop;
     X := A(J);
  end ArrayPartitionedTransfer;

end ForLoop_ArrayPartitionedTransfer;
