#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export ADT_DROP_URL=http://dl.google.com/android/ADT-23.0.6.zip
export ADT_DROP=${ADT_DROP_URL##*/}
if [ ! -f $ADT_DROP ]; then
  echo
  echo Downloading ADT $ADT_DROP
  echo
  wget $ADT_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/amandroiddev/adt" > ${ELINKDIR}/adt.link
mkdir -p AmandroidDev/adt/eclipse
cd AmandroidDev/adt/eclipse
> .eclipseextension
unzip -oq ../../../$ADT_DROP
cd ../..
zip -rq adt.sapp adt ../${ELINKDIR}/adt.link
cd ..
rm -fR eclipse AmandroidDev/adt
echo
echo ...done!
