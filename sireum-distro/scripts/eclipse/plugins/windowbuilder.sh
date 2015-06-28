#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export WB_DROP_URL=http://ftp.osuosl.org/pub/eclipse/windowbuilder/WB/release/R201506241200-1/WB_v1.8.0_UpdateSite_for_Eclipse4.5.zip
export WB_DROP=${WB_DROP_URL##*/}
if [ ! -f $WB_DROP ]; then
  echo
  echo Downloading WindowBuilder $WB_DROP
  echo
  wget $WB_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/windowbuilder" > ${ELINKDIR}/windowbuilder.link
mkdir -p EclipseBase/windowbuilder/eclipse
cd EclipseBase/windowbuilder/eclipse
> .eclipseextension
unzip -oq ../../../$WB_DROP
cd ../..
zip -rq windowbuilder.sapp windowbuilder ../${ELINKDIR}/windowbuilder.link
cd ..
rm -fR eclipse EclipseBase/windowbuilder
echo
echo ...done!
