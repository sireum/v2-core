package Lbl_t
is
  -- I could not find a purpose for Mem_Cols so I removed it.  Also,
  --  Spark was complaining about not being able to export Proc_Id's
  --  when I defined them as an enumeration so I switched them to
  --  Integers instead.

  --subtype Mem_Cols is Integer range 0 .. 6;
  
  Max_Proc_Id : constant Integer := 3;
  subtype Proc_Id is Integer range 1 .. Max_Proc_Id;
  
  Mem_Size: constant Integer := Proc_Id'Last * Proc_Id'Last;
  subtype Pointer is Integer range Proc_Id'First .. Mem_Size;
end Lbl_t;

