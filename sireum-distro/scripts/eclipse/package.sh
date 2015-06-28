#!/bin/bash
#
# This script assumes that Sireum is reachable from the PATH environment variable.
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export DROP_URL=${MIRROR_URL:=http://ftp.osuosl.org/pub/eclipse/technology/epp/downloads/release}/${REL:=mars/R}
export VERSION=jee-mars-R
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
if [ ! -f eclipse-$VERSION-win32-x86_64.zip ]; then
  echo Downloading Eclipse $VERSION win64
  echo
  wget $DROP_URL/eclipse-$VERSION-win32-x86_64.zip
  echo
fi
#
echo Packaging jee-mac64.sapp
#
tar xfz eclipse-$VERSION-macosx-cocoa-x86_64.tar.gz
mv Eclipse.app/Contents/Eclipse jee
mv Eclipse.app/Contents/Resources/Eclipse.icns jee/
rm -fR Eclipse.app
mkdir jee/links
sireum tools sapper jee-mac64.sapp jee
rm -R jee
#
echo Packaging jee-linux64.sapp
#
tar xfz eclipse-$VERSION-linux-gtk-x86_64.tar.gz
mv eclipse jee
rm jee/eclipse
mkdir jee/links
#mkdir -p jee/Eclipse.app/Contents/Eclipse/links
sireum tools sapper jee-linux64.sapp jee
rm -fR jee
#
echo Packaging jee-win64.sapp
#
unzip -q eclipse-$VERSION-win32-x86_64.zip
mv eclipse jee
rm jee/eclipse.exe
rm jee/eclipsec.exe
mkdir jee/links
#mkdir -p jee/Eclipse.app/Contents/Eclipse/links
zip -q -r jee-win64.sapp jee
rm -R jee
#
echo
echo ...done!
