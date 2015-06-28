#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/eclipse-fonts" > ${ELINKDIR}/eclipse-fonts.link
mkdir -p EclipseBase/eclipse-fonts
cd EclipseBase/eclipse-fonts
svn checkout https://eclipse-fonts.googlecode.com/svn/trunk/FontsUpdate eclipse
cd eclipse
> .eclipseextension
rm -fR `find . -name ".svn"`
rm .project
cd ../..
zip -rq eclipse-fonts.sapp eclipse-fonts ../${ELINKDIR}/eclipse-fonts.link
cd ..
rm -fR EclipseBase/eclipse-fonts eclipse
echo
echo ...done!
