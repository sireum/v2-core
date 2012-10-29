-- Realization of a single Integer set using a fixed size array.
-- In this version the package spec does not have contracts
-- (or only very minimal contracts).
--
-- @author Patrice Chalin, SAnToS Lab & DSRG.org.

package Integer_Set
  --# own State;
is
   Max_Items : constant := 3;
   
   subtype Element_Type is Integer;
   subtype Size_Range is Integer range 0 .. Max_Items;

   function Size return Size_Range;
   --# global in State;

   function isFull return Boolean;
   --# global in State;
   --# return Size(State) = Size_Range'Last;

   function isMember(E: in Element_Type) return Boolean;
   --# global in State;

   procedure Add(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;
   --# pre not isFull(State);

   procedure Remove(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;

   procedure Empty;
   --# global out State;
   --# derives State from;

end Integer_Set;

package body Integer_Set
  --# own State is Elem_Array, Member_Count;
is
  NULL_INDEX : constant Size_Range := Size_Range'First;
    
  subtype Index_Type is Size_Range range NULL_INDEX + 1 .. Size_Range'Last;
  
  type Elem_Array_Type is array (Index_Type) of Element_Type;

   Elem_Array : Elem_Array_Type;
   Member_Count : Size_Range;

   function Unique_Elements return Boolean 
     --# global Elem_Array, Member_Count;
   is
     Result : Boolean := True; 
   begin
     for I in Size_Range range 1 .. Member_Count - 1 loop
       for J in Size_Range range I + 1 .. Member_Count loop
         Result := Result and then Elem_Array(I) /= Elem_Array(J);
         exit when not Result;
       end loop;
     end loop;
     return Result;
   end Unique_Elements;
   
   function Invariant return Boolean 
     --# global Elem_Array, Member_Count;
   is
   begin
     return Unique_Elements;
   end Invariant;
   
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
     --# return Result =>
     --#    ((for all J in Index_Type range Index_Type'First .. Member_Count
     --#         => (Elem_Array(J) /= E))
     --#      and Result = NULL_INDEX)
     --#   or else
     --#     (Result in Index_Type'First .. Member_Count 
     --#      and Elem_Array(Result) = E);
   is
      I : Integer := Index_Type'First;
   begin
      while I <= Member_Count and then Elem_Array (I) /= E loop
         --# assert I in Index_Type'First .. Member_Count + 1
         --# and (for all J in Integer range
         --#        Index_Type'First .. I - 1 => (
         --#          Elem_Array(J) /= E));
         I := I + 1;
      end loop;
      
      if I > Member_Count then
         I := NULL_INDEX;
     end if;
     
     return I;
   end Get_Element_Index;

   function isMember(E: in Element_Type) return Boolean
     --# global in Elem_Array, Member_Count;
     --# return for some I in Integer range Index_Type'First .. Member_Count =>
     --#            (Elem_Array(I) = E);
   is
   begin
      return Get_Element_Index(E) /= NULL_INDEX;
   end isMember;

   procedure Add(E: Element_Type)
     --# global in out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from E, Elem_Array, Member_Count;
     --# pre Invariant (Elem_Array, Member_Count);
     --# post Invariant (Elem_Array, Member_Count) and (isMember(E, Elem_Array~, Member_Count~) -> (
     --#       Elem_Array = Elem_Array~ and Member_Count = Member_Count~))
     --#   and (not isMember(E, Elem_Array~, Member_Count~) -> (
     --#          (isFull(Member_Count~) and Elem_Array = Elem_Array~ and Member_Count = Member_Count~)
     --#          or else (
     --#            Elem_Array = Elem_Array~[Member_Count => E] and
     --#            Member_Count = Member_Count~ + 1)));
   is
   begin
      if not isFull and not isMember(E) then
        Member_Count := Member_Count + 1;
        Elem_Array (Member_Count) := E;
      end if;
   end Add;

   procedure Remove(E: Element_Type)
     --# global in out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from E, Elem_Array, Member_Count;
     --# pre Invariant (Elem_Array, Member_Count) and Member_Count /= 0;
     --# post (not isMember(E, Elem_Array~, Member_Count~) -> (
     --#       Elem_Array = Elem_Array~ and Member_Count = Member_Count~))
     --#  and (isMember(E, Elem_Array~, Member_Count~) ->
     --#        (Elem_Array = 
     --#         Elem_Array~[Get_Element_Index(E, Elem_Array~, Member_Count~) => 
     --#                       Elem_Array~(Member_Count~)]
     --#       and Member_Count = Member_Count~ - 1));
   is
      I : Size_Range;
   begin
      I := Get_Element_Index(E);
      if I /= NULL_INDEX then
         Elem_Array (I) := Elem_Array(Member_Count);
         -- Elem_Array(Member_Count) := 0; -- necessary?
         Member_Count := Member_Count - 1;
      end if;
   end Remove;

   procedure Empty
     --# global out Elem_Array, Member_Count;
     --# derives Elem_Array, Member_Count from;
     --# post Member_Count = 0;
   is
   begin
      Member_Count := NULL_INDEX;
      Elem_Array := Elem_Array_Type'(others => 0);
   end Empty;

end Integer_Set;
