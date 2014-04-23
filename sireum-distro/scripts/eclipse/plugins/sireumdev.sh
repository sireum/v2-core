#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SCIDE_DROP_URL=${SCIDE_URL:=http://download.scala-ide.org/sdk/helium/e38/scala211/}${R:=stable}/${SCIDE_DROP:=update-site.zip}
if [ ! -f $SCIDE_DROP ]; then
  echo
  echo Downloading Scala IDE $R
  echo
  wget $SCIDE_DROP_URL
  echo
fi
export SUBCLIPSE_DROP_URL=${SUBCLIPSE_URL:=http://subclipse.tigris.org/files/documents/906/49378/site-}${SUBCLIPSE_VERSION:=1.10.4}.zip
export SUBCLIPSE_DROP=site-$SUBCLIPSE_VERSION.zip
if [ ! -f $SUBCLIPSE_DROP ]; then
  echo
  echo Downloading Subclipse $SUBCLIPSE_VERSION
  echo
  wget $SUBCLIPSE_DROP_URL
  echo
fi
#
# Scala IDE
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../sireumdev/eclipse-plugins-scala" > eclipse/dsl/links/scala.link 
mkdir SireumDev 2> /dev/null
mkdir SireumDev/eclipse-plugins-scala 2> /dev/null
cd SireumDev/eclipse-plugins-scala
unzip -oq ../../$SCIDE_DROP
mv site eclipse
> eclipse/.eclipseextension
cd ..
zip -rq eclipse-plugins-scala.sapp eclipse-plugins-scala ../eclipse/dsl/links/scala.link
cd ..
echo "path=../../sireumdev/subclipse" > eclipse/dsl/links/subclipse.link 
mkdir SireumDev/subclipse 2> /dev/null
mkdir SireumDev/subclipse/eclipse 2> /dev/null
cd SireumDev/subclipse/eclipse
unzip -oq ../../../$SUBCLIPSE_DROP
> .eclipseextension
cd ../..
zip -rq subclipse.sapp subclipse ../eclipse/dsl/links/subclipse.link
cd ..
rm -fR SireumDev/eclipse-plugins-scala SireumDev/subclipse
echo
echo ...done!
