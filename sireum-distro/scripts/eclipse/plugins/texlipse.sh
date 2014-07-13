#!/bin/bash
#
export TEXLIPSE_DROP_URL=${TEXLIPSE_URL:=http://downloads.sourceforge.net/project/texlipse/texlipse%20plugin/}${TEXLIPSE_VERSION:=1.5.0}/texlipse_$TEXLIPSE_VERSION%20src.zip
export TEXLIPSE_DROP="texlipse_$TEXLIPSE_VERSION src.zip"
if [ ! -f "$TEXLIPSE_DROP" ]; then
  echo
  echo Downloading Texlipse $TEXLIPSE_VERSION
  echo
  wget $TEXLIPSE_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/texlipse" > eclipse/dsl/links/texlipse.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/texlipse 2> /dev/null
mkdir EclipseBase/texlipse/eclipse 2> /dev/null
cd EclipseBase/texlipse
aspell --encoding=UTF-8 --lang=en dump master > en.dict
cd eclipse
> .eclipseextension
unzip -oq ../../../"$TEXLIPSE_DROP"
cd ../..
zip -rq texlipse.sapp texlipse ../eclipse/dsl/links/texlipse.link
cd ..
rm -fR eclipse EclipseBase/texlipse
echo
echo ...done!
