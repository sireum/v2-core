#!/bin/bash
# This script assumes that JDK files are present in the working directory, and
# Sireum is reachable from the PATH environment variable.
#
export PACKAGE_HOME=$( cd "$( dirname "$0" )" &> /dev/null && pwd )
export SCALA_DROP_URL=${SCALA_URL:=http://scala-lang.org/files/archive/scala-}${SCALA_VERSION:=2.10.3}.tgz
export SCALA_DROP=scala-$SCALA_VERSION.tgz
if [ ! -f $SCALA_DROP ]; then
  echo
  echo Downloading Scala $SCALA_VERSION
  echo
  wget $SCALA_DROP_URL
  echo
fi
export SCALA_DOCS_DROP_URL=${SCALA_DOCS_URL:=http://scala-lang.org/files/archive/scala-docs-}$SCALA_VERSION.zip
export SCALA_DOCS_DROP=scala-docs-$SCALA_VERSION.zip
if [ ! -f $SCALA_DOCS_DROP ]; then
  echo
  echo Downloading Scala Docs $SCALA_VERSION
  echo
  wget $SCALA_DOCS_DROP_URL
  echo
fi
tar xfz $SCALA_DROP
mv scala-$SCALA_VERSION scala.new
sireum tools sapper scala.sapp scala.new
rm -fR scala.new
unzip -oq $SCALA_DOCS_DROP
mv scala-docs-$SCALA_VERSION scala-docs
zip -rq scala-docs.sapp scala-docs
rm -fR scala-docs
echo
echo ...done!
