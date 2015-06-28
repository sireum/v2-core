#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export ASTVIEW_DROP_URL=http://archive.eclipse.org/jdt/ui/update-site/plugins/org.eclipse.jdt.astview_1.1.9.201406161921.jar
export ASTVIEW_DROP=${ASTVIEW_DROP_URL##*/}
if [ ! -f $ASTVIEW_DROP ]; then
  echo
  echo Downloading ASTVIEW $ASTVIEW_DROP
  echo
  wget $ASTVIEW_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/astview" > ${ELINKDIR}/astview.link
mkdir -p EclipseBase/astview/eclipse/plugins
mkdir -p EclipseBase/astview/eclipse/features
> EclipseBase/astview/eclipse/.eclipseextension
cp $ASTVIEW_DROP EclipseBase/astview/eclipse/plugins
cd EclipseBase
zip -rq astview.sapp astview ../${ELINKDIR}/astview.link
cd ..
rm -fR eclipse EclipseBase/astview
echo
echo ...done!
