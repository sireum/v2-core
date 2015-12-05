#!/bin/bash
ELINKDIR=eclipse/jee/links
RELDIR=../..
export VRAPPER_DROP_URL=http://sourceforge.net/projects/vrapper/files/vrapper/0.60.0/vrapper_0.60.0_20151004.zip
export VRAPPER_DROP=${VRAPPER_DROP_URL##*/}
if [ ! -f "$VRAPPER_DROP" ]; then
  echo
  echo Downloading Vrapper $VRAPPER_DROP
  echo
  wget $VRAPPER_DROP_URL
  echo
fi
#
# Vrapper
#
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/vrapper" > ${ELINKDIR}/vrapper.link
mkdir -p EclipseBase/vrapper/eclipse
cd EclipseBase/vrapper/eclipse
unzip -oq ../../../$VRAPPER_DROP
> .eclipseextension
cd ../..
zip -rq vrapper.sapp vrapper ../${ELINKDIR}/vrapper.link
cd ..
rm -fR EclipseBase/vrapper
echo
echo ...done!
