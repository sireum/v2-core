#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export WB_DROP_URL=http://ftp.osuosl.org/pub/eclipse/windowbuilder/WB/release/R201406251200/WB_v1.7.0_UpdateSite_for_Eclipse4.4.zip
export WB_DROP=${WB_DROP_URL##*/}
if [ ! -f $WB_DROP ]; then
  echo
  echo Downloading WindowBuilder $WB_DROP
  echo
  wget $WB_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/windowbuilder" > eclipse/dsl/links/windowbuilder.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/windowbuilder 2> /dev/null
mkdir EclipseBase/windowbuilder/eclipse 2> /dev/null
cd EclipseBase/windowbuilder/eclipse
> .eclipseextension
unzip -oq ../../../$WB_DROP
cd ../..
zip -rq windowbuilder.sapp windowbuilder ../eclipse/dsl/links/windowbuilder.link
cd ..
rm -fR eclipse EclipseBase/windowbuilder
echo
echo ...done!
