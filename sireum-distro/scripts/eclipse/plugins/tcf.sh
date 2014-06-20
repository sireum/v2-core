#!/bin/bash
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export TCF_DROP_URL=https://hudson.eclipse.org/tcf/job/tcf-master-4.4/lastSuccessfulBuild/artifact/features/org.eclipse.tcf.repo/target/org.eclipse.tcf.repo.zip
export TCF_DROP=${TCF_DROP_URL##*/}
if [ ! -f $TCF_DROP ]; then
  echo
  echo Downloading TCF $TCF_DROP
  echo
  wget $TCF_DROP_URL
  echo
fi
#
# TCF
#
mkdir eclipse 2> /dev/null
mkdir eclipse/dsl 2> /dev/null
mkdir eclipse/dsl/links 2> /dev/null
echo "path=../../sireumdev/tcf" > eclipse/dsl/links/tcf.link 
mkdir SireumDev 2> /dev/null
mkdir SireumDev/tcf 2> /dev/null
mkdir SireumDev/tcf/eclipse 2> /dev/null
cd SireumDev/tcf/eclipse
> .eclipseextension
unzip -oq ../../../$TCF_DROP
cd ../..
zip -rq tcf.sapp tcf ../eclipse/dsl/links/tcf.link
cd ..
rm -fR eclipse SireumDev/tcf
echo
echo ...done!
