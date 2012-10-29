package Binary_Heap is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   function HeapOrdered(T : Array_Type; N:Index_Type) return Boolean;

 
   procedure Insert(Heap : in out Array_Type; N: in out Index_Type; Value: in Contents_Type);
   --# derives Heap from Heap, Value, N & N from N;
   --# pre N < Max_Table_Size and HeapOrdered(Heap,N);
   --# post N = N~ + 1 and HeapOrdered(Heap,N);
   procedure DeleteMin(Heap: in out Array_Type; N: in out Index_Type);
   --# derives Heap from Heap, N & N from N;
   --# pre HeapOrdered(Heap, N);
   --# post N = N~ - 1 and HeapOrdered(Heap, N);
end Binary_Heap;

package body Binary_Heap is
 function HeapOrdered(T: Array_Type; N : Index_Type) return Boolean
    is
       Result: Boolean := True;
  begin
    for I in Index_Type range 1 .. N/2 loop
       if T(I) > T(2*I) then
         Result := False;
       end if;
    end loop;
    for I in Index_Type range 1 .. (N-1)/2 loop
       if T(I) > T(2*I+1) then
         Result := False;
       end if;
    end loop;
    return Result;
  end HeapOrdered;
   procedure Insert(Heap : in out Array_Type;N: in out Index_Type; Value:in Contents_Type) is
      K : Index_Type;
      J : Index_Type;
      Temp: Contents_Type;
     begin 
       Heap(N+1) := Value;
       K := N + 1;
       loop 
         J := K;
         if J > 1 and Heap(J/2) > Heap(K) then
           K := J/2;
           Temp := Heap(J);
           Heap(J) := Heap(K);
           Heap(k) := Temp;
         end if;
         exit when J = K;
      end loop;
      N := N + 1;
   end Insert;
   
   procedure DeleteMin(Heap: in out Array_Type; N: in out Index_Type) is
     K: Index_Type;
     J: Index_Type;
     Temp: Contents_Type;
   begin
     Heap(1) := Heap(N);
     K := 1;
     loop
       J := 2*K;
       if J + 1 <= N - 1 and Heap(J+1) < Heap(J) then
         J := J + 1;
       end if;
       if J <= N - 1 and Heap(J) < Heap(K) then
         Temp := Heap(J);
         Heap(J) := Heap(K); 
         Heap(K) := Temp;
         K := J;
       end if;
       exit when J /= K;
     end loop;
     N:= N - 1;
   end DeleteMin;
end Binary_Heap;
