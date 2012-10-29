package Main 
is
   type T is (Open, Shut);

   HighThreshold : constant Positive := 10;
   LowThreshold  : constant Positive := 10;
   
   
   procedure Test(Limit   : in Positive; 
		 Counter : in out Natural; 
		 Tripped : in out Boolean;
		 CurrentEvent    : in Boolean; 
		 IntegratedEvent : out Boolean);
   --#derives Counter         from CurrentEvent, Limit, Counter &
   --#        IntegratedEvent from Tripped, CurrentEvent, Counter, Limit &
   --#        Tripped         from Tripped, CurrentEvent, Counter, Limit;
   
   procedure SetTo(Setting : in T; Valve: out T);
   --#derives Valve from Setting;
   
   procedure ControlHigh(Limit: in Positive; Counter: in out Natural; Tripped: in out Boolean; ValveStatus: out T);
   --#derives ValveStatus     from Tripped, Counter, Limit &
   --#        Counter         from Limit, Counter &
   --#        Tripped         from Tripped, Counter, Limit;   
   
   procedure ControlLow(Limit: in Positive; Counter: in out Natural; Tripped: in out Boolean; ValveStatus: out T);
   --#derives ValveStatus     from Tripped, Counter, Limit &
   --#        Counter         from Limit, Counter &
   --#        Tripped         from Tripped, Counter, Limit;
   
end Main;

package body Main
is
  procedure Test(Limit   : in Positive; Counter : in out Natural; Tripped : in out Boolean;
                 CurrentEvent    : in     Boolean;
                 IntegratedEvent :    out Boolean)
   is
   begin
      if CurrentEvent then
	 if Counter =Limit then
	    Tripped := True;
	 else
	    Counter := Counter + 1;
	 end if;
      else
	 if Counter = 0 then
	    Tripped := False;
	 else
	    Counter := Counter - 1;
	 end if;
      end if;
      IntegratedEvent := Tripped;
  end Test;
  
  procedure SetTo(Setting : in T; Valve: out T) 
  is 
  begin
     Valve := Setting;
  end SetTo;
  
  function IsActive return Boolean
  is
  begin
     return True;
  end IsActive;

  procedure ControlHigh(Limit: in Positive; Counter: in out Natural; Tripped: in out Boolean; ValveStatus: out T)
  is
     RawTooFull,
     TooFull : Boolean;
  begin
    RawTooFull := IsActive;
    Test(Limit, Counter, Tripped,
         RawTooFull, TooFull);
    if TooFull then
      SetTo(Open, ValveStatus);
    else
      SetTo(Shut, ValveStatus);
    end if;
  end ControlHigh;

  procedure ControlLow(Limit: in Positive; Counter: in out Natural; Tripped: in out Boolean; ValveStatus: out T)
  is
    RawTooEmpty,
    TooEmpty : Boolean;
  begin
    RawTooEmpty := IsActive;
    Test(Limit, Counter, Tripped,
         RawTooEmpty, TooEmpty);
    if TooEmpty then
      SetTo(Open, ValveStatus);
    else
      SetTo(Shut, ValveStatus);
    end if;
  end ControlLow;

end Main;
