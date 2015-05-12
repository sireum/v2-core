#!/bin/bash
# This script assumes that JDK files are present in the working directory, and
# Sireum is reachable from the PATH environment variable.
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
