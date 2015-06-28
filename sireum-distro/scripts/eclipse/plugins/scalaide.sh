#!/bin/bash
ELINKDIR=eclipse/jee/links
RELDIR=../..
export SCIDE_DROP_URL=http://download.scala-ide.org/sdk/lithium/e44/scala211/stable/update-site.zip
export SCIDE_DROP=${SCIDE_DROP_URL##*/}
if [ ! -f $SCIDE_DROP ]; then
  echo
  echo Downloading Scala IDE $SCIDE_DROP
  echo
  wget $SCIDE_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/sireumdev/eclipse-plugins-scala" > ${ELINKDIR}/scala.link
mkdir -p SireumDev/eclipse-plugins-scala
cd SireumDev/eclipse-plugins-scala
unzip -oq ../../$SCIDE_DROP
mv site eclipse
> eclipse/.eclipseextension
cd ..
zip -rq eclipse-plugins-scala.sapp eclipse-plugins-scala ../${ELINKDIR}/scala.link
cd ..
rm -fR SireumDev/eclipse-plugins-scala eclipse
echo
echo ...done!
