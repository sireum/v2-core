#!/bin/bash
# This script assumes that JDK files are present in the working directory, and
# Sireum is reachable from the PATH environment variable.
#
# mac64
#
#
ZULU_VERSION=1.8.0_66-8.11.0.1
ZULU_M_DROP_URL=http://cdn.azulsystems.com/zulu/bin/zulu${ZULU_VERSION}-macosx.zip
ZULU_M_DROP="${ZULU_M_DROP_URL##*/}"
if [ ! -f ${ZULU_M_DROP} ]; then
  echo
  echo "Downloading ${ZULU_M_DROP}"
  echo
  wget --referer=http://www.azulsystems.com/products/zulu/downloads ${ZULU_M_DROP_URL}
fi
#
# linux64
#
ZULU_L_DROP_URL=http://cdn.azulsystems.com/zulu/bin/zulu${ZULU_VERSION}-x86lx64.zip
ZULU_L_DROP="${ZULU_L_DROP_URL##*/}"
if [ ! -f ${ZULU_L_DROP} ]; then
  echo
  echo "Downloading ${ZULU_L_DROP}"
  echo
  wget --referer=http://www.azulsystems.com/products/zulu/downloads ${ZULU_L_DROP_URL}
fi
#
# win64
#
ZULU_W_DROP_URL=http://cdn.azulsystems.com/zulu/bin/zulu${ZULU_VERSION}-win64.zip
ZULU_W_DROP="${ZULU_W_DROP_URL##*/}"
if [ ! -f ${ZULU_W_DROP} ]; then
  echo
  echo "Downloading ${ZULU_W_DROP}"
  echo
  wget --referer=http://www.azulsystems.com/products/zulu/downloads ${ZULU_W_DROP_URL}
fi
#
# mac64
#
#
echo Packaging java-mac64.sapp
unzip -q "`find . -d 1 -name "zulu*macosx.zip" 2> /dev/null`"
mv "`find . -d 1 -name "zulu*macosx" 2> /dev/null`" java.new
sireum tools sapper java-mac64.sapp java.new
rm -fR java.new
#
# linux64
#
echo Packaging java-linux64.sapp
unzip -q "`find . -d 1 -name "zulu*x86lx64.zip" 2> /dev/null`"
mv "`find . -d 1 -name "zulu*x86lx64" 2> /dev/null`" java.new
sireum tools sapper java-linux64.sapp java.new
rm -fR java.new
#
# win64
#
echo Packaging java-win64.sapp
unzip -q "`find . -d 1 -name "zulu*win64.zip" 2> /dev/null`"
mv "`find . -d 1 -name "zulu*win64" 2> /dev/null`" java.new
sireum tools sapper java-win64.sapp java.new
rm -fR java.new
