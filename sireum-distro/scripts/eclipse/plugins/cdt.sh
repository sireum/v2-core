#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export CDT_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/cdt/releases/8.4/cdt-8.4.0.zip
export CDT_DROP=${CDT_DROP_URL##*/}
if [ ! -f $CDT_DROP ]; then
  echo
  echo Downloading CDT $CDT_DROP
  echo
  wget $CDT_DROP_URL
  echo
fi
#
# CDT
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../compilerdev/team/cdt" > eclipse/dsl/links/cdt.link 
mkdir CompilerDev 2> /dev/null
mkdir CompilerDev/team 2> /dev/null
mkdir CompilerDev/team/cdt 2> /dev/null
mkdir CompilerDev/team/cdt/eclipse 2> /dev/null
cd CompilerDev/team/cdt/eclipse
> .eclipseextension
unzip -oq ../../../../$CDT_DROP
cd ../../..
zip -rq team.sapp team ../eclipse/dsl/links/cdt.link
cd ..
rm -fR eclipse CompilerDev/team
echo
echo ...done!
