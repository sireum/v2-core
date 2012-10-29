package body MainHelper
is
  procedure ControlHigh(HighIntegrator : in out FaultIntegrator.T)
  is
     RawTooFull,
     TooFull : Boolean;
  begin
    RawTooFull := WaterHighSensor.IsActive;
    FaultIntegrator.Test(HighIntegrator,
                         RawTooFull,
                         --to get
                         TooFull);
    if TooFull then
      DrainValve.SetTo(Valve.Open);
    else
      DrainValve.SetTo(Valve.Shut);
    end if;
  end ControlHigh;

  procedure ControlLow (LowIntegrator : in out FaultIntegrator.T)
  is
    RawTooEmpty,
    TooEmpty : Boolean;
  begin
    RawTooEmpty := WaterLowSensor.IsActive;
    FaultIntegrator.Test(LowIntegrator,
                         RawTooEmpty,
                         --to get
                         TooEmpty);
    if TooEmpty then
      FillValve.SetTo(Valve.Open);
    else
      FillValve.SetTo(Valve.Shut);
    end if;
  end ControlLow;
end MainHelper;
