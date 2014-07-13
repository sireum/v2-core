#!/bin/bash
#
export ASM_DROP_URL=http://download.forge.objectweb.org/asm/de.loskutov.BytecodeOutline.update_2.4.3.zip
export ASM_DROP=${ASM_DROP_URL##*/}
if [ ! -f $ASM_DROP ]; then
  echo
  echo Downloading Bytecode Outline $ASM_DROP
  echo
  wget $ASM_DROP_URL
  echo
fi
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../eclipsebase/asm" > eclipse/dsl/links/asm.link 
mkdir EclipseBase 2> /dev/null
mkdir EclipseBase/asm 2> /dev/null
mkdir EclipseBase/asm/eclipse 2> /dev/null
cd EclipseBase/asm/eclipse
> .eclipseextension
unzip -oq ../../../$ASM_DROP
cd ../..
zip -rq asm.sapp asm ../eclipse/dsl/links/asm.link
cd ..
rm -fR eclipse EclipseBase/asm
echo
echo ...done!
