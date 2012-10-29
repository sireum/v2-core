package Array_Utilities is
  Max_Table_Size : constant := 100;
  type Base_Index_Type is range 0..Max_Table_Size;
  subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
  type Contents_Type is range -1000 .. 1000;
  type Array_Type is array(Index_Type) of Contents_Type;

  procedure Swap(X, Y : in out Integer);
  --# derives X from Y & Y from X;
  --# post X = Y~ and Y = X~;

  procedure Swap_Elements_No_Post(T : in out Array_Type;
                                  I, J : in Index_Type);
  --# derives T from T,I,J;

  procedure Swap_Elements_weak(T : in out Array_Type;
                               I, J : in Index_Type);
  --# derives T from T,I,J;
  --# post T(I) = T~(J) and T(J) = T~(I);

  procedure Swap_Elements_strong(T : in out Array_Type;
                                 I, J : in Index_Type);
  --# derives T from T,I,J;
  --# post T = T~[I => T~(J); J => T~(I)];

end Array_Utilities;

package body Array_Utilities is

   procedure Swap(X, Y : in out Integer)
   is
      T : Integer;
   begin
      T := X; X := Y; Y := T;
   end Swap;

   procedure Swap_Elements_No_Post(T : in out Array_Type;
                               I, J : in Index_Type)
  is
    Temp : Contents_Type;
  begin
    Temp := T(I);
    T(I) := T(J);
    T(J) := Temp;
  end Swap_Elements_No_Post;

  procedure Swap_Elements_weak(T : in out Array_Type;
                               I, J : in Index_Type)
  is
    Temp : Contents_Type;
  begin
    Temp := T(I);
    T(I) := T(J);
    T(J) := Temp;
  end Swap_Elements_weak;

  procedure Swap_Elements_strong(T : in out Array_Type;
                                 I, J : in Index_Type)
  is
    Temp : Contents_Type;
  begin
    Temp := T(I);
    T(I) := T(J);
    T(J) := Temp;
  end Swap_Elements_strong;

end Array_Utilities;

