#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export GEF_DROP_URL=${GEF_MIRROR_URL:=http://ftp.osuosl.org/pub/eclipse/tools/gef/downloads/drops/}${GEF_VERSION:=3.9.1}/${GEF_REL:=R}${GEF_BUILD_DATE:=201308190730}/GEF-ALL-$GEF_VERSION.zip
export GEF_DROP=GEF-ALL-$GEF_VERSION.zip
if [ ! -f $GEF_DROP ]; then
  echo
  echo Downloading GEF SDK $GEF_VERSION
  echo
  wget $GEF_DROP_URL
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
export SUBCLIPSE_DROP_URL=${SUBCLIPSE_URL:=http://subclipse.tigris.org/files/documents/906/49378/site-}${SUBCLIPSE_VERSION:=1.10.4}.zip
export SUBCLIPSE_DROP=site-$SUBCLIPSE_VERSION.zip
if [ ! -f $SUBCLIPSE_DROP ]; then
  echo
  echo Downloading Subclipse $SUBCLIPSE_VERSION
  echo
  wget $SUBCLIPSE_DROP_URL
  echo
fi
export ELT_DROP_URL=http://elt.googlecode.com/archive/${ELT_COMMIT_SHORT:=c4c170f31632}${ELT_COMMIT:=2d0d6040411b4170a4c696ff8c2b}.zip
export ELT_DROP=$ELT_COMMIT_SHORT$ELT_COMMIT.zip
if [ ! -f $ELT_DROP ]; then
  echo
  echo Downloading ELT $ELT_COMMIT_SHORT
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
cd eclipse-plugins-essential/elt/
unzip -oq ../../$ELT_DROP
mv elt-$ELT_COMMIT_SHORT/update-site elt-$ELT_COMMIT_SHORT/eclipse
mv elt-$ELT_COMMIT_SHORT/eclipse .
rm -fR elt-$ELT_COMMIT_SHORT
cd eclipse
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
