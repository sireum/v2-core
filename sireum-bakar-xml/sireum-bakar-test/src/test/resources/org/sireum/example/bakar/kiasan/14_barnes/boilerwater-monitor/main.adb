with WaterHighSensor,
     WaterLowSensor,
     Valve,
     FillValve,
     DrainValve,
     FaultIntegrator,
     MainHelper;
--# inherit WaterHighSensor,
--#         WaterLowSensor,
--#         Valve,
--#         FillValve,
--#         DrainValve,
--#         FaultIntegrator,
--#         MainHelper;
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

begin  -- Main
  FaultIntegrator.Init(HighIntegrator, HighThreshold);
  FaultIntegrator.Init(LowIntegrator, LowThreshold);

  FillValve.SetTo(Valve.Shut);
  DrainValve.SetTo(Valve.Shut);

  loop
    MainHelper.ControlHigh(HighIntegrator);
    MainHelper.ControlLow(LowIntegrator);
  end loop;
end Main;
