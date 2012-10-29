package Dependence_Test_Suite_02
is
   procedure Success0 (O : out Integer);
   --# derives O from ;
   
   procedure Success1 (I : in Integer; O : out Integer);
   --# derives O from I ;

   procedure Success2 (I : in Integer; O : out Integer);
   --# derives O from I ;
   
   procedure Success14 (I1 : in Integer; O1 : out Integer);
   --# derives O1 from I1;
   
end Dependence_Test_Suite_02;
