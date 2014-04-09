@echo off
SETLOCAL
SET SIREUM_HOME=%~dp0\..\..\..
pushd .
cd %SIREUM_HOME%
set SIREUM_HOME=%CD%
popd
%SIREUM_HOME%\sireum launch osate --args %*
ENDLOCAL
