# File: Makefile
#

BASEDIR=./
POGS_FILE_NAME=fse10

default: spark_target

include Makefile.in

array_util:
	$(MAKE) TARGET="array_utilities.ada array_utilities_ptf.ada"

factorial:
	$(MAKE) -C Factorial

flip flipflop:
	$(MAKE) -C Flip_Flop

cleanAll:
	@-for d in `find . -type d -name "[A-Z]*"`; do \
		echo "Cleaning $$d"; \
		$(MAKE) -C $$d clean; \
		done

# End of file
