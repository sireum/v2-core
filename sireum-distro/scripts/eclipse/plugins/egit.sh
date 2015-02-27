#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export EGIT_DROP_URL=${EGIT_URL:=http://ftp.osuosl.org/pub/eclipse/egit/updates/org.eclipse.egit.repository-}${EGIT_VERSION:=3.6.2}.${EGIT_BUILD_DATE:=201501210735}-r.zip
export EGIT_DROP=org.eclipse.egit.repository-$EGIT_VERSION.$EGIT_BUILD_DATE-r.zip
if [ ! -f $EGIT_DROP ]; then
  echo
  echo Downloading EGIT $EGIT_VERSION.$EGIT_BUILD_DATE
  echo
  wget $EGIT_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/egit" > eclipse/dsl/links/egit.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/egit 2> /dev/null
mkdir EclipseBase/egit/eclipse 2> /dev/null
cd EclipseBase/egit/eclipse
> .eclipseextension
unzip -oq ../../../$EGIT_DROP
cd ../..
zip -rq egit.sapp egit ../eclipse/dsl/links/egit.link
cd ..
rm -fR eclipse EclipseBase/egit
echo
echo ...done!
