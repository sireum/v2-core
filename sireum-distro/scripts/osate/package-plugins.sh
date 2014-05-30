#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export EGIT_DROP_URL=${EGIT_URL:=http://ftp.osuosl.org/pub/eclipse/egit/updates/org.eclipse.egit.repository-}${EGIT_VERSION:=3.3.2}.${EGIT_BUILD_DATE:=201404171909}-r.zip
export EGIT_DROP=org.eclipse.egit.repository-$EGIT_VERSION.$EGIT_BUILD_DATE-r.zip
if [ ! -f $EGIT_DROP ]; then
  echo
  echo Downloading EGIT $EGIT_VERSION.$EGIT_BUILD_DATE
  echo
  wget $EGIT_DROP_URL
  echo
fi
#export RDALTE_DROP_URL=${RDALTE_URL:=http://people.cis.ksu.edu/~jjedrys/downloads/Rdalte.zip}
#export RDALTE_VERSION=1.0.0.201310032002
#export RDALTE_DROP=Rdalte.zip
#if [ ! -f $RDALTE_DROP ]; then
#  echo
#  echo Downloading Rdalte $RDALTE_VERSION
#  echo
#  wget $RDALTE_DROP_URL
#  echo
#fi
#export BLESS_VERSION=0.0.3
#export BLESS_DROP_URL=${BLESS_URL:=http://people.cis.ksu.edu/~jjedrys/downloads/BLESSupdate.zip}
#export BLESS_DROP=BLESSupdate.zip
#if [ ! -f $BLESS_DROP ]; then
#  echo
#  echo Downloading BLESS $BLESS_VERSION
#  echo
#  wget $BLESS_DROP_URL
#  echo
#fi
#mkdir rdalte 2> /dev/null
#cd rdalte
#unzip -oq ../$RDALTE_DROP
#mv Rdalte eclipse
#> eclipse/.eclipseextension
#cd ..
mkdir egit 2> /dev/null
mkdir egit/eclipse 2> /dev/null
cd egit/eclipse
unzip -oq ../../$EGIT_DROP
> .eclipseextension
cd ../..
#mkdir bless 2> /dev/null
#cd bless
#unzip -oq ../$BLESS_DROP
#mv BLESSupdate eclipse
#> eclipse/.eclipseextension
#cd ..
#zip -rq rdalte.sapp rdalte
zip -rq egit.sapp egit 
#zip -rq bless.sapp bless
#zip -rq osate2dep.sapp osate2dep
echo
echo ...done!
