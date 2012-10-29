package Labeled_Statements
is
   subtype Index_Range is Integer range 1..100;
     
   function Stupid(Z : Index_Range) return Integer;

end Labeled_Statements;

package body Labeled_Statements is

   procedure P (I, J : Integer; Ret : out Boolean)
     --# derives Ret from I, J; 
   is begin
     if I > J then
       Ret := True;
     else 
       Ret := False;
     end if;
   end P;
   
   function Stupid(Z : Index_Range) return Integer 
   is
     J : Integer;
     B : Boolean;
   begin
     
     <<forLoop>> << line_27 >>
     for I in Index_Range loop
       
       <<jInit>>
       J := 0;
       
       <<whileLoop>> <<      whitespace_trimmed_by_antlr      >>
       while J < Z loop
         <<jInc>> <<c>>
         J := J + 1;
         
         <<procCall>>
         P(I, J, B);
         
         <<ifLabel>>
         if B then
           <<exitLabel>>
           exit;
         end if;
         
         <<caseLabel>>
         case J is
           when 3 =>
             <<incJByOne>> 
             J := J + 1; 
           when others =>
             <<JStaysTheSame>> 
             J := J;
         end case;
       end loop;

       --# assert J > 0;  -- cannot label proof contexts
     end loop;
     
     <<returnLabel>> <<d>>
     return J;
   end Stupid;
   
 end Labeled_Statements;