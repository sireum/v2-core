#!/bin/bash
#
# This script assumes that Sireum is reachable from the PATH environment variable.
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export DROP_URL=${MIRROR_URL:=http://ftp.osuosl.org/pub/eclipse/eclipse/downloads/drops4}/${REL:=R}-${VERSION:=4.2.2}-${BUILD_DATE:=201302041200}
if [ ! -f eclipse-SDK-$VERSION-macosx-cocoa-x86_64.tar.gz ]; then
  echo
  echo Downloading Eclipse Classic $VERSION mac64
  echo
  wget $DROP_URL/eclipse-SDK-$VERSION-macosx-cocoa-x86_64.tar.gz
  echo
fi
if [ ! -f eclipse-SDK-$VERSION-linux-gtk-x86_64.tar.gz ]; then
  echo Downloading Eclipse Classic $VERSION linux64
  echo
  wget $DROP_URL/eclipse-SDK-$VERSION-linux-gtk-x86_64.tar.gz
  echo
fi
if [ ! -f eclipse-SDK-$VERSION-linux-gtk.tar.gz ]; then
  echo Downloading Eclipse Classic $VERSION linux32
  echo
  wget $DROP_URL/eclipse-SDK-$VERSION-linux-gtk.tar.gz
  echo
fi
if [ ! -f eclipse-SDK-$VERSION-win32-x86_64.zip ]; then
  echo Downloading Eclipse Classic $VERSION win64
  echo
  wget $DROP_URL/eclipse-SDK-$VERSION-win32-x86_64.zip
  echo
fi
if [ ! -f eclipse-SDK-$VERSION-win32.zip ]; then
  echo Downloading Eclipse Classic $VERSION win32
  echo
  wget $DROP_URL/eclipse-SDK-$VERSION-win32.zip
  echo
fi
#
echo Packaging classic-mac64.sapp
#
tar xfz eclipse-SDK-$VERSION-macosx-cocoa-x86_64.tar.gz
mv eclipse classic
mv classic/Eclipse.app/Contents/MacOS/eclipse classic/Eclipse.app/Contents/MacOS/eclipse.orig
cp $PACKAGE_HOME/mac/eclipse classic/Eclipse.app/Contents/MacOS/
chmod 755 classic/Eclipse.app/Contents/MacOS/eclipse
cp -R $PACKAGE_HOME/links classic/
sireum tools sapper classic-mac64.sapp classic
tar cfz classic-mac64.tar.gz classic
rm -R classic
#
echo Packaging classic-linux64.sapp
#
tar xfz eclipse-SDK-$VERSION-linux-gtk-x86_64.tar.gz
mv eclipse classic
rm classic/eclipse
cp $PACKAGE_HOME/linux/eclipse classic/
chmod 755 classic/eclipse
cp -R $PACKAGE_HOME/links classic/
sireum tools sapper classic-linux64.sapp classic
#tar cfz classic-linux64.tar.gz classic
rm -fR classic
#
echo Packaging classic-linux32.sapp
#
tar xfz eclipse-SDK-$VERSION-linux-gtk.tar.gz
mv eclipse classic
rm classic/eclipse
cp $PACKAGE_HOME/linux/eclipse classic/
chmod 755 classic/eclipse
cp -R $PACKAGE_HOME/links classic/
sireum tools sapper classic-linux32.sapp classic
#tar cfz classic-linux32.tar.gz classic
rm -fR classic
#
echo Packaging classic-win64.sapp
#
unzip -q eclipse-SDK-$VERSION-win32-x86_64.zip
mv eclipse classic
rm classic/eclipse.exe
rm classic/eclipsec.exe
cp $PACKAGE_HOME/windows/eclipse.bat classic/
cp $PACKAGE_HOME/windows/README.TXT classic/
cp -R $PACKAGE_HOME/links classic/
zip -q -r classic-win64.sapp classic
rm -R classic
#
echo Packaging classic-win32.sapp
#
unzip -q eclipse-SDK-$VERSION-win32.zip
mv eclipse classic
rm classic/eclipse.exe
rm classic/eclipsec.exe
cp $PACKAGE_HOME/windows/eclipse.bat classic/
cp $PACKAGE_HOME/windows/README.TXT classic/
cp -R $PACKAGE_HOME/links classic/
zip -q -r classic-win32.sapp classic
rm -R classic
echo
echo ...done!
