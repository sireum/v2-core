#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export PYDEV_DROP_URL=${PYDEV_URL:=http://downloads.sourceforge.net/project/pydev/pydev/PyDev%20}${PYDEV_VERSION:=4.4.0}/PyDev%20${PYDEV_VERSION}.zip
export PYDEV_DROP=PyDev-$PYDEV_VERSION.zip
if [ ! -f $PYDEV_DROP ]; then
  echo
  echo Downloading PyDev $PYDEV_VERSION
  echo
  wget $PYDEV_DROP_URL -O $PYDEV_DROP
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/pydev" > ${ELINKDIR}/pydev.link
mkdir -p EclipseBase/pydev/eclipse
cd EclipseBase/pydev/eclipse
> .eclipseextension
unzip -oq ../../../$PYDEV_DROP
cd ../..
zip -rq pydev.sapp pydev ../${ELINKDIR}/pydev.link
cd ..
rm -fR eclipse EclipseBase/pydev
echo
echo ...done!
