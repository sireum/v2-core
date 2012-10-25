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
CALL %SCALA_BIN% -nocompdaemon -savecompiled %SCALA_OPTIONS% %SCRIPT% %SIREUM_HOME% %*
GOTO :eof
::!#
SireumDistro.main(argv)
