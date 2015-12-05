#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export MWE_DROP_URL=http://ftp.osuosl.org/pub/eclipse/modeling/emft/mwe/downloads/drops/2.8.1/R201508030411/emft-mwe-Update-2.8.1.zip
export MWE_DROP=${MWE_DROP_URL##*/}
if [ ! -f $MWE_DROP ]; then
  echo
  echo Downloading MWE $MWE_DROP
  echo
  wget $MWE_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/mwe2" > ${ELINKDIR}/mwe2.link
mkdir -p EclipseBase/mwe2/eclipse
cd EclipseBase/mwe2/eclipse
unzip -oq ../../../$MWE_DROP
> .eclipseextension
cd ../..
zip -rq mwe2.sapp mwe2 ../${ELINKDIR}/mwe2.link
cd ..
rm -fR eclipse EclipseBase/mwe2
echo
echo ...done!
