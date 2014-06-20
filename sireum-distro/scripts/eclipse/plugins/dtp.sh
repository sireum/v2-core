#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export DTP_DROP_URL=http://ftp.osuosl.org/pub/eclipse/datatools/downloads/1.12/dtp_1.12.0.zip
export DTP_DROP=${DTP_DROP_URL##*/}
if [ ! -f $DTP_DROP ]; then
  echo
  echo Downloading DTP $DTP_DROP
  echo
  wget $DTP_DROP_URL
  echo
fi
#
# DTP
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../webdev/dtp" > eclipse/dsl/links/dtp.link 
mkdir WebDev 2> /dev/null
mkdir WebDev/dtp 2> /dev/null
mkdir WebDev/dtp/eclipse 2> /dev/null
cd WebDev/dtp/eclipse
> .eclipseextension
cd ..
unzip -oq ../../$DTP_DROP
cd ..
zip -rq dtp.sapp dtp ../eclipse/dsl/links/dtp.link
cd ..
rm -fR eclipse WebDev/dtp
echo
echo ...done!
