@echo off
@
@rem This will start a cache server
@
SET JAVA_HOME=C:\oracle\jdk1.8.0_121

setlocal

:config
@rem specify the Coherence installation directory
set coherence_home=%~dp0\..

@rem specify the JVM heap size
set memory=512m


:start
if not exist "%coherence_home%\lib\coherence.jar" goto instructions

if "%java_home%"=="" (set java_exec=java) else (set java_exec=%java_home%\bin\java)


:launch

set java_opts="-Xms%memory% -Xmx%memory% -Dtangosol.coherence.cacheconfig=C:\oracle\coherence\bin\jpa-cache-config-web.xml"

"%java_exec%" -server -showversion "%java_opts%" -cp "%coherence_home%\lib\coherence.jar;%coherence_home%\lib\coherence-jpa.jar;C:\oracle\jdevstudio122100\jdbc\lib\ojdbc14.jar;C:\oracle\jdevstudio122100\toplink\jlib\toplink-essentials.jar;C:\projecs\workspace\12.2.1.0.0\coherence\web\public_html\WEB-INF\classes" com.tangosol.net.DefaultCacheServer %1

goto exit

:instructions

echo Usage:
echo   ^<coherence_home^>\bin\cache-server.cmd
goto exit

:exit
endlocal
@echo on