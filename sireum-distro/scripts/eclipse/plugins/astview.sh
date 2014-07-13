#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export ASTVIEW_DROP_URL=http://archive.eclipse.org/jdt/ui/update-site/plugins/org.eclipse.jdt.astview_1.1.9.201406161921.jar
export ASTVIEW_DROP=${ASTVIEW_DROP_URL##*/}
if [ ! -f $ASTVIEW_DROP ]; then
  echo
  echo Downloading ASTVIEW $ASTVIEW_DROP
  echo
  wget $ASTVIEW_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/astview" > eclipse/dsl/links/astview.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/astview 2> /dev/null
mkdir EclipseBase/astview/eclipse 2> /dev/null
mkdir EclipseBase/astview/eclipse/plugins 2> /dev/null
mkdir EclipseBase/astview/eclipse/features 2> /dev/null
> EclipseBase/astview/eclipse/.eclipseextension
cp $ASTVIEW_DROP EclipseBase/astview/eclipse/plugins
cd EclipseBase
zip -rq astview.sapp astview ../eclipse/dsl/links/astview.link
cd ..
rm -fR eclipse EclipseBase/astview
echo
echo ...done!
