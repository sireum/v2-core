#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SCIDE_DROP_URL=${SCIDE_URL:=http://download.scala-ide.org/sdk/helium/e38/scala210/}${R:=dev}/${SCIDE_DROP:=update-site.zip}
if [ ! -f $SCIDE_DROP ]; then
  echo
  echo Downloading Scala IDE $R
  echo
  wget $SCIDE_DROP_URL
  echo
fi
export VRAPPER_DROP_URL=${VRAPPER_URL:=http://sourceforge.net/projects/vrapper/files/vrapper/}${VRAPPER_VERSION:=0.40.0}/${VRAPPER_DROP:=vrapper_${VRAPPER_VERSION}_20140208.zip}
if [ ! -f "$VRAPPER_DROP" ]; then
  echo
  echo Downloading Vrapper $VRAPPER_DROP
  echo
  wget $VRAPPER_DROP_URL
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
rm -fR SireumDev/eclipse-plugins-scala
#
# Vrapper
#
echo "path=../../sireumdev/vrapper" > eclipse/dsl/links/vrapper.link 
mkdir SireumDev/vrapper 2> /dev/null
mkdir SireumDev/vrapper/eclipse 2> /dev/null
cd SireumDev/vrapper/eclipse
unzip -oq ../../../$VRAPPER_DROP
> .eclipseextension
cd ../..
zip -rq vrapper.sapp vrapper ../eclipse/dsl/links/vrapper.link
cd ..
rm -fR SireumDev/vrapper
echo
echo ...done!
