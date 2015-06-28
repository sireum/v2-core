#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export MARKDOWN_DROP_URL=http://dl.bintray.com/enide/Markdown/1.1.0/plugins/winterwell.markdown_1.1.0.201402240523.jar
export MARKDOWN_DROP=${MARKDOWN_DROP_URL##*/}
if [ ! -f $MARKDOWN_DROP ]; then
  echo
  echo Downloading MARKDOWN $MARKDOWN_DROP
  echo
  wget $MARKDOWN_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/markdown" > ${ELINKDIR}/markdown.link
mkdir -p EclipseBase/markdown/eclipse/plugins
mkdir -p EclipseBase/markdown/eclipse/features
> EclipseBase/markdown/eclipse/.eclipseextension
cp $MARKDOWN_DROP EclipseBase/markdown/eclipse/plugins
cd EclipseBase
zip -rq markdown.sapp markdown ../${ELINKDIR}/markdown.link
cd ..
rm -fR eclipse EclipseBase/markdown
echo
echo ...done!
