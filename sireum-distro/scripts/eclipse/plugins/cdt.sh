#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export CDT_DROP_URL=${CDT_URL:=http://ftp.osuosl.org/pub/eclipse/tools/cdt/releases/}${CDT_RELEASE:=kepler/sr1}/cdt-master-${CDT_VERSION:=8.2.1}.zip
export CDT_DROP=cdt-master-$CDT_VERSION.zip
if [ ! -f $CDT_DROP ]; then
  echo
  echo Downloading CDT $CDT_VERSION
  echo
  wget $CDT_DROP_URL
  echo
fi
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
echo
echo ...done!
