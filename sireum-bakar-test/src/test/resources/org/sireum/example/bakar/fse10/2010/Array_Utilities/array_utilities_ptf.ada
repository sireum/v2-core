--# inherit Array_Utilities;
package Array_Utilities.PTF is
   ---------------------------------------------------------------------------
   -- Helper proof functions
   ---------------------------------------------------------------------------

   function Elem_Count_In_Range(A: Array_Utilities.Array_Type;
                                E: Array_Utilities.Contents_Type;
                                L, U: Array_Utilities.Index_Type)
                               return Natural;
   --# return C => (A(L) = E ->  C = Elem_Count_In_Range(A,E,L+1,U) + 1) and
   --#             (A(L) /= E -> C = Elem_Count_In_Range(A,E,L+1,U));

   function Elem_Count(A: Array_Utilities.Array_Type;
                       E: Array_Utilities.Contents_Type) return Natural;
   --# return Elem_Count_In_Range(A, E,
   --#           Array_Utilities.Index_Type'First,
   --#           Array_Utilities.Index_Type'Last);

   ---------------------------------------------------------------------------
   -- Proof functions used in Array_Utilities
   ---------------------------------------------------------------------------

   function Ordered(A: Array_Utilities.Array_Type;
                    L,U: Array_Utilities.Index_Type) return Boolean;
   --# return for all I in Array_Utilities.Index_Type range L .. U - 1
   --#         => (A(I) <= A(I+1));

   function Perm(A, B : Array_Utilities.Array_Type) return Boolean;
   --# return I => (for all J in Array_Utilities.Index_Type
   --#              => (Elem_Count(A, A(J)) = Elem_Count(B, B(J))));

end Array_Utilities.PTF;

--
--============================================================================
--

package body Array_Utilities.PTF is

   function Elem_Count_In_Range(A: Array_Utilities.Array_Type;
                                E: Array_Utilities.Contents_Type;
                                L, U: Array_Utilities.Index_Type)
                               return Natural
   is
      Result: Integer := 0;
   begin
      for I in Array_Utilities.Index_Type range L .. U loop
         if A(I) = E then
            Result := Result + 1;
         end if;
      end loop;
      return Result;
   end Elem_Count_In_Range;

   function Elem_Count(A : Array_Utilities.Array_Type; E : Array_Utilities.Contents_Type) return Natural
   is
   begin
      return Elem_Count_In_Range(A, E,
                                 Array_Utilities.Index_Type'First,
                                 Array_Utilities.Index_Type'Last);
   end Elem_Count;

   function Ordered(A: Array_Utilities.Array_Type; L,U : Array_Utilities.Index_Type) return Boolean
   is
      Result: Boolean := True;
   begin
      for I in Array_Utilities.Index_Type range L .. U - 1 loop
         if A(I) > A(I + 1) then
            Result := False;
         end if;
      end loop;
      return Result;
   end Ordered;

   function Perm(A, B : Array_Utilities.Array_Type) return Boolean is
      Result: Boolean := True;
   begin
      for I in Array_Utilities.Index_Type loop
         if Elem_Count(A, A(I)) /= Elem_Count(B, A(I)) then
            Result := False;
         end if;
      end loop;
      return Result;
   end Perm;

end Array_Utilities.PTF;

