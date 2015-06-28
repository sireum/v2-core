#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export TMF_DROP_URL=http://ftp.osuosl.org/pub/eclipse/modeling/tmf/xtext/downloads/drops/2.8.3/R201506010551/tmf-xtext-SDK-2.8.3.zip
export TMF_DROP=${TMF_DROP_URL##*/}
if [ ! -f $TMF_DROP ]; then
  echo
  echo Downloading TMF XTEXT SDK $TMF_DROP
  echo
  wget $TMF_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/tmf" > ${ELINKDIR}/tmf.link
mkdir -p EclipseBase/tmf
cd EclipseBase/tmf
unzip -oq ../../$TMF_DROP
> eclipse/.eclipseextension
cd ..
zip -rq tmf.sapp tmf ../${ELINKDIR}/tmf.link
cd ..
rm -fR eclipse EclipseBase/tmf
echo
echo ...done!
