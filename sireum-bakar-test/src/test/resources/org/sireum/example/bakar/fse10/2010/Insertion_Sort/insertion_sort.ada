package Insertion_Sort is
   Max_Table_Size : constant := 100;
   type Base_Index_Type is range 0..Max_Table_Size;
   subtype Index_Type is Base_Index_Type range 1..Max_Table_Size;
   type Contents_Type is range -1000 .. 1000;
   type Array_Type is array(Index_Type) of Contents_Type;

   --# function Ordered(A : Array_Type; L,U : Index_Type) return Boolean;

   --#function Perm(A, B : Array_Type; L,U : Index_Type) return Boolean;
 
   procedure InsertionSort(Table : in out Array_Type);
   --# derives Table from Table;
   --# post Ordered(Table,1,Max_Table_Size) and
   --# Perm(Table,Table~, 1, Max_Table_Size);
end Insertion_Sort;

package body Insertion_Sort is

   procedure InsertionSort(Table : in out Array_Type) is
      J : Index_Type;
      Temp: Contents_Type;
     begin 
      for P in Index_Type range 2 .. Max_Table_Size loop
         Temp := Table(P);
         J := P;
         while (J > 1 and Table(J - 1) > Temp) loop
            Table(J) := Table(J-1);
            J := J - 1;
         --# assert Temp = Table~(P) and 1<= J and J < P and
         --# (for all K in Index_Type range 
         --# J + 1 .. P => (Table(K) > Temp)) and Ordered(Table, 1, P) and
         --# Perm(Table[J => Temp], Table~, 1, P); 
         end loop;
         Table(J) := Temp;         
         --# assert 2 <= P and P <= Max_Table_Size and
         --#      Ordered(Table,1,P) and
         --#      Perm(Table,Table~, 1, P) and 
         --#      (for all K in Index_Type range
         --#       P+1 .. Max_Table_Size => (Table(K) = Table~(K)));
      end loop;
   end InsertionSort;
   
end Insertion_Sort;
