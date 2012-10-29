# File: Makefile
#

BASEDIR=./
POGS_FILE_NAME=$(shell basename `pwd`)

TARGET=[^S]*.ada

default: spark_target

include Makefile.in

cleanAll:
	@-for d in `find . -type d -name "[A-Z]*"`; do \
		echo "Cleaning $$d"; \
		$(MAKE) -C $$d clean; \
		done

# End of file
