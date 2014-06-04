#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export GEF_DROP_URL=http://ftp.osuosl.org/pub/eclipse/tools/gef/downloads/drops/3.9.100/S201405261516/GEF-SDK-3.9.100RC2.zip
export GEF_DROP=${GEF_DROP_URL##*/}
if [ ! -f $GEF_DROP ]; then
  echo
  echo Downloading GEF SDK $GEF_DROP
  echo
  wget $GEF_DROP_URL
  echo
fi
export ASM_DROP_URL=http://download.forge.objectweb.org/asm/de.loskutov.BytecodeOutline.update_2.4.2.zip
export ASM_DROP=${ASM_DROP_URL##*/}
if [ ! -f $ASM_DROP ]; then
  echo
  echo Downloading Bytecode Outline $ASM_DROP
  echo
  wget $ASM_DROP_URL
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
#mkdir eclipse-plugins-essential/elt 2> /dev/null
#mkdir eclipse-plugins-essential/elt/eclipse 2> /dev/null
#cd eclipse-plugins-essential/elt/
#unzip -oq ../../$ELT_DROP
#mv elt-$ELT_COMMIT_SHORT/update-site elt-$ELT_COMMIT_SHORT/eclipse
#mv elt-$ELT_COMMIT_SHORT/eclipse .
#rm -fR elt-$ELT_COMMIT_SHORT
#cd eclipse
#> .eclipseextension
#cd ../../..
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
