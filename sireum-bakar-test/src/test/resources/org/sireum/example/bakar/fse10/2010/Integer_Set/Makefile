# File: Makefile

BASEDIR=../../
POGS_FILE_NAME=$(shell basename `pwd`)

default: orig ptf
	@echo
	@echo "Checking both orig and ptf."
	@echo

include ../../Makefile.in

orig:
	$(MAKE) spark_target TARGET="Model.ada \
		Integer_Set.ada"

ptf:
	$(MAKE) spark_target TARGET="Model.ada \
		Integer_Set_ads4ptf.ada \
		Integer_Set_ptf.ada"

# End of file
