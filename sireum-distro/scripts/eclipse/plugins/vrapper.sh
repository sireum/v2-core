#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export VRAPPER_DROP_URL=http://sourceforge.net/projects/vrapper/files/vrapper/0.54.0/vrapper_0.54.0_20150405.zip
export VRAPPER_DROP=${VRAPPER_DROP_URL##*/}
if [ ! -f "$VRAPPER_DROP" ]; then
  echo
  echo Downloading Vrapper $VRAPPER_DROP
  echo
  wget $VRAPPER_DROP_URL
  echo
fi
#
# Vrapper
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/vrapper" > eclipse/dsl/links/vrapper.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/vrapper 2> /dev/null
mkdir EclipseBase/vrapper/eclipse 2> /dev/null
cd EclipseBase/vrapper/eclipse
unzip -oq ../../../$VRAPPER_DROP
> .eclipseextension
cd ../..
zip -rq vrapper.sapp vrapper ../eclipse/dsl/links/vrapper.link
cd ..
rm -fR EclipseBase/vrapper
echo
echo ...done!
