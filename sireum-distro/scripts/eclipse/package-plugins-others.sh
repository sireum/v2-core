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
export CDT_DROP_URL=${CDT_URL:=http://ftp.osuosl.org/pub/eclipse/tools/cdt/releases/}${CDT_RELEASE:=kepler/sr1}/cdt-master-${CDT_VERSION:=8.2.1}.zip
export CDT_DROP=cdt-master-$CDT_VERSION.zip
if [ ! -f $CDT_DROP ]; then
  echo
  echo Downloading CDT $CDT_VERSION
  echo
  wget $CDT_DROP_URL
  echo
fi
export PYDEV_DROP_URL=${PYDEV_URL:=http://downloads.sourceforge.net/project/pydev/pydev/PyDev%20}${PYDEV_VERSION:=2.8.2}/PyDev%20${PYDEV_VERSION}.zip
export PYDEV_DROP=PyDev-$PYDEV_VERSION.zip
if [ ! -f $PYDEV_DROP ]; then
  echo
  echo Downloading PyDev $PYDEV_VERSION
  echo
  wget $PYDEV_DROP_URL -O $PYDEV_DROP
  echo
fi
export AID_DROP_URL=${AID_URL:=http://www.objectaid.net/update/objectaid-}${AID_VERSION:=1.1.4}.zip
export AID_DROP=objectaid-$AID_VERSION.zip
if [ ! -f $AID_DROP ]; then
  echo
  echo Downloading ObjectAid $AID_VERSION
  echo
  wget $AID_DROP_URL
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
#
# CDT
#
mkdir eclipse 2> /dev/null
mkdir eclipse/classic 2> /dev/null
mkdir eclipse/classic/links 2> /dev/null
echo "path=../../compilerdev/team/cdt" > eclipse/classic/links/cdt.link 
mkdir CompilerDev 2> /dev/null
mkdir CompilerDev/team 2> /dev/null
mkdir CompilerDev/team/cdt 2> /dev/null
mkdir CompilerDev/team/cdt/eclipse 2> /dev/null
cd CompilerDev/team/cdt/eclipse
> .eclipseextension
unzip -oq ../../../../$CDT_DROP
cd ../../..
zip -rq team.sapp team ../eclipse/classic/links/cdt.link
cd ..
rm -fR eclipse CompilerDev/team
#
# PyDev
#
mkdir eclipse 2> /dev/null
mkdir eclipse/classic 2> /dev/null
mkdir eclipse/classic/links 2> /dev/null
echo "path=../../bakardev/eclipse-plugins-pydev/pydev" > eclipse/classic/links/pydev.link 
mkdir BakarDev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev/pydev 2> /dev/null
mkdir BakarDev/eclipse-plugins-pydev/pydev/eclipse 2> /dev/null
cd BakarDev/eclipse-plugins-pydev/pydev/eclipse
> .eclipseextension
unzip -oq ../../../../$PYDEV_DROP
cd ../../..
zip -rq eclipse-plugins-pydev.sapp eclipse-plugins-pydev ../eclipse/classic/links/pydev.link
cd ..
rm -fR eclipse BakarDev/eclipse-plugins-pydev
#
# ObjectAid
#
mkdir eclipse 2> /dev/null
mkdir eclipse/classic 2> /dev/null
mkdir eclipse/classic/links 2> /dev/null
echo "path=../../sireumdev/objectaid" > eclipse/classic/links/objectaid.link 
mkdir SireumDev 2> /dev/null
mkdir SireumDev/objectaid 2> /dev/null
mkdir SireumDev/objectaid/eclipse 2> /dev/null
cd SireumDev/objectaid/eclipse
> .eclipseextension
unzip -oq ../../../$AID_DROP
cd ../..
zip -rq objectaid.sapp objectaid ../eclipse/classic/links/objectaid.link
cd ..
rm -fR eclipse SireumDev/objectaid
echo
echo ...done!
