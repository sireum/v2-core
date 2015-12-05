#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export XPAND_DROP_URL=http://ftp.osuosl.org/pub/eclipse/modeling/m2t/xpand/downloads/drops/2.1.0/R201505260349/m2t-xpand-Update-2.1.0.zip
export XPAND_DROP=${XPAND_DROP_URL##*/}
if [ ! -f $XPAND_DROP ]; then
  echo
  echo Downloading XPAND $XPAND_DROP
  echo
  wget $XPAND_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/xpand" > ${ELINKDIR}/xpand.link
mkdir -p EclipseBase/xpand/eclipse
cd EclipseBase/xpand/eclipse
unzip -oq ../../../$XPAND_DROP
> .eclipseextension
cd ../..
zip -rq xpand.sapp xpand ../${ELINKDIR}/xpand.link
cd ..
rm -fR eclipse EclipseBase/xpand
echo
echo ...done!
