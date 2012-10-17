@echo off
SET SIREUM_HOME=%~dp0\..\..\..
pushd .
cd %SIREUM_HOME%
set SIREUM_HOME=%CD%
popd
start %SIREUM_HOME%\apps\eclipse\classic\eclipse.exe %*