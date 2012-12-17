#!/bin/bash
# This script assumes that JDK files are present in the working directory, and
# Sireum is reachable from the PATH environment variable.
#
# mac64
#
echo Packaging java-mac64.sapp
hdiutil mount "`find . -d 1 -name "jdk-*-macosx-x64.dmg" 2> /dev/null`" > /dev/null
pkgutil --expand "`find /Volumes -name "JDK*.pkg" 2> /dev/null`" jdk-mac64 > /dev/null
tar xf "`find jdk-mac64 -name "jdk*.pkg"`/Payload"
mv Contents/Home java.new
sireum tools sapper java-mac64.sapp java.new
rm -fR Contents jdk-mac64 java.new
hdiutil unmount "`find /Volumes -d 1 -name "JDK*" 2> /dev/null`" > /dev/null
#
# linux64
#
echo Packaging java-linux64.sapp
tar xfz "`find . -d 1 -name "jdk-*-linux-x64.tar.gz" 2> /dev/null`"
mv "`find . -d 1 -name "jdk1*" 2> /dev/null`" java.new
sireum tools sapper java-linux64.sapp java.new
rm -fR java.new
#
# linux32
#
echo Packaging java-linux32.sapp
tar xfz "`find . -d 1 -name "jdk-*-linux-i586.tar.gz" 2> /dev/null`"
mv "`find . -d 1 -name "jdk1*" 2> /dev/null`" java.new
sireum tools sapper java-linux32.sapp java.new
rm -fR java.new
echo
echo ...done!
