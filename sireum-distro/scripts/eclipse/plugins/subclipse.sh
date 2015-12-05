#!/bin/bash
ELINKDIR=eclipse/jee/links
RELDIR=../..
export SUBCLIPSE_DROP_URL=http://subclipse.tigris.org/files/documents/906/49433/site-1.10.10.zip
export SUBCLIPSE_DROP=${SUBCLIPSE_DROP_URL##*/}
if [ ! -f "$SUBCLIPSE_DROP" ]; then
  echo
  echo Downloading Subclipse $SUBCLIPSE_DROP
  echo
  wget $SUBCLIPSE_DROP_URL
  echo
fi
#
# Vrapper
#
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/subclipse" > ${ELINKDIR}/subclipse.link
mkdir -p EclipseBase/subclipse/eclipse
cd EclipseBase/subclipse/eclipse
unzip -oq ../../../$SUBCLIPSE_DROP
> .eclipseextension
cd ../..
zip -rq subclipse.sapp subclipse ../${ELINKDIR}/subclipse.link
cd ..
rm -fR EclipseBase/subclipse
echo
echo ...done!
