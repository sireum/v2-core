#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export COLOR_THEME_DROP_URL=http://eclipse-color-theme.github.io/update/eclipse-color-theme-update-site.zip
export COLOR_THEME_DROP=${COLOR_THEME_DROP_URL##*/}
if [ ! -f $COLOR_THEME_DROP ]; then
  echo
  echo Downloading COLOR_THEME $COLOR_THEME_DROP
  echo
  wget $COLOR_THEME_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/colortheme" > ${ELINKDIR}/colortheme.link
mkdir -p EclipseBase/colortheme/eclipse
cd EclipseBase/colortheme/eclipse
> .eclipseextension
unzip -oq ../../../$COLOR_THEME_DROP
mv update/* .
rm -fR update
cd ../..
zip -rq colortheme.sapp colortheme ../${ELINKDIR}/colortheme.link
cd ..
rm -fR eclipse EclipseBase/colortheme
echo
echo ...done!
