--# inherit Binary_Heap;
package Binary_Heap.PTF is
   ---------------------------------------------------------------------------
   -- Helper proof functions
   ---------------------------------------------------------------------------

   function HeapOrdered(T : Binary_Heap.Array_Type; N:Binary_Heap.Index_Type) return Boolean;
   --# pre N <= Binary_Heap.Max_Table_Size;
   --# return (for all I in Binary_Heap.Index_Type range 1 ..N/2 
   --#         => (T(I) <= T(2*I))) and
   --#        (for all I in Binary_Heap.Index_Type range 1..(N-1)/2
   --#        => (T(I) <= T(2*I + 1)));

end Binary_Heap.PTF;

package body Binary_Heap.PTF is

  function HeapOrdered(T: Binary_Heap.Array_Type; N : Binary_Heap.Index_Type) return Boolean
    is
       Result: Boolean := True;
  begin
    for I in Binary_Heap.Index_Type range 1 .. N/2 loop
       if T(I) > T(2*I) then
         Result := False;
       end if;
    end loop;
    for I in Binary_Heap.Index_Type range 1 .. (N-1)/2 loop
       if T(I) > T(2*I+1) then
         Result := False;
       end if;
    end loop;
    return Result;
  end HeapOrdered;
end Binary_Heap.PTF;
