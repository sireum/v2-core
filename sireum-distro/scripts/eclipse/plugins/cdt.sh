#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export CDT_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/cdt/releases/8.6/cdt-8.6.0.zip
export CDT_DROP=${CDT_DROP_URL##*/}
if [ ! -f $CDT_DROP ]; then
  echo
  echo Downloading CDT $CDT_DROP
  echo
  wget $CDT_DROP_URL
  echo
fi
#
# CDT
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/cdt" > eclipse/dsl/links/cdt.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/cdt 2> /dev/null
mkdir EclipseBase/cdt/eclipse 2> /dev/null
cd EclipseBase/cdt/eclipse
> .eclipseextension
unzip -oq ../../../$CDT_DROP
cd ../..
zip -rq cdt.sapp cdt ../eclipse/dsl/links/cdt.link
cd ..
rm -fR eclipse EclipseBase/cdt
echo
echo ...done!
