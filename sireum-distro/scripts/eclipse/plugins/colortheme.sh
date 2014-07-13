#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export COLOR_THEME_DROP_URL=http://eclipse-color-theme.github.io/update/eclipse-color-theme-update-site.zip
export COLOR_THEME_DROP=${COLOR_THEME_DROP_URL##*/}
if [ ! -f $COLOR_THEME_DROP ]; then
  echo
  echo Downloading COLOR_THEME $COLOR_THEME_DROP
  echo
  wget $COLOR_THEME_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/colortheme" > eclipse/dsl/links/colortheme.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/colortheme 2> /dev/null
mkdir EclipseBase/colortheme/eclipse 2> /dev/null
cd EclipseBase/colortheme/eclipse
> .eclipseextension
unzip -oq ../../../$COLOR_THEME_DROP
cd ../..
zip -rq colortheme.sapp colortheme ../eclipse/dsl/links/colortheme.link
cd ..
rm -fR eclipse EclipseBase/colortheme
echo
echo ...done!
