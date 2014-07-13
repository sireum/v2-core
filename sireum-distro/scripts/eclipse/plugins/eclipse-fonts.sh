#!/bin/bash
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/eclipse-fonts" > eclipse/dsl/links/eclipse-fonts.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/eclipse-fonts 2> /dev/null
cd EclipseBase/eclipse-fonts
svn checkout https://eclipse-fonts.googlecode.com/svn/trunk/FontsUpdate eclipse
cd eclipse
> .eclipseextension
rm -fR `find . -name ".svn"`
rm .project
cd ../..
zip -rq eclipse-fonts.sapp eclipse-fonts ../eclipse/dsl/links/eclipse-fonts.link
cd ..
rm -fR EclipseBase/eclipse-fonts eclipse
echo
echo ...done!
