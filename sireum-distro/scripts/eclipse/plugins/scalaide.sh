#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SCIDE_DROP_URL=${SCIDE_URL:=http://download.scala-ide.org/sdk/e38/scala210/}${R:=dev}/${SCIDE_DROP:=update-site.zip}
if [ ! -f $SCIDE_DROP ]; then
  echo
  echo Downloading Scala IDE $R
  echo
  wget $SCIDE_DROP_URL
  echo
fi
#
# Scala IDE
#
mkdir eclipse 2> /dev/null
mkdir eclipse/classic 2> /dev/null
mkdir eclipse/classic/links 2> /dev/null
echo "path=../../sireumdev/eclipse-plugins-scala" > eclipse/classic/links/scala.link 
mkdir SireumDev 2> /dev/null
mkdir SireumDev/eclipse-plugins-scala 2> /dev/null
cd SireumDev/eclipse-plugins-scala
unzip -oq ../../$SCIDE_DROP
mv site eclipse
> eclipse/.eclipseextension
cd ..
zip -rq eclipse-plugins-scala.sapp eclipse-plugins-scala ../eclipse/classic/links/scala.link
cd ..
rm -fR SireumDev/eclipse-plugins-scala
echo
echo ...done!
