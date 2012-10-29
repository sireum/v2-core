package Constraints 
is
  subtype Sub_Int is Integer range 0 .. 10;
  Int_Low_Const: constant Sub_Int := 0;
  Int_High_Const: constant := 100;
  type Int_Range is range Int_Low_Const .. Int_High_Const;
  
  type Day is (Mon, Tue, Wed, Thur, Fri, Sat, Sun);
  subtype Weekday is Day range Mon .. Fri;
  
  procedure foo(a : in out Integer; dayVar : in Day);
  --# derives a from a, dayVar;
  
end Constraints;


package body Constraints
is

procedure foo(a : in out Integer; dayVar : in Day) is
begin
  if not (dayVar = Tue) then
    a := a + 2;
  elsif Day'Succ(dayVar) = Wed then -- always true as dayVar equals 'Tue'
    a := a - 1;
  else
    a := a * 1; -- unreachable
  end if;
end foo;

end Constraints;