with Model;
--# inherit Model;
package Integer_Set
  --# own State: Model.Z_Set;
  --#     Elem_Array : Elem_Array_Type; Member_Count : Size_Range;
is
   subtype Element_Type is Integer;
   subtype Size_Range is Integer range 0 .. 100;

   --! type Z_Set is abstract;
   --! function Z_Set_Empty return Z_Set;
   --! function Z_Set_Add(e: Element_Type; s: Z_Set) return Z_Set;
   --! function Z_Set_Remove(e: Element_Type; s: Z_Set) return Z_Set;
   --! function Z_Set_Size(s: Z_Set) return Integer;
   --! function Z_Set_IsEmpty(s: Z_Set) return Boolean;
   --! function Z_Set_IsMember(e: Element_Type; s: Z_Set) return Boolean;

   --  The invariant must be given as a real function here because we want to
   --  be able to give it two contracts: on over the abstract state and one
   --  over the concrete state. Since we cannot give two contracts in the
   --  _ptf.ada file, we give the abstract one here.

   function Invariant return Boolean; -- package invariant
   --# global in State;
   --# return Model.Z_Set_Size(State) in Size_Range;

   function Size return Size_Range;
   --# global in State;
   --# return Model.Z_Set_Size(State);

   function isFull return Boolean;
   --# global in State;
   --# return Size(State) = Size_Range'Last;

   function isMember(E: in Element_Type) return Boolean;
   --# global in State;
   --# return Model.Z_Set_IsMember(E, State);

   procedure Add(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;
   --# pre not isFull(State);
   --# post State = Model.Z_Set_Add(E, State~);

   procedure Remove(E: in Element_Type);
   --# global in out State;
   --# derives State from E, State;
   --# pre Model.Z_Set_IsEmpty(State);
   --# post State = Model.Z_Set_Remove(E, State~);

   procedure Empty;
   --# global out State;
   --# derives State from;
   --# post Model.Z_Set_IsEmpty(State);

-- private

   subtype Index_Type is Size_Range range 1 .. Size_Range'Last;
   type Elem_Array_Type is array (Index_Type) of Element_Type;

   Member_Count : Size_Range;
   Elem_Array : Elem_Array_Type;

   --# function Represents(ea: Elem_Array_Type; mc: Size_Range) return Model.Z_Set;
   --! return { ea[i] | I in Integer range 1 .. mc };

   -- The representation inv is not strictly necessary, but is useful to test
   -- the idea of having one.
   --# function Rep_Invariant(ea: Elem_Array_Type; mc: Size_Range) return Boolean;
   --  that each member appears exactly once in the element array
   -- = for all I, J in Integer range Index_Type'First .. Member_Count =>
   --   Elem_Array(I) = Elem_Array(J) -> I = J

end Integer_Set;
