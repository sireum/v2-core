package FaultIntegrator
is
  type T is limited private;

  procedure Init(FI        :    out T;
                 Threshold : in     Positive);
  --# derives FI from Threshold;

  procedure Test(FI              : in out T;
                 CurrentEvent    : in     Boolean;
                 IntegratedEvent :    out Boolean);
  --# derives IntegratedEvent,
  --#         FI              from FI, CurrentEvent;

private
  type T is record
              Limit   : Positive;
              Counter : Natural;
              Tripped : Boolean;
            end record;
end FaultIntegrator;

package body FaultIntegrator
is

  procedure Init(FI        :    out T;
                 Threshold : in     Positive)
  is
  begin
    FI := T'(Limit   => Threshold,
             Counter => 0,
             Tripped => False);
  end Init;

  procedure Test(FI              : in out T;
                 CurrentEvent    : in     Boolean;
                 IntegratedEvent :    out Boolean)
  is
  begin
    if CurrentEvent then
      if FI.Counter = FI.Limit then
        FI.Tripped := True;
      else
        FI.Counter := FI.Counter + 1;
      end if;
    else -- no CurrentEvent
      if FI.Counter = 0 then
        FI.Tripped := False;
      else
        FI.Counter := FI.Counter - 1;
      end if;
    end if;
    IntegratedEvent := FI.Tripped;
  end Test;
end FaultIntegrator;
