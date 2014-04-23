#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export VRAPPER_DROP_URL=${VRAPPER_URL:=http://sourceforge.net/projects/vrapper/files/vrapper/}${VRAPPER_VERSION:=0.42.0}/${VRAPPER_DROP:=vrapper_${VRAPPER_VERSION}_20140406.zip}
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
echo "path=../../vim/vrapper" > eclipse/dsl/links/vrapper.link 
mkdir Vim 2> /dev/null
mkdir Vim/vrapper 2> /dev/null
mkdir Vim/vrapper/eclipse 2> /dev/null
cd Vim/vrapper/eclipse
unzip -oq ../../../$VRAPPER_DROP
> .eclipseextension
cd ../..
zip -rq vrapper.sapp vrapper ../eclipse/dsl/links/vrapper.link
cd ..
rm -fR Vim/vrapper
echo
echo ...done!
