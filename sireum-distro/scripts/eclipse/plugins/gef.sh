#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export GEF_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/gef/downloads/drops/3.9.100/R201405261516/GEF-ALL-3.9.100.zip
export GEF_DROP=${GEF_DROP_URL##*/}
if [ ! -f $GEF_DROP ]; then
  echo
  echo Downloading GEF SDK $GEF_DROP
  echo
  wget $GEF_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/gef" > eclipse/dsl/links/gef.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/gef 2> /dev/null
cd EclipseBase/gef
unzip -oq ../../$GEF_DROP
> eclipse/.eclipseextension
cd ..
zip -rq gef.sapp gef ../eclipse/dsl/links/gef.link
cd ..
rm -fR eclipse EclipseBase/gef
echo
echo ...done!
