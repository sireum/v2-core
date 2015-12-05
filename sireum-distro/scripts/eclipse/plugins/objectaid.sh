#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export AID_DROP_URL=http://www.objectaid.net/update/objectaid-1.1.9.zip
export AID_DROP=${AID_DROP_URL##*/}
if [ ! -f $AID_DROP ]; then
  echo
  echo Downloading ObjectAid $AID_DROP
  echo
  wget $AID_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/objectaid" > ${ELINKDIR}/objectaid.link
mkdir -p EclipseBase/objectaid/eclipse
cd EclipseBase/objectaid/eclipse
> .eclipseextension
unzip -oq ../../../$AID_DROP
cd ../..
zip -rq objectaid.sapp objectaid ../${ELINKDIR}/objectaid.link
cd ..
rm -fR eclipse EclipseBase/objectaid
echo
echo ...done!
