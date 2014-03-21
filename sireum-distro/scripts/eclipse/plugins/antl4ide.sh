#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export NAME=antlr4ide
export VERSION=4.3
git clone https://github.com/jknack/$NAME.git
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../antlr/$NAME" > eclipse/dsl/links/$NAME.link 
mkdir Antlr 2> /dev/null
mkdir Antlr/$NAME 2> /dev/null
mv $NAME/updates/release/4.3 Antlr/$NAME/eclipse  
cd Antlr/$NAME/eclipse
> .eclipseextension
cd ../..
zip -rq $NAME.sapp $NAME ../eclipse/dsl/links/$NAME.link
cd ..
rm -fR eclipse Antlr/$NAME
echo
echo ...done!
