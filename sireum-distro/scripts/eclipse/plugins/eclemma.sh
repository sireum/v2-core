#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export ECLEMMA_DROP_URL=http://download.eclipselab.org/eclemma/release/eclemma-2.3.2.zip
export ECLEMMA_DROP=${ECLEMMA_DROP_URL##*/}
if [ ! -f $ECLEMMA_DROP ]; then
  echo
  echo Downloading ECLEMMA $ECLEMMA_DROP
  echo
  wget $ECLEMMA_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/eclemma" > eclipse/dsl/links/eclemma.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/eclemma 2> /dev/null
mkdir EclipseBase/eclemma/eclipse 2> /dev/null
cd EclipseBase/eclemma/eclipse
> .eclipseextension
unzip -oq ../../../$ECLEMMA_DROP
cd ../..
zip -rq eclemma.sapp eclemma ../eclipse/dsl/links/eclemma.link
cd ..
rm -fR eclipse EclipseBase/eclemma
echo
echo ...done!
