-- Realization of a single Integer set using a fixed size array.
-- Functional specs are written using Spark proof type and functions.
--
-- @author Patrice Chalin, SAnToS Lab & DSRG.org.

package Integer_Set
  --# own State: Z_Set;
is
   subtype Element_Type is Integer;
   subtype Size_Range is Integer range 0 .. 100;

   --# type Z_Set is abstract;
   --# function Z_Set_Empty return Z_Set;
   --# function Z_Set_Add(e: Element_Type; s: Z_Set) return Z_Set;
   --# function Z_Set_Remove(e: Element_Type; s: Z_Set) return Z_Set;
   --# function Z_Set_Size(s: Z_Set) return Integer;
   --# function Z_Set_IsEmpty(s: Z_Set) return Boolean;
   --# function Z_Set_IsMember(e: Element_Type; s: Z_Set) return Boolean;

   --# function Invariant return Boolean; -- package invariant
   --! global in State;
   --! return Z_Set_Size(State) in Size_Range

   function Size return Size_Range;
   --# global in State;
   --# return Z_Set_Size(State);

   function isFull return Boolean;
   --# global in State;
   --# return Size(State) = Size_Range'Last;

   function isMember(E: in Element_Type) return Boolean;
   --# global in State;
   --# return Z_Set_IsMember(E, State);

   procedure Add(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;
   --# pre not isFull(State);
   --# post State = Z_Set_Add(E, State~);

   procedure Remove(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;
   --# pre Z_Set_IsEmpty(State);
   --# post State = Z_Set_Remove(E, State~);

   procedure Empty;
   --# global out State;
   --# derives State from;
   --# post Z_Set_IsEmpty(State);

end Integer_Set;

package body Integer_Set
  --# own State is Elem_Array, Member_Count;
is
   subtype Index_Type is Size_Range range 1 .. Size_Range'Last;
   type Elem_Array_Type is array (Index_Type) of Element_Type;

   Elem_Array : Elem_Array_Type;
   Member_Count : Size_Range;

   --# function Represents(ea: Elem_Array_Type; mc: Size_Range) return Z_Set;
   --! return { ea[i] | I in Integer range 1 .. mc };

   -- The representation inv is not strictly necessary, but is useful to test
   -- the idea of having one.
   --# function Rep_Invariant(ea: Elem_Array_Type; mc: Size_Range) return Boolean;
   --  that each member appears exactly once in the element array
   -- = for all I, J in Integer range Index_Type'First .. Member_Count =>
   --   Elem_Array(I) = Elem_Array(J) -> I = J

   -- End of ADS4PFT

   function Size return Size_Range
     --# global in Member_Count;
     --# return Member_Count;
   is begin
      return Member_Count;
   end Size;

   function isFull return Boolean
     --# global in Member_Count;
     --# return Member_Count = Size_Range'Last;
   is begin
      return Size = Size_Range'Last;
   end IsFull;

   function Get_Element_Index(E: in Element_Type) return Size_Range
     --# global in Elem_Array, Member_Count;
     --# return I =>
     --# (Z_Set_IsMember(E, represents(Elem_Array, Member_Count))
     --#   -> (I in 1 .. Member_Count and Elem_Array(I) = E))
     --# and
     --#   (not Z_Set_IsMember(E, represents(Elem_Array, Member_Count))
     --#    -> I = 0);
   is
      I : Integer := Index_Type'First;
   begin
      while I <= Member_Count and then Elem_Array (I) /= E loop
         --# assert I in Index_Type'First .. Member_Count + 1
         --# and (for all J in Integer range
         --#        Index_Type'First .. I-1 => (
         --#          Elem_Array(J) /= E));
         I := I + 1;
      end loop;
      if I > Member_Count then
         I := 0;
     end if;
     return I;
   end Get_Element_Index;

   function isMember(E: in Element_Type) return Boolean
     --# global in Elem_Array, Member_Count;
     --# return for some I in Integer range
     --#          Index_Type'First .. Member_Count =>
     --#            (Elem_Array(I) = E);
   is
   begin
      return Get_Element_Index(E) /= 0;
   end isMember;

   procedure Add(E: Element_Type)
     --# global in out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from E, Elem_Array, Member_Count;
     --# pre Member_Count < Size_Range'Last;
     --# post (isMember(E, Elem_Array~, Member_Count~) -> (
     --#       Elem_Array = Elem_Array~ and Member_Count = Member_Count~));
     --! commenting these out as they contain array updates
     --  and (not isMember(E, Elem_Array~, Member_Count~) -> (
     --       Elem_Array = Elem_Array~[Member_Count => E]
     --       and Member_Count = Member_Count~ + 1));
   is
   begin
      if not isMember(E) then
         if Member_Count < Size_Range'Last then
            Member_Count := Member_Count + 1;
            Elem_Array (Member_Count) := E;
         end if;
      end if;
   end Add;

   procedure Remove(E: Element_Type)
     --# global in out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from E, Elem_Array, Member_Count;
     --# pre Member_Count /= 0;
     --# post (not isMember(E, Elem_Array~, Member_Count~) -> (
     --#       Elem_Array = Elem_Array~ and Member_Count = Member_Count~))
     --#  and (isMember(E, Elem_Array~, Member_Count~)
     --#      -> (Elem_Array = Elem_Array~[
     --#          Get_Element_Index(E, Elem_Array, Member_Count) =>
     --#             Elem_Array~(Member_Count~)]
     --#       and Member_Count = Member_Count~ - 1));
   is
      I : Integer;
   begin
      I := Get_Element_Index(E);
      if I /= 0 then
         Elem_Array (I) := Elem_Array(Member_Count);
         Elem_Array(Member_Count) := 0; -- necessary?
         Member_Count := Member_Count - 1;
      end if;
   end Remove;

   procedure Empty
     --# global out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from;
     --# post Member_Count = 0;
   is
   begin
      Member_Count := 0;
      Elem_Array := Elem_Array_Type'(others => 0);
   end Empty;

end Integer_Set;
