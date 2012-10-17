#!/bin/sh
export SIREUM_HOME=`dirname $(cd "${0%/*}/../../../" 2>/dev/null; echo "$PWD"/"${0##*/}")`
$SIREUM_HOME/apps/eclipse/classic/eclipse $*
