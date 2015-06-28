#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export TEXLIPSE_DROP_URL=${TEXLIPSE_URL:=http://downloads.sourceforge.net/project/texlipse/texlipse%20plugin/}${TEXLIPSE_VERSION:=1.5.0}/texlipse_$TEXLIPSE_VERSION%20src.zip
export TEXLIPSE_DROP="texlipse_$TEXLIPSE_VERSION src.zip"
if [ ! -f "$TEXLIPSE_DROP" ]; then
  echo
  echo Downloading Texlipse $TEXLIPSE_VERSION
  echo
  wget $TEXLIPSE_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/texlipse" > ${ELINKDIR}/texlipse.link
mkdir -p EclipseBase/texlipse/eclipse
cd EclipseBase/texlipse
aspell --encoding=UTF-8 --lang=en dump master > en.dict
cd eclipse
> .eclipseextension
unzip -oq ../../../"$TEXLIPSE_DROP"
cd ../..
zip -rq texlipse.sapp texlipse ../${ELINKDIR}/texlipse.link
cd ..
rm -fR eclipse EclipseBase/texlipse
echo
echo ...done!
