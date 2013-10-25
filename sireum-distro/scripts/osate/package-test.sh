#!/bin/bash
#
# This script assumes that Sireum is reachable from the PATH environment variable.
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export VERSION=2.0.4
export BUILD_DATE=201309241635
export DROP_URL=${MIRROR_URL:=http://www.aadl.info/aadl/osate/testing/products/}

if [ ! -f osate2-macosx.cocoa.x86_64.zip ]; then
  echo
  echo Downloading Osate2 $VERSION mac64
  echo
  wget $DROP_URL/osate2-macosx.cocoa.x86_64.zip
  echo
fi
if [ ! -f osate2-linux.gtk.x86_64.zip ]; then
  echo Downloading Osate2 $VERSION linux64
  echo
  wget $DROP_URL/osate2-linux.gtk.x86_64.zip
  echo
fi
if [ ! -f osate2-linux.gtk.x86.zip ]; then
  echo Downloading Osate2 $VERSION linux32
  echo
  wget $DROP_URL/osate2-linux.gtk.x86.zip
  echo
fi
if [ ! -f osate2-win32.win32.x86_64.zip ]; then
  echo Downloading Osate2 $VERSION win64
  echo
  wget $DROP_URL/osate2-win32.win32.x86_64.zip
  echo
fi
if [ ! -f osate2-SDK-win32.win32.zip ]; then
  echo Downloading Osate2 $VERSION win32
  echo
  wget $DROP_URL/osate2-win32.win32.x86.zip
  echo
fi
#
echo Packaging osate2-mac64.sapp
#
unzip osate2-macosx.cocoa.x86_64.zip 
cp -R $PACKAGE_HOME/links osate2/
sireum tools sapper osate2-mac64.sapp osate2
#tar cfz osate2-mac64.tar.gz osate2
rm -R osate2
#
echo Packaging osate2-linux64.sapp
#
unzip osate2-linux.gtk.x86_64.zip
cp -R $PACKAGE_HOME/links osate2/
sireum tools sapper osate2-linux64.sapp osate2
#tar cfz osate2-linux64.tar.gz osate2
rm -fR osate2
#
echo Packaging osate2-linux32.sapp
#
unzip osate2-linux.gtk.x86.zip
cp -R $PACKAGE_HOME/links osate2/
sireum tools sapper osate2-linux32.sapp osate2
#tar cfz osate2-linux32.tar.gz osate2
rm -fR osate2
#
echo Packaging osate2-win64.sapp
#
unzip -q osate2-win32.win32.x86_64.zip
cp -R $PACKAGE_HOME/links osate2/
zip -q -r osate2-win64.sapp osate2
rm -R osate2
#
echo Packaging osate2-win32.sapp
#
unzip -q osate2-win32.win32.x86.zip
cp -R $PACKAGE_HOME/links osate2/
zip -q -r osate2-win32.sapp osate2
rm -R osate2
echo
echo ...done!
