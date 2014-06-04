#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SUBVERSIVE_DROP_URL=http://ftp.osuosl.org/pub/eclipse/technology/subversive/2.0/builds/Subversive-2.0.0.I20140519-1700.zip
export SUBVERSIVE_DROP=${SUBVERSIVE_DROP_URL##*/}
if [ ! -f $SUBVERSIVE_DROP ]; then
  echo
  echo Downloading Subversive $SUBVERSIVE_DROP
  echo
  wget $SUBVERSIVE_DROP_URL
  echo
fi
export SUBVERSIVE_CONN_DROP_URL=http://community.polarion.com/projects/subversive/download/eclipse/4.0/builds/Subversive-connectors-4.0.2.I20140528-1700.zip
export SUBVERSIVE_CONN_DROP=${SUBVERSIVE_CONN_DROP_URL##*/}
if [ ! -f $SUBVERSIVE_CONN_DROP ]; then
  echo
  echo Downloading Subversive Connectors $SUBVERSIVE_CONN_DROP
  echo
  wget $SUBVERSIVE_CONN_DROP_URL
  echo
fi
#
# Subversive
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
mkdir SireumDev 2> /dev/null
echo "path=../../sireumdev/subversive" > eclipse/dsl/links/subversive.link 
mkdir SireumDev/subversive 2> /dev/null
mkdir SireumDev/subversive/eclipse 2> /dev/null
cd SireumDev/subversive/eclipse
unzip -oq ../../../$SUBVERSIVE_CONN_DROP
unzip -oq ../../../$SUBVERSIVE_DROP
> .eclipseextension
cd ../..
zip -rq subversive.sapp subversive ../eclipse/dsl/links/subversive.link
cd ..
rm -fR SireumDev/subversive eclipse
echo
echo ...done!
