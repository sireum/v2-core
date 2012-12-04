::#!
@echo off
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
IF EXIST %SIREUM_HOME%\apps\platform\java (
  SET JAVA_HOME=%SIREUM_HOME%\apps\platform\java
  SET PATH=%JAVA_HOME%\bin:%PATH%
)
IF EXIST %SIREUM_HOME%\apps\platform\scala (
  SET SCALA_HOME=%SIREUM_HOME%\apps\platform\scala
  SET PATH=%SCALA_HOME%\bin:%PATH%
)
IF NOT DEFINED SCALA_BIN (
  SET SCALA_BIN=scala
)
CALL %SCALA_BIN% -target:jvm-1.7 -language:reflectiveCalls -nocompdaemon -savecompiled %SCALA_OPTIONS% %SCRIPT% %SIREUM_HOME% %*
SET CODE=%ERRORLEVEL%
IF EXIST %JAVA_HOME%.new (
  RD %JAVA_HOME% /S /Q
  MOVE %JAVA_HOME%.new %JAVA_HOME%
)
IF EXIST %SCALA_HOME%.new (
  RD %SCALA_HOME% /S /Q
  MOVE %SCALA_HOME%.new %SCALA_HOME%
)
IF EXIST %SCRIPT%.new (
  MOVE /Y %SCRIPT%.new %SCRIPT% > NUL
  ECHO Reloading Sireum...
  ECHO.
  %SCRIPT% update
)
EXIT /B %CODE%
::!#
SireumDistro.main(argv)
