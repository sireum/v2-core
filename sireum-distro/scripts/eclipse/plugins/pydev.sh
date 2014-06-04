#!/bin/bash
#
export PYDEV_DROP_URL=${PYDEV_URL:=http://downloads.sourceforge.net/project/pydev/pydev/PyDev%20}${PYDEV_VERSION:=3.5.0}/PyDev%20${PYDEV_VERSION}.zip
export PYDEV_DROP=PyDev-$PYDEV_VERSION.zip
if [ ! -f $PYDEV_DROP ]; then
  echo
  echo Downloading PyDev $PYDEV_VERSION
  echo
  wget $PYDEV_DROP_URL -O $PYDEV_DROP
  echo
fi
#
# PyDev
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../bakardev/eclipse-plugins-pydev/pydev" > eclipse/dsl/links/pydev.link 
mkdir BakarDev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev/pydev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev/pydev/eclipse 2> /dev/null
cd BakarDev/eclipse-plugins-pydev/pydev/eclipse
> .eclipseextension
unzip -oq ../../../../$PYDEV_DROP
cd ../../..
zip -rq eclipse-plugins-pydev.sapp eclipse-plugins-pydev ../eclipse/dsl/links/pydev.link
cd ..
rm -fR eclipse BakarDev/eclipse-plugins-pydev
echo
echo ...done!
