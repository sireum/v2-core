package Bubble_Sort is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   --#function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;
   --#function Partitioned(A : Array_Type; L, M, U : Index_Type) return Boolean;

   --#function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean;
 
   procedure BubbleSort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --#   Perm(Table,Table~, 1, Max_Table_Size);
end Bubble_Sort;

package body Bubble_Sort is

      procedure Swap_Elements(T : in out Array_Type;
                              I, J : in Index_Type)
        --# derives T from T,I,J;
        --# post T = T~[I => T~(J); J => T~(I)] and Perm(T,T~, 1, Max_Table_Size);
      is
         Temp : Contents_Type;
      begin
         Temp := T(I); T(I) := T(J); T(J) := Temp;
      end Swap_Elements;

   procedure BubbleSort(Table : in out Array_Type) is
     begin 
      for I in reverse Index_Type range 1..Max_Table_Size loop
        for J in Index_Type range 2..I loop
          if Table(J - 1) > Table(J) then
            Swap_Elements(Table, J - 1, J);
          end if;
          --# assert (for all K in Index_Type range 
          --# 1 .. J - 1 => (Table(K) < Table(J))) and 
          --# Perm(Table, Table~, 1, Max_Table_Size); 
        end loop;
        --# assert Ordered(Table,I, Max_Table_Size) and
        --# Partitioned(Table, 1, I, Max_Table_Size) and
        --# Perm(Table,Table~, 1, Max_Table_Size);
      end loop;
   end BubbleSort;
   
end Bubble_Sort;
