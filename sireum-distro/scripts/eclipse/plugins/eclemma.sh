#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export ECLEMMA_DROP_URL=http://download.eclipselab.org/eclemma/release/eclemma-2.3.2.zip
export ECLEMMA_DROP=${ECLEMMA_DROP_URL##*/}
if [ ! -f $ECLEMMA_DROP ]; then
  echo
  echo Downloading ECLEMMA $ECLEMMA_DROP
  echo
  wget $ECLEMMA_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/eclemma" > ${ELINKDIR}/eclemma.link
mkdir -p EclipseBase/eclemma/eclipse
cd EclipseBase/eclemma/eclipse
> .eclipseextension
unzip -oq ../../../$ECLEMMA_DROP
cd ../..
zip -rq eclemma.sapp eclemma ../${ELINKDIR}/eclemma.link
cd ..
rm -fR eclipse EclipseBase/eclemma
echo
echo ...done!
