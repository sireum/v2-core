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
package MainHelper
is
  procedure ControlHigh(HighIntegrator : in out FaultIntegrator.T);
  --# global in     WaterHighSensor.State;
  --#           out DrainValve.State;
  --# derives DrainValve.State,
  --#         HighIntegrator
  --#           from HighIntegrator,
  --#                WaterHighSensor.State;

  procedure ControlLow(LowIntegrator : in out FaultIntegrator.T);
  --# global in     WaterLowSensor.State;
  --#           out FillValve.State;
  --# derives FillValve.State,
  --#         LowIntegrator
  --#            from LowIntegrator,
  --#                 WaterLowSensor.State;  
end MainHelper;
