--# inherit Insertion_Sort;
package Insertion_Sort.PTF is
   ---------------------------------------------------------------------------
   -- Helper proof functions
   ---------------------------------------------------------------------------
   function Elem_Count_In_Range(A: Insertion_Sort.Array_Type;
                       E: Insertion_Sort.Contents_Type; L,U : Insertion_Sort.Index_Type) return Natural;
   --#return C => (A(L) = E ->  C = Elem_Count_In_Range(A,E,L+1,U) + 1) and
   --#             (A(L) /= E -> C = Elem_Count_In_Range(A,E,L+1,U));


   function Ordered(A : Insertion_Sort.Array_Type; L,U : Insertion_Sort.Index_Type) return Boolean;
   --# pre 1 <= L and L <= U and U <= Insertion_Sort.Max_Table_Size;
   --# return for all I in Insertion_Sort.Index_Type range L .. U - 1
   --#         => (A(I) <= A(I+1));

   function Perm(A, B : Insertion_Sort.Array_Type; L,U : Insertion_Sort.Index_Type) return Boolean;
   --# return I => (for all J in Insertion_Sort.Index_Type
   --#              => (Elem_Count_In_Range(A, A(J),L,U) = Elem_Count_In_Range(B, B(J),L,U)));


end Insertion_Sort.PTF;

package body Insertion_Sort.PTF is
   function Elem_Count_In_Range(A: Insertion_Sort.Array_Type;
                                E: Insertion_Sort.Contents_Type;
                                L, U: Insertion_Sort.Index_Type)
                               return Natural
   is
      Result: Integer := 0;
   begin
      for I in Insertion_Sort.Index_Type range L .. U loop
         if A(I) = E then
            Result := Result + 1;
         end if;
      end loop;
      return Result;
   end Elem_Count_In_Range;

  function Perm(A, B : Insertion_Sort.Array_Type;L,U:Insertion_Sort.Index_Type) return Boolean is
      Result: Boolean := True;
   begin
      for I in Insertion_Sort.Index_Type range L..U loop
         if Elem_Count_In_Range(A, A(I),L,U) /= Elem_Count_In_Range(B, A(I),L,U) then
            Result := False;
         end if;
      end loop;
      return Result;
   end Perm;


  function Ordered(A: Insertion_Sort.Array_Type; L,U : Insertion_Sort.Index_Type) return Boolean
    is
       Result: Boolean := True;
  begin
    for I in Insertion_Sort.Index_Type range L .. U - 1 loop
       if A(I) > A(I + 1) then
         Result := False;
       end if;
    end loop;
    return Result;
  end Ordered;
end Insertion_Sort.PTF;
