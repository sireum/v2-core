with WaterHighSensor,
     WaterLowSensor,
     Valve,
     FillValve,
     DrainValve,
     FaultIntegrator;
--# inherit WaterHighSensor,
--#         WaterLowSensor,
--#         Valve,
--#         FillValve,
--#         DrainValve,
--#         FaultIntegrator;
--# main_program;
procedure Main
--# global in     WaterHighSensor.State,
--#               WaterLowSensor.State;
--#           out FillValve.State,
--#               DrainValve.State;
--# derives FillValve.State
--#            from WaterLowSensor.State  &
--#         DrainValve.State
--#            from WaterHighSensor.State;
is

  HighIntegrator,
  LowIntegrator  : FaultIntegrator.T;

  HighThreshold : constant Positive := 10;
  LowThreshold  : constant Positive := 10;

  procedure ControlHigh
  --# global in     WaterHighSensor.State;
  --#           out DrainValve.State;
  --#        in out HighIntegrator;
  --# derives DrainValve.State,
  --#         HighIntegrator
  --#           from HighIntegrator,
  --#                WaterHighSensor.State;
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

  procedure ControlLow
  --# global in     WaterLowSensor.State;
  --#           out FillValve.State;
  --#        in out LowIntegrator;
  --# derives FillValve.State,
  --#         LowIntegrator
  --#            from LowIntegrator,
  --#                 WaterLowSensor.State;
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


begin  -- Main
  FaultIntegrator.Init(HighIntegrator, HighThreshold);
  FaultIntegrator.Init(LowIntegrator, LowThreshold);

  FillValve.SetTo(Valve.Shut);
  DrainValve.SetTo(Valve.Shut);

  loop
    ControlHigh;
    ControlLow;
  end loop;
end Main;
