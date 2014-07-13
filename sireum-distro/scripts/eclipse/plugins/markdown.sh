#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export MARKDOWN_DROP_URL=http://dl.bintray.com/enide/Markdown/1.1.0/plugins/winterwell.markdown_1.1.0.201402240523.jar
export MARKDOWN_DROP=${MARKDOWN_DROP_URL##*/}
if [ ! -f $MARKDOWN_DROP ]; then
  echo
  echo Downloading MARKDOWN $MARKDOWN_DROP
  echo
  wget $MARKDOWN_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/markdown" > eclipse/dsl/links/markdown.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/markdown 2> /dev/null
mkdir EclipseBase/markdown/eclipse 2> /dev/null
mkdir EclipseBase/markdown/eclipse/plugins 2> /dev/null
mkdir EclipseBase/markdown/eclipse/features 2> /dev/null
> EclipseBase/markdown/eclipse/.eclipseextension
cp $MARKDOWN_DROP EclipseBase/markdown/eclipse/plugins
cd EclipseBase
zip -rq markdown.sapp markdown ../eclipse/dsl/links/markdown.link
cd ..
rm -fR eclipse EclipseBase/markdown
echo
echo ...done!
