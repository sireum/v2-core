Boiler Water Monitor   SPARK Ada Example

Source of Example
--------------------
This directory contains the code of the Boiler Water Monitor example
used in the SPARK/Praxis "INFORMED Design Method for SPARK" document
that is supplied with the manuals in the AdaCore/Praxis SPARK distribution.
The source code files in this directory were not provided by Praxis.
Instead, the contents of the files were extracted from the PDF of the
INFORMED document.  For a detailed description and walkthrough of this
example, see "INFORMED Design Method for SPARK".


Example Overview (adapted from text in "INFORMED Design Method for SPARK")
---------------------------------------------------------------------------

A device is needed to monitor the depth of water in a boiler
vessel. Two sensors are provided “water low” and “water high”. When
the water is low a fill valve is to be opened. When the water is high
a drain valve is to be opened. When neither high nor low signal is
present both valves are closed. To prevent the valves chattering some
delay of operation is required with the valves only operating after 10
successive, consistent signals have been received from the associated
sensor (this is referred to as "fault integration" in the code).

The application is organized as follows.

faultintegrator.ads/adb 
 Provides a record recording the threshold at which action is required
 (e.g., 10 signals), the current number of raw events seen and whether 
 the output is currently tripped.  The FaultIntegrator package provides
 code for manipulating this structure.

drainvalve.ads
 Interface to external memory-mapped variable for holding values that 
 control the drain value.

fillvalue.ads
 Interface to external memory-mapped variable for holding values that 
 control the fill value.

waterhighsensor.ads
 Interface to external memory-mapped variable for reading values from
 high water sensor

waterlowsensor.ads
 Interface to external memory-mapped variable for reading values from
 low water sensor

main.adb -- main_program procedure.  
 Initializes fault integrators and values. Executes an infinite
 loop that repeatedly (a) monitors the water high sensor and takes 
 appropriate action and (b) monitors the water low sensor and takes
 appropriate action.

Language Features Used
------------------------
This is a very simple application with most scalar types.  A private
record type is used in fault integration, and a record aggregate is
used in initialization.  There are no arrays.  A few if-then-else 
control structures are used; there are no while loops or for loops.

Specification Features
-------------------------
Only information flow specifications are used in this example.
There is no data refinement.

Executable
---------------
This application is not executable.  To execute, it would require
some facility for providing the values of the boundary variables
(sensors).

     
Analysis
---------------

Analysis via the SPARK Examiner is achieved by:

 spark @bodies

using the supplied default switch file which selects the
required profile, index file and configuration file.

