# File: Makefile

BASEDIR=../../
POGS_FILE_NAME=$(shell basename `pwd`)

default: orig ptf
	@echo
	@echo "Checking both orig and ptf."
	@echo

include ../../Makefile.in

orig:
	$(MAKE) spark_target TARGET="Flip_Flop.ada"

ptf:
	$(MAKE) spark_target TARGET="Flip_Flop_ads4ptf.ada Flip_Flop_ptf.ada"


# End of file
