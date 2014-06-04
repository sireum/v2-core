#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../sireumdev/eclipse-fonts" > eclipse/dsl/links/eclipse-fonts.link 
mkdir SireumDev 2> /dev/null
mkdir SireumDev/eclipse-fonts 2> /dev/null
cd SireumDev/eclipse-fonts
svn checkout https://eclipse-fonts.googlecode.com/svn/trunk/FontsUpdate eclipse
cd eclipse
> .eclipseextension
rm -fR `find . -name ".svn"`
rm .project
cd ../..
zip -rq eclipse-fonts.sapp eclipse-fonts ../eclipse/dsl/links/eclipse-fonts.link
cd ..
rm -fR SireumDev/eclipse-fonts eclipse
echo
echo ...done!
