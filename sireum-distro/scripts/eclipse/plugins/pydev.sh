#!/bin/bash
#
export PYDEV_DROP_URL=${PYDEV_URL:=http://downloads.sourceforge.net/project/pydev/pydev/PyDev%20}${PYDEV_VERSION:=4.0.0}/PyDev%20${PYDEV_VERSION}.zip
export PYDEV_DROP=PyDev-$PYDEV_VERSION.zip
if [ ! -f $PYDEV_DROP ]; then
  echo
  echo Downloading PyDev $PYDEV_VERSION
  echo
  wget $PYDEV_DROP_URL -O $PYDEV_DROP
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/pydev" > eclipse/dsl/links/pydev.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/pydev 2> /dev/null
mkdir EclipseBase/pydev/eclipse 2> /dev/null
cd EclipseBase/pydev/eclipse
> .eclipseextension
unzip -oq ../../../$PYDEV_DROP
cd ../..
zip -rq pydev.sapp pydev ../eclipse/dsl/links/pydev.link
cd ..
rm -fR eclipse EclipseBase/pydev
echo
echo ...done!
