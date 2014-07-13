#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export AID_DROP_URL=http://www.objectaid.net/update/objectaid-1.1.6.zip
export AID_DROP=${AID_DROP_URL##*/}
if [ ! -f $AID_DROP ]; then
  echo
  echo Downloading ObjectAid $AID_DROP
  echo
  wget $AID_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/objectaid" > eclipse/dsl/links/objectaid.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/objectaid 2> /dev/null
mkdir EclipseBase/objectaid/eclipse 2> /dev/null
cd EclipseBase/objectaid/eclipse
> .eclipseextension
unzip -oq ../../../$AID_DROP
cd ../..
zip -rq objectaid.sapp objectaid ../eclipse/dsl/links/objectaid.link
cd ..
rm -fR eclipse EclipseBase/objectaid
echo
echo ...done!
