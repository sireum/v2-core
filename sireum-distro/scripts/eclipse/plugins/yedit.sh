#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export YEDIT_DROP_URL=https://yedit.googlecode.com/files/org.dadacoalition.yedit_0.0.13.jar
export YEDIT_DROP=${YEDIT_DROP_URL##*/}
if [ ! -f $YEDIT_DROP ]; then
  echo
  echo Downloading YEDIT $YEDIT_DROP
  echo
  wget $YEDIT_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/yedit" > eclipse/dsl/links/yedit.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/yedit 2> /dev/null
mkdir EclipseBase/yedit/eclipse 2> /dev/null
mkdir EclipseBase/yedit/eclipse/plugins 2> /dev/null
mkdir EclipseBase/yedit/eclipse/features 2> /dev/null
> EclipseBase/yedit/eclipse/.eclipseextension
cp $YEDIT_DROP EclipseBase/yedit/eclipse/plugins
cd EclipseBase
zip -rq yedit.sapp yedit ../eclipse/dsl/links/yedit.link
cd ..
rm -fR eclipse EclipseBase/yedit
echo
echo ...done!
