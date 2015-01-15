#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export GFMVIEWER_DROP_URL=http://dl.bintray.com/satyagraha/generic/1.9.3/plugins/code.satyagraha.gfm.viewer.plugin_1.9.3.jar
export GFMVIEWER_DROP=${GFMVIEWER_DROP_URL##*/}
if [ ! -f $GFMVIEWER_DROP ]; then
  echo
  echo Downloading GFMVIEWER $GFMVIEWER_DROP
  echo
  wget $GFMVIEWER_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/gfmviewer" > eclipse/dsl/links/gfmviewer.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/gfmviewer 2> /dev/null
mkdir EclipseBase/gfmviewer/eclipse 2> /dev/null
mkdir EclipseBase/gfmviewer/eclipse/plugins 2> /dev/null
mkdir EclipseBase/gfmviewer/eclipse/features 2> /dev/null
> EclipseBase/gfmviewer/eclipse/.eclipseextension
cp $GFMVIEWER_DROP EclipseBase/gfmviewer/eclipse/plugins
cd EclipseBase
zip -rq gfmviewer.sapp gfmviewer ../eclipse/dsl/links/gfmviewer.link
cd ..
rm -fR eclipse EclipseBase/gfmviewer
echo
echo ...done!
