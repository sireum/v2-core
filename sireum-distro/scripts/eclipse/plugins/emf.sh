#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export EMF_DROP_URL=${EMF_URL:=http://ftp.osuosl.org/pub/eclipse/modeling/emf/emf/downloads/drops/}${EMF_VERSION:=2.9.2}/${EMF_RELEASE:=R201402031126}/${EMF_DROP:=emf-xsd-SDK-$EMF_VERSION.zip}
if [ ! -f $EMF_DROP ]; then
  echo
  echo Downloading EMF $EMF_VERSION
  echo
  wget $EMF_DROP_URL
  echo
fi
#
# EMF
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../webdev/emf" > eclipse/dsl/links/emf.link 
mkdir WebDev 2> /dev/null
mkdir WebDev/emf 2> /dev/null
mkdir WebDev/emf/eclipse 2> /dev/null
cd WebDev/emf/eclipse
> .eclipseextension
cd ..
unzip -oq ../../$EMF_DROP
cd ..
zip -rq emf.sapp emf ../eclipse/dsl/links/emf.link
cd ..
rm -fR eclipse WebDev/emf
echo
echo ...done!
