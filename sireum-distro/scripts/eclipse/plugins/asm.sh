#!/bin/bash
#
ELINKDIR=eclipse/jee/links
RELDIR=../..
export ASM_DROP_URL=http://download.forge.objectweb.org/asm/de.loskutov.BytecodeOutline.update_2.4.3.zip
export ASM_DROP=${ASM_DROP_URL##*/}
if [ ! -f $ASM_DROP ]; then
  echo
  echo Downloading Bytecode Outline $ASM_DROP
  echo
  wget $ASM_DROP_URL
  echo
fi
mkdir -p ${ELINKDIR}
echo "path=${RELDIR}/eclipsebase/asm" > ${ELINKDIR}/asm.link
mkdir -p EclipseBase/asm/eclipse
cd EclipseBase/asm/eclipse
> .eclipseextension
unzip -oq ../../../$ASM_DROP
cd ../..
zip -rq asm.sapp asm ../${ELINKDIR}/asm.link
cd ..
rm -fR eclipse EclipseBase/asm
echo
echo ...done!
