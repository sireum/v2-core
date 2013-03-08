#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export GEF_DROP_URL=${GEF_MIRROR_URL:=http://ftp.osuosl.org/pub/eclipse/tools/gef/downloads/drops/}${GEF_VERSION:=3.8.2}/${GEF_REL:=R}${GEF_BUILD_DATE:=201301141834}/GEF-ALL-$GEF_VERSION.zip
export GEF_DROP=GEF-ALL-$GEF_VERSION.zip
if [ ! -f $GEF_DROP ]; then
  echo
  echo Downloading GEF SDK $GEF_VERSION
  echo
  wget $GEF_DROP_URL
  echo
fi
export EGIT_DROP_URL=${EGIT_URL:=http://download.eclipse.org/egit/updates-}${EGIT_VERSION:=2.3}/org.eclipse.egit.repository-$EGIT_VERSION.${EGIT_VERSION_M:=1}.${EGIT_BUILD_DATE:=201302201838}-r.zip
export EGIT_DROP=org.eclipse.egit.repository-$EGIT_VERSION.$EGIT_VERSION_M.$EGIT_BUILD_DATE-r.zip
if [ ! -f $EGIT_DROP ]; then
  echo
  echo Downloading EGIT $EGIT_VERSION.$EGIT_VERSION_M.$EGIT_BUILD_DATE
  echo
  wget $EGIT_DROP_URL
  echo
fi
export ASM_DROP_URL=${ASM_URL:=http://download.forge.objectweb.org/asm/de.loskutov.BytecodeOutline.update_}${ASM_VERSION:=2.4.0}.zip
export ASM_DROP=de.loskutov.BytecodeOutline.update_$ASM_VERSION.zip
if [ ! -f $ASM_DROP ]; then
  echo
  echo Downloading Bytecode Outline $ASM_VERSION
  echo
  wget $ASM_DROP_URL
  echo
fi
export SUBCLIPSE_DROP_URL=${SUBCLIPSE_URL:=http://subclipse.tigris.org/files/documents/906/49260/site-}${SUBCLIPSE_VERSION:=1.8.18}.zip
export SUBCLIPSE_DROP=site-$SUBCLIPSE_VERSION.zip
if [ ! -f $SUBCLIPSE_DROP ]; then
  echo
  echo Downloading Subclipse $SUBCLIPSE_VERSION
  echo
  wget $SUBCLIPSE_DROP_URL
  echo
fi
export ELT_DROP_URL=${ELT_URL:=http://elt.googlecode.com/files/elt-}${ELT_VERSION:=1.1.0}.${ELT_BUILD_DATE:=201208062130}.zip
export ELT_DROP=elt-$ELT_VERSION.$ELT_BUILD_DATE.zip
if [ ! -f $ELT_DROP ]; then
  echo
  echo Downloading ELT $ELT_VERSION
  echo
  wget $ELT_DROP_URL
  echo
fi
export TEXLIPSE_DROP_URL=${TEXLIPSE_URL:=http://downloads.sourceforge.net/project/texlipse/texlipse%20plugin/}${TEXLIPSE_VERSION:=1.5.0}/texlipse_$TEXLIPSE_VERSION%20src.zip
export TEXLIPSE_DROP="texlipse_$TEXLIPSE_VERSION src.zip"
if [ ! -f "$TEXLIPSE_DROP" ]; then
  echo
  echo Downloading Texlipse $TEXLIPSE_VERSION
  echo
  wget $TEXLIPSE_DROP_URL
  echo
fi
mkdir eclipse-plugins-essential 2> /dev/null
mkdir eclipse-plugins-essential/gef 2> /dev/null
cd eclipse-plugins-essential/gef
unzip -oq ../../$GEF_DROP
> eclipse/.eclipseextension
cd ../..
mkdir eclipse-plugins-essential/egit 2> /dev/null
mkdir eclipse-plugins-essential/egit/eclipse 2> /dev/null
cd eclipse-plugins-essential/egit/eclipse
unzip -oq ../../../$EGIT_DROP
> .eclipseextension
cd ../../..
mkdir eclipse-plugins-essential/asm 2> /dev/null
mkdir eclipse-plugins-essential/asm/eclipse 2> /dev/null
cd eclipse-plugins-essential/asm/eclipse
unzip -oq ../../../$ASM_DROP
> .eclipseextension
cd ../../..
mkdir eclipse-plugins-essential/subclipse 2> /dev/null
mkdir eclipse-plugins-essential/subclipse/eclipse 2> /dev/null
cd eclipse-plugins-essential/subclipse/eclipse
unzip -oq ../../../$SUBCLIPSE_DROP
> .eclipseextension
cd ../../..
mkdir eclipse-plugins-essential/elt 2> /dev/null
mkdir eclipse-plugins-essential/elt/eclipse 2> /dev/null
cd eclipse-plugins-essential/elt/eclipse
unzip -oq ../../../$ELT_DROP
> .eclipseextension
cd ../../..
mkdir eclipse-plugins-essential/texlipse 2> /dev/null
mkdir eclipse-plugins-essential/texlipse/eclipse 2> /dev/null
cd eclipse-plugins-essential/texlipse
aspell --encoding=UTF-8 --lang=en dump master > en.dict
cd eclipse
unzip -oq ../../../"$TEXLIPSE_DROP"
> .eclipseextension
cd ../../..
zip -rq eclipse-plugins-essential.sapp eclipse-plugins-essential
echo
echo ...done!
