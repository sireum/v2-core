#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
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
