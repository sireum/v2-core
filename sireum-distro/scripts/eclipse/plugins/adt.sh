#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export ADT_DROP_URL=http://dl.google.com/android/ADT-23.0.2.zip
export ADT_DROP=${ADT_DROP_URL##*/}
if [ ! -f $ADT_DROP ]; then
  echo
  echo Downloading ADT $ADT_DROP
  echo
  wget $ADT_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../amandroiddev/adt" > eclipse/dsl/links/adt.link 
mkdir AmandroidDev 2> /dev/null
mkdir AmandroidDev/adt 2> /dev/null
mkdir AmandroidDev/adt/eclipse 2> /dev/null
cd AmandroidDev/adt/eclipse
> .eclipseextension
unzip -oq ../../../$ADT_DROP
cd ../..
zip -rq adt.sapp adt ../eclipse/dsl/links/adt.link
cd ..
rm -fR eclipse AmandroidDev/adt
echo
echo ...done!
