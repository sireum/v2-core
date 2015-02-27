#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SCIDE_DROP_URL=http://download.scala-ide.org/sdk/lithium/e44/scala211/stable/update-site.zip
export SCIDE_DROP=${SCIDE_DROP_URL##*/}
if [ ! -f $SCIDE_DROP ]; then
  echo
  echo Downloading Scala IDE $SCIDE_DROP
  echo
  wget $SCIDE_DROP_URL
  echo
fi
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
rm -fR SireumDev/eclipse-plugins-scala eclipse
echo
echo ...done!
