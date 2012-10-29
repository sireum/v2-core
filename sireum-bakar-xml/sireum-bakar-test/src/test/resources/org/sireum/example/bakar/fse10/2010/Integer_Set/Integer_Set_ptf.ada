-- Definition of proof functions. Note that this
-- is checked against the Integer_Set shadown.
--
-- @author Patrice Chalin, SAnToS Lab & DSRG.org.

with Model;
--# inherit Model, Integer_Set;
package Integer_Set.PTF
is
   function Invariant return Boolean;
   --# global in Integer_Set.Member_Count;
   --# return Integer_Set.Member_Count in Integer_Set.Size_Range;

   function Represents(Elem_Array : Integer_Set.Elem_Array_Type;
                       Member_Count : Integer_Set.Size_Range) return Model.Z_Set;

   function Rep_Invariant(Elem_Array : Integer_Set.Elem_Array_Type;
                          Member_Count : Integer_Set.Size_Range) return Boolean;
   --# return for all I in Integer range Integer_Set.Index_Type'First .. Member_Count =>
   --#          (for all J in Integer range Integer_Set.Index_Type'First .. Member_Count =>
   --#            (I /= J -> Elem_Array(I) /= Elem_Array(J)));

end Integer_Set.PTF;

package body Integer_Set.PTF
is
   function Invariant return Boolean
   is begin
      return -- Model.Z_Set_Size(Represents(Elem_Array, Member_Count))
        Integer_Set.Member_Count
                                in Integer_Set.Size_Range;
   end Invariant;

   function Represents(Elem_Array : Integer_Set.Elem_Array_Type;
                       Member_Count : Integer_Set.Size_Range) return Model.Z_Set
   is
      Result: Model.Z_Set;
   begin
      Result := Model.Z_Set_Empty;
      for I in Integer_Set.Index_Type
        range Integer_Set.Index_Type'First .. Member_Count
      loop
         Result := Model.Z_Set_Add(Elem_Array(I), Result);
         --# assert Model.Z_Set_Size(Result) = I and
         --#   (for all J in Integer_Set.Index_Type range 1 .. I =>
         --#      (Model.Z_Set_IsMember(Elem_Array(J), Result)));
      end loop;
      return Result;
   end represents;

   function Rep_Invariant(Elem_Array : Integer_Set.Elem_Array_Type;
                          Member_Count : Integer_Set.Size_Range) return Boolean
   is
      Result: Boolean := true;
   begin
      for I in Integer_Set.Index_Type
        range Integer_Set.Index_Type'First .. Member_Count
      loop
         for J in Integer_Set.Index_Type
           range I+1 .. Member_Count
         loop
            Result := Result and Elem_Array(I) /= Elem_Array(J);
            --# assert Result <->
            --# (for all I2 in Integer range Integer_Set.Index_Type'First .. I =>
            --#          (for all J2 in Integer range I+1 .. J =>
            --#            (Elem_Array(I2) /= Elem_Array(J2))));
         end loop;
         --# assert Result <->
         --# (for all I2 in Integer range Integer_Set.Index_Type'First .. I =>
         --#          (for all J2 in Integer range I+1 .. Member_Count =>
         --#            (Elem_Array(I2) /= Elem_Array(J2))));
      end loop;
      return Result;
   end Rep_Invariant;


end Integer_Set.PTF;
