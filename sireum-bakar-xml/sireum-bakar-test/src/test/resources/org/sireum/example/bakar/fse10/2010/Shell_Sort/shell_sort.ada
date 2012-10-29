package Shell_Sort is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   --#function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;
   --#function Partitioned(A : Array_Type; L, M, U : Index_Type) return Boolean;

   --#function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean;
 
   procedure ShellSort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --#   Perm(Table,Table~, 1, Max_Table_Size);
end Shell_Sort;

package body Shell_Sort is

      procedure Swap_Elements(T : in out Array_Type;
                              I, J : in Index_Type)
        --# derives T from T,I,J;
        --# post T = T~[I => T~(J); J => T~(I)] and Perm(T,T~, 1, Max_Table_Size);
      is
         Temp : Contents_Type;
      begin
         Temp := T(I); T(I) := T(J); T(J) := Temp;
      end Swap_Elements;

   procedure ShellSort(Table : in out Array_Type) is
     Gap : Index_Type;
     J : Index_Type;
     begin 
       Gap := Max_Table_Size;
       loop
         Gap := Gap / 2;
        for I in Index_Type range Gap..Max_Table_Size loop
          J := I- Gap;
          while J >= 1 and Table(J) > Table(J + Gap) loop
            Swap_Elements(Table, J, J + Gap);
            J := J - Gap;
          --assert (for all K in Index_Type range 
          -- J + Gap .. I => (K-L != Gap or Table(L) <= Table(K))) and 
          -- Perm(Table, Table~, J+Gap, I); 
          end loop;
          -- assert (for all K,L in Index_Type range 
          -- 1 .. I => (K-L != Gap or Table(L) <= Table(K))) and 
          -- Perm(Table, Table~, 1, I); 
        end loop;
         exit when Gap = 1;
      -- assert (for all K,L in Index_Type range 1..Max_Table_Size
      -- => (K-L != Gap) or Table(L) <= Table(K);
     end loop;
   end ShellSort;
end Shell_Sort;
