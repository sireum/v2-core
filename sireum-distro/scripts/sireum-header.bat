::#!
@echo off
IF NOT DEFINED SCALA_BIN (
  SET SCALA_BIN=scala
)
SET SIREUM_DIST=true
SET SIREUM_HOME=%~dp0
SET SCRIPT=%SIREUM_HOME%\%~nx0
SET FILE1=%SCRIPT%
SET FILE2=%SCRIPT%.jar
IF NOT EXIST %FILE2% ( 
  ECHO Please wait while Sireum is loading...
  GOTO END 
)
FOR /F %%i IN ('DIR /B /O:D %FILE1% %FILE2%') DO SET NEWEST=%%i
IF %NEWEST%==%~nx0 (
  ECHO Please wait while Sireum is loading... 
)
:END
CALL %SCALA_BIN% -target:jvm-1.7 -language:reflectiveCalls -nocompdaemon -savecompiled %SCALA_OPTIONS% %SCRIPT% %SIREUM_HOME% %*
IF ERRORLEVEL 8 (
  MOVE /Y %SCRIPT%.new %SCRIPT% > NUL
  ECHO Reloading Sireum...
  ECHO.
  %SCRIPT% update
)
GOTO :eof
::!#
SireumDistro.main(argv)
