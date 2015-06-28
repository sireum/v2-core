#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export GFMVIEWER_DROP_URL=http://dl.bintray.com/satyagraha/generic/1.9.3/plugins/code.satyagraha.gfm.viewer.plugin_1.9.3.jar
export GFMVIEWER_DROP=${GFMVIEWER_DROP_URL##*/}
if [ ! -f $GFMVIEWER_DROP ]; then
  echo
  echo Downloading GFMVIEWER $GFMVIEWER_DROP
  echo
  wget $GFMVIEWER_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/gfmviewer" > ${ELINKDIR}/gfmviewer.link
mkdir -p EclipseBase/gfmviewer/eclipse/plugins
mkdir -p EclipseBase/gfmviewer/eclipse/features
> EclipseBase/gfmviewer/eclipse/.eclipseextension
cp $GFMVIEWER_DROP EclipseBase/gfmviewer/eclipse/plugins
cd EclipseBase
zip -rq gfmviewer.sapp gfmviewer ../${ELINKDIR}/gfmviewer.link
cd ..
rm -fR eclipse EclipseBase/gfmviewer
echo
echo ...done!
