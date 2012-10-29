package P is
   procedure Fault_Integrator(Fault_Found : in Boolean;
                              Trip : in out Boolean;
                              Counter : in out Integer);
   --# derives Trip from Fault_Found, Trip, Counter &
   --#         Counter from Fault_Found, Counter;
end P;

package body P is
   Upper_Limit : constant Integer := 3_500;
   Lower_Limit : constant Integer := -20;
   Up_Rate     : constant Integer := 5;
   Down_Rate   : constant Integer := 3;

   procedure Fault_Integrator(Fault_Found : in Boolean;
                              Trip : in out Boolean;
                              Counter : in out Integer)
   is
   begin
      if Fault_Found then
         Counter := Counter + Up_Rate;
         if Counter >= Upper_Limit then
            Trip := True; Counter := Upper_Limit;
         end if;
      else
         Counter := Counter - Down_Rate;
         if Counter <= Lower_Limit then
            Trip := False; Counter := Lower_Limit;
         end if;
      end if;
   end Fault_Integrator;
end P;
