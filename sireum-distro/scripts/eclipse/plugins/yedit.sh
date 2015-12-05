#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export YEDIT_DROP_URL=http://dadacoalition.org/yedit/plugins/org.dadacoalition.yedit_1.0.20.201509041456-RELEASE.jar
export YEDIT_DROP=${YEDIT_DROP_URL##*/}
if [ ! -f $YEDIT_DROP ]; then
  echo
  echo Downloading YEDIT $YEDIT_DROP
  echo
  wget $YEDIT_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/yedit" > ${ELINKDIR}/yedit.link
mkdir -p EclipseBase/yedit/eclipse/plugins
mkdir -p EclipseBase/yedit/eclipse/features
> EclipseBase/yedit/eclipse/.eclipseextension
cp $YEDIT_DROP EclipseBase/yedit/eclipse/plugins
cd EclipseBase
zip -rq yedit.sapp yedit ../${ELINKDIR}/yedit.link
cd ..
rm -fR eclipse EclipseBase/yedit
echo
echo ...done!
