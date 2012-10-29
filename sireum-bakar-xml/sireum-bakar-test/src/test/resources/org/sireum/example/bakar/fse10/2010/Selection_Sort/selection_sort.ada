package Selection_Sort is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   --#function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;
   --#function Partitioned(A : Array_Type; L, M, U : Index_Type) return Boolean;

   --#function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean;
 
   procedure SelectionSort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --#   Perm(Table,Table~, 1, Max_Table_Size);
end Selection_Sort;

package body Selection_Sort is

      procedure Swap_Elements(T : in out Array_Type;
                              I, J : in Index_Type)
        --# derives T from T,I,J;
        --# post T = T~[I => T~(J); J => T~(I)] and Perm(T,T~, 1, Max_Table_Size);
      is
         Temp : Contents_Type;
      begin
         Temp := T(I); T(I) := T(J); T(J) := Temp;
      end Swap_Elements;

   procedure SelectionSort(Table : in out Array_Type) is
     MinPos : Index_Type;
     begin 
      for I in Index_Type range 1..Max_Table_Size loop
        MinPos := I;
        for J in Index_Type range I+1..MAX_TABLE_SIZE loop
          if Table(J) < Table(MinPos) then
            MinPos := J;
          end if;
          --# assert MinPos >= I and MinPos <= J and
          --# (for all K in Index_Type range 
          --# I .. J => (Table(K) <= Table(MinPos)));
        end loop;
        Swap_Elements(Table, I, MinPos);
        --# assert Ordered(Table,1, I) and
        --# Perm(Table,Table~, 1, I);
      end loop;
   end SelectionSort;
   
end Selection_Sort;
