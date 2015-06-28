#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export EGIT_DROP_URL=${EGIT_URL:=http://ftp.osuosl.org/pub/eclipse/egit/updates/org.eclipse.egit.repository-}${EGIT_VERSION:=4.0.1}.${EGIT_BUILD_DATE:=201506240215}-r.zip
export EGIT_DROP=org.eclipse.egit.repository-$EGIT_VERSION.$EGIT_BUILD_DATE-r.zip
if [ ! -f $EGIT_DROP ]; then
  echo
  echo Downloading EGIT $EGIT_VERSION.$EGIT_BUILD_DATE
  echo
  wget $EGIT_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/egit" > ${ELINKDIR}/egit.link
mkdir -p EclipseBase/egit/eclipse
cd EclipseBase/egit/eclipse
> .eclipseextension
unzip -oq ../../../$EGIT_DROP
cd ../..
zip -rq egit.sapp egit ../${ELINKDIR}/egit.link
cd ..
rm -fR eclipse EclipseBase/egit
echo
echo ...done!
