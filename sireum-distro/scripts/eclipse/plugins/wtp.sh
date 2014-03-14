#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export WTP_DROP_URL=${WTP_URL:=http://ftp.osuosl.org/pub/eclipse/webtools/downloads/drops/}R${WTP_VERSION:=3.5.2}/${WTP_RELEASE:=R-$WTP_VERSION-20140217150812}/${WTP_DROP:=wtp4x-$WTP_RELEASE.zip}
if [ ! -f $WTP_DROP ]; then
  echo
  echo Downloading WTP $WTP_VERSION
  echo
  wget $WTP_DROP_URL
  echo
fi
#
# WTP
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../webdev/wtp" > eclipse/dsl/links/wtp.link 
mkdir WebDev 2> /dev/null
mkdir WebDev/wtp 2> /dev/null
mkdir WebDev/wtp/eclipse 2> /dev/null
cd WebDev/wtp/eclipse
> .eclipseextension
cd ..
unzip -oq ../../$WTP_DROP
cd ..
zip -rq wtp.sapp wtp ../eclipse/dsl/links/wtp.link
cd ..
rm -fR eclipse WebDev/wtp
echo
echo ...done!
