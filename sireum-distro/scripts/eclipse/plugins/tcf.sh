#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export TCF_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/tcf/releases/1.2/r/tcf-1.2.0.zip
export TCF_DROP=${TCF_DROP_URL##*/}
if [ ! -f $TCF_DROP ]; then
  echo
  echo Downloading TCF $TCF_DROP
  echo
  wget $TCF_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/tcf" > eclipse/dsl/links/tcf.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/tcf 2> /dev/null
mkdir EclipseBase/tcf/eclipse 2> /dev/null
cd EclipseBase/tcf/eclipse
> .eclipseextension
unzip -oq ../../../$TCF_DROP
cd ../..
zip -rq tcf.sapp tcf ../eclipse/dsl/links/tcf.link
cd ..
rm -fR eclipse EclipseBase/tcf
echo
echo ...done!
