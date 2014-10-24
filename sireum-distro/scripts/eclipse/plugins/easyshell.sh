#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export EASY_SHELL_DROP_URL=http://downloads.sourceforge.net/project/pluginbox/easyshell/Release-1.4.2/Easy_Shell_1.4.2.zip
export EASY_SHELL_DROP=${EASY_SHELL_DROP_URL##*/}
export EASY_SHELL=${EASY_SHELL_DROP%.*}
if [ ! -f $EASY_SHELL_DROP ]; then
  echo
  echo Downloading EASY_SHELL $EASY_SHELL_DROP
  echo
  wget $EASY_SHELL_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/easyshell" > eclipse/dsl/links/easyshell.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/easyshell 2> /dev/null
cd EclipseBase/easyshell
unzip -oq ../../$EASY_SHELL_DROP
mv $EASY_SHELL/eclipse .
rm -R $EASY_SHELL
> eclipse/.eclipseextension
cd ..
zip -rq easyshell.sapp easyshell ../eclipse/dsl/links/easyshell.link
cd ..
rm -fR eclipse EclipseBase/easyshell
echo
echo ...done!
