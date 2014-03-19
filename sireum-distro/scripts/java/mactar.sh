#!/bin/bash
# This script assumes that JDK files are present in the working directory, and
# Sireum is reachable from the PATH environment variable.
#
# mac64
#
echo Packaging java-mac64.tar.gz
hdiutil mount "`find . -d 1 -name "jdk-*-macosx-x64.dmg" 2> /dev/null`" > /dev/null
pkgutil --expand "`find /Volumes -name "JDK*.pkg" 2> /dev/null`" jdk-mac64 > /dev/null
tar xf "`find jdk-mac64 -name "jdk*.pkg"`/Payload"
mv Contents/Home java
tar cfz jdk-mac64.tar.gz java
rm -fR Contents jdk-mac64 java
hdiutil unmount "`find /Volumes -d 1 -name "JDK*" 2> /dev/null`" > /dev/null
