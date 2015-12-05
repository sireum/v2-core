#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export CDT_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/cdt/releases/8.7/cdt-8.8.0.zip
export CDT_DROP=${CDT_DROP_URL##*/}
if [ ! -f $CDT_DROP ]; then
  echo
  echo Downloading CDT $CDT_DROP
  echo
  wget $CDT_DROP_URL
  echo
fi
#
# CDT
#
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/cdt" > ${ELINKDIR}/cdt.link
mkdir -p EclipseBase/cdt/eclipse
cd EclipseBase/cdt/eclipse
> .eclipseextension
unzip -oq ../../../$CDT_DROP
cd ../..
zip -rq cdt.sapp cdt ../${ELINKDIR}/cdt.link
cd ..
rm -fR eclipse EclipseBase/cdt
echo
echo ...done!
