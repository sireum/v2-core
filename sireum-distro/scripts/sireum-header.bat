::#!
@echo off
SETLOCAL
SET SIREUM_DIST=true
SET SIREUM_HOME=%~dp0
SET SCRIPT=%SIREUM_HOME%%~nx0
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
IF EXIST %SIREUM_HOME%apps\platform\java (
  SET JAVA_HOME=%SIREUM_HOME%apps\platform\java
  SET PATH=%SIREUM_HOME%apps\platform\java\bin;%PATH%
)
IF EXIST %SIREUM_HOME%apps\platform\scala (
  SET SCALA_HOME=%SIREUM_HOME%apps\platform\scala
  SET PATH=%SIREUM_HOME%apps\platform\scala\bin;%PATH%
)
IF NOT DEFINED SCALA_BIN (
  SET SCALA_BIN=scala
)
CALL %SCALA_BIN% -target:jvm-1.7 -nocompdaemon -savecompiled %SCALA_OPTIONS% %SCRIPT% %SIREUM_HOME% %*
SET CODE=%ERRORLEVEL%
IF EXIST %SIREUM_HOME%apps\platform\java.new (
  RD %SIREUM_HOME%apps\platform\java /S /Q
  MOVE /Y %SIREUM_HOME%apps\platform\java.new %SIREUM_HOME%apps\platform\java > NUL
  DEL /Q %SCRIPT%.jar
  SET RELOAD=true
)
IF EXIST %SIREUM_HOME%apps\platform\scala.new (
  RD %SIREUM_HOME%apps\platform\scala /S /Q
  MOVE /Y %SIREUM_HOME%apps\platform\scala.new %SIREUM_HOME%apps\platform\scala > NUL
  DEL /Q %SCRIPT%.jar
  SET RELOAD=true
)
IF EXIST %SCRIPT%.new (
  MOVE /Y %SCRIPT%.new %SCRIPT% > NUL
  SET RELOAD=true
)
IF %RELOAD% == "true" (
  SET RELOAD=false
  ECHO Reloading Sireum...
  ECHO.
  %SCRIPT% %*
)
ENDLOCAL
EXIT /B %CODE%
::!#
SireumDistro.main(argv)