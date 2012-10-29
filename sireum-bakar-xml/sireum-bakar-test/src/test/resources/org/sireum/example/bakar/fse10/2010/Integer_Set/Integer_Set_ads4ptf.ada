-- Integer_Set Shadow
--
-- @author Patrice Chalin, SAnToS Lab & DSRG.org.

with Model;
--# inherit Model;
package Integer_Set
  --# own State: Model.Z_Set;
  --#     Elem_Array : Elem_Array_Type; Member_Count : Size_Range;
is
   subtype Element_Type is Integer;
   subtype Size_Range is Integer range 0 .. 100;

   --  The invariant must be given as a real function here because we want to
   --  be able to give it two contracts: on over the abstract state and one
   --  over the concrete state. Since we cannot give two contracts in the
   --  _ptf.ada file, we give the abstract one here.
   --
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

   --# function Rep_Invariant(ea: Elem_Array_Type; mc: Size_Range) return Boolean;
   --! return for all I in Integer range Integer_Set.Index_Type'First .. Member_Count =>
   --!          (for all J in Integer range Integer_Set.Index_Type'First .. Member_Count =>
   --!            (I /= J -> Elem_Array(I) /= Elem_Array(J)));

end Integer_Set;
