#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export EMF_DROP_URL=http://ftp.osuosl.org/pub/eclipse/modeling/emf/emf/downloads/drops/2.10.1/R201409011055/emf-xsd-SDK-2.10.1.zip
export EMF_DROP=${EMF_DROP_URL##*/}
if [ ! -f $EMF_DROP ]; then
  echo
  echo Downloading EMF $EMF_DROP
  echo
  wget $EMF_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/emf" > eclipse/dsl/links/emf.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/emf 2> /dev/null
mkdir EclipseBase/emf/eclipse 2> /dev/null
cd EclipseBase/emf/eclipse
> .eclipseextension
unzip -oq ../../../$EMF_DROP
cd ../..
zip -rq emf.sapp emf ../eclipse/dsl/links/emf.link
cd ..
rm -fR eclipse EclipseBase/emf
echo
echo ...done!
