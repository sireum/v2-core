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
