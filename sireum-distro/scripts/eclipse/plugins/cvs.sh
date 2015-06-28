#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export CVS_DROP_URL=http://ftp.osuosl.org/pub/eclipse/  eclipse/downloads/drops4/R-4.5-201506032000/org.eclipse.cvs-4.5.zip
export CVS_DROP=${CVS_DROP_URL##*/}
if [ ! -f $CVS_DROP ]; then
  echo
  echo Downloading CVS $CVS_DROP
  echo
  wget $CVS_DROP_URL
  echo
fi
#
# CVS
#
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/cvs" > ${ELINKDIR}/cvs.link
mkdir -p EclipseBase/cvs/eclipse
cd EclipseBase/cvs/eclipse
> .eclipseextension
unzip -oq ../../../$CVS_DROP
cd ../..
zip -rq cvs.sapp cvs ../${ELINKDIR}/cvs.link
cd ..
rm -fR eclipse EclipseBase/cvs
echo
echo ...done!
