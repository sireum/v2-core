=======
WARNING: If you don't want your subdirectories to be deleted ...
=======

BEWARE: The Spark tools create subdirectories to contain various files when it 
processes Spark source.  The Makefile clean has been written to delete all 
subdirectories except those whose name starts with a capital letter.  Hence, if 
you create a subdirectory and want to ensure that it sticks around, ensure that 
it starts with [A-Z]. -- Patrice

=======================================
Info about the tests in this directory.
=======================================

The Makefile allows you to run the Spark Examiner on specified TARGET Spark files. Before doing so you need to define

  SPARK_BIN

to refer to, well, the bin directory of your local Spark toolset.

To run the tools do:

  make TARGET="f1.ada f2.ada ... fk.ada"

To clean (though see the WARNING above):

  make clean

There are some examples that are in their own subdirectories.  In that case, e.g.

  cd Factorial
  make

will run the Spark tools on the example in the subdirectory.

