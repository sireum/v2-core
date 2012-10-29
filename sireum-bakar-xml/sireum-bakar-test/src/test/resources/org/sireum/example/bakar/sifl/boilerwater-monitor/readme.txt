-- John Hatcliff, August 22, 2007
This directory contains the code of the Boiler Water Monitor
from the SPARK/Praxis INFORMED document.  I could not find the
code for this elsewhere, but simply extracted it from the PDF
of the INFORMED document.

Characteristics of the example:
  * very simple 
           -- only two package implementations defined (main, fault integrator).
              The rest of the packages implement boundary variables
  * no arrays
  * very simple information flow.  There are a couple of conditions,
    but these assign different values of the same enumerated type
    in the true/false branch, so I doubt these will contribute to
    interesting conditional information flow.
    
Analysis is easily achieved by:

 spark @bodies

using the supplied default switch file which selects the
required profile, index file and configuration file.

This was my first time to use switch files, index files, etc.,
so something may not be quite right.

Detailed assessment:
-------------------------

Summary of the two implementation packages

  FaultIntegrator:
    Init procedure
      - one line (parallel) assignment of record components
    Test
      - approx 12 LOC
      - nested if statements
      - same variables are assigned in each branch, doesn't look 
          like interesting conditional flow

  Main:
    body 
     - 6 LOC
     - 4 straightline initializations via procedure calls
     - infinite loop with two procedure calls (these procedures
         modify global variables only)

    ControlHigh
      - 8 LOC
      - one conditional (test exp is boolean variable)
        both branches assign to same variable
        These assign different values of the same enumerated type
        in the true/false branch, so I doubt these will contribute 
        to interesting information flow.  But we should check what
        the tool says.

    ControlLow
      - 8 LOC
       (similar to above)

