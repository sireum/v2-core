#!/bin/bash
#
# This script assumes that Sireum is reachable from the PATH environment variable.
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export DROP_URL=${MIRROR_URL:=http://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release}/${REL:=luna/R}
export VERSION=dsl-luna-R
if [ ! -f eclipse-$VERSION-macosx-cocoa-x86_64.tar.gz ]; then
  echo
  echo Downloading Eclipse $VERSION mac64
  echo
  wget $DROP_URL/eclipse-$VERSION-macosx-cocoa-x86_64.tar.gz
  echo
fi
if [ ! -f eclipse-$VERSION-linux-gtk-x86_64.tar.gz ]; then
  echo Downloading Eclipse $VERSION linux64
  echo
  wget $DROP_URL/eclipse-$VERSION-linux-gtk-x86_64.tar.gz
  echo
fi
if [ ! -f eclipse-$VERSION-linux-gtk.tar.gz ]; then
  echo Downloading Eclipse $VERSION linux32
  echo
  wget $DROP_URL/eclipse-$VERSION-linux-gtk.tar.gz
  echo
fi
if [ ! -f eclipse-$VERSION-win32-x86_64.zip ]; then
  echo Downloading Eclipse $VERSION win64
  echo
  wget $DROP_URL/eclipse-$VERSION-win32-x86_64.zip
  echo
fi
if [ ! -f eclipse-$VERSION-win32.zip ]; then
  echo Downloading Eclipse $VERSION win32
  echo
  wget $DROP_URL/eclipse-$VERSION-win32.zip
  echo
fi
#
echo Packaging dsl-mac64.sapp
#
tar xfz eclipse-$VERSION-macosx-cocoa-x86_64.tar.gz
mv eclipse dsl
mv dsl/Eclipse.app/Contents/MacOS/eclipse dsl/Eclipse.app/Contents/MacOS/eclipse.orig
cp $PACKAGE_HOME/mac/eclipse dsl/Eclipse.app/Contents/MacOS/
chmod 755 dsl/Eclipse.app/Contents/MacOS/eclipse
cp -R $PACKAGE_HOME/links dsl/
sireum tools sapper dsl-mac64.sapp dsl
tar cfz dsl-mac64.tar.gz dsl
rm -R dsl
#
echo Packaging dsl-linux64.sapp
#
tar xfz eclipse-$VERSION-linux-gtk-x86_64.tar.gz
mv eclipse dsl
rm dsl/eclipse
cp $PACKAGE_HOME/linux/eclipse dsl/
chmod 755 dsl/eclipse
cp -R $PACKAGE_HOME/links dsl/
sireum tools sapper dsl-linux64.sapp dsl
#tar cfz dsl-linux64.tar.gz dsl
rm -fR dsl
#
echo Packaging dsl-linux32.sapp
#
tar xfz eclipse-$VERSION-linux-gtk.tar.gz
mv eclipse dsl
rm dsl/eclipse
cp $PACKAGE_HOME/linux/eclipse dsl/
chmod 755 dsl/eclipse
cp -R $PACKAGE_HOME/links dsl/
sireum tools sapper dsl-linux32.sapp dsl
#tar cfz dsl-linux32.tar.gz dsl
rm -fR dsl
#
echo Packaging dsl-win64.sapp
#
unzip -q eclipse-$VERSION-win32-x86_64.zip
mv eclipse dsl
rm dsl/eclipse.exe
rm dsl/eclipsec.exe
cp $PACKAGE_HOME/windows/eclipse.bat dsl/
cp $PACKAGE_HOME/windows/README.TXT dsl/
cp -R $PACKAGE_HOME/links dsl/
zip -q -r dsl-win64.sapp dsl
rm -R dsl
#
echo Packaging dsl-win32.sapp
#
unzip -q eclipse-$VERSION-win32.zip
mv eclipse dsl
rm dsl/eclipse.exe
rm dsl/eclipsec.exe
cp $PACKAGE_HOME/windows/eclipse.bat dsl/
cp $PACKAGE_HOME/windows/README.TXT dsl/
cp -R $PACKAGE_HOME/links dsl/
zip -q -r dsl-win32.sapp dsl
rm -R dsl
echo
echo ...done!
