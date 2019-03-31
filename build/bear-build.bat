@echo off 
rem ENABLEDELAYEDEXPANSION
rem ---------------------------------------------------------------------------
rem 
rem  @author panda.
rem  @since 2019-03-27 23:51.
rem 
rem ---------------------------------------------------------------------------

title build-bear package

echo if build can not work ,please set JAVA_HOME and  MAVEN_HOME...

rem ---------------------------------------------------------------------------
rem set  JAVA_HOME and  MAVEN_HOME like this ,eg:
rem
rem set JAVA_HOME=D:\Program Files\jdk1.8.0_172
rem set MAVEN_HOME=E:\apache-maven-3.3.9
rem set CLASSPATH=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
rem set Path=%JAVA_HOME%\bin;%JAVA_HOME%\jar\bin;%MAVEN_HOME%\bin
rem
rem ---------------------------------------------------------------------------


rem set JAVA_HOME=D:\Program Files\jdk1.8.0_172
rem set MAVEN_HOME=E:\apache-maven-3.3.9



if not "%JAVA_HOME%" == "" goto javaHomeEnd
echo please set JAVA_HOME...
goto end
:javaHomeEnd
echo Using JAVA_HOME:   "%JAVA_HOME%"


if not "%MAVEN_HOME%" == "" goto mavenHomeEnd
echo please set MAVEN_HOME...
goto end
:mavenHomeEnd
echo Using MAVEN_HOME:   "%MAVEN_HOME%"


if not "%PATH%" == "" goto pathEnd
echo please set MAVEN_HOME...
set PATH=%JAVA_HOME%\bin;%JAVA_HOME%\jar\bin;%MAVEN_HOME%\bin;C:\Windows\System32
goto end
:pathEnd
echo Using PATH:   "%PATH%"

set CLASSPATH=.;%JAVA_HOME%\lib\dt.jar;%JAVA_HOME%\lib\tools.jar
rem set PATH=%JAVA_HOME%\bin;%JAVA_HOME%\jar\bin;%MAVEN_HOME%\bin

echo Using CLASSPATH:   "%CLASSPATH%"

echo= 
echo, 
echo;
echo ===  This build environment is OK. Let's go. ===
echo= 
echo, 
echo;

color 02

echo === Start building bears... ===

cd ..\

rem call mvn clean install 使用下面语句替换
rem  call mvn package -Dmaven.test.skip=true 

echo === End build bears...=== 
echo= 
echo, 
echo;

rem ---------------------------------------------------------------------------
rem  Copy  file 
rem  echo 当前盘符：%~d0
rem  echo 当前盘符和路径：%~dp0
rem  echo 当前盘符和路径的短文件名格式：%~sdp0
rem  echo 当前批处理全路径：%~f0
rem  echo 当前CMD默认目录：%cd%
rem ---------------------------------------------------------------------------

echo === Start copy file to targer ...=== 

cd %~dp0 

rem 开始进行文件目录拷贝
echo del Last build jar, and Copy jar ...
set rootFloder=%~dp0bears\
echo %rootFloder%


del/s /Q %~dp0bears\
rd /S /Q %~dp0bears\


rem 延迟2秒，确保上一句语句执行完后再往下走
ping -n 2 127.1 >nul

rem 创建根目录
md "%~dp0bears"
rem 创建根目录下的WEB目录
ping -n 1 127.1 >nul
md "%~dp0bears\web"

rem 创建根目录下的SERVER目录
ping -n 1 127.1 >nul
md "%~dp0bears\server"

cd %~dp0

rem 从属性文件中读取配置信息-jar服务列表
for /f "delims== tokens=1,*" %%a in ('type bear-build.properties ^|findstr /i "bear.server"') do  set modules="%%b"
rem 从属性文件中读取配置信息-web应用列表
for /f "delims== tokens=1,*" %%a in ('type fms-build.properties ^|findstr /i "bear.web"') do  set modulesweb="%%b"

rem 拷贝的模组，配置在构建属性文件下(bear-build.properties)
echo copy modules:  %modules%

cd ../
set suffix=
rem set suffix=-assembly

rem 启用变量延迟
SETLOCAL ENABLEDELAYEDEXPANSION

:GOON
for /f "delims=, tokens=1,*" %%i in (%modules%) do (

	   echo %%i
	   set folderName=%%i%suffix%

	   XCOPY "%cd%\%%i\target\!folderName!\*"  "%~dp0bears\server" /S /E /D
	   
	   rem copy %~dp0bears\server\%%i\lib\*.jar  %~dp0bears\server\lib\
	   rem del /q %~dp0bears\server\%%i\lib\*.jar
	   
	   copy "%cd%\%%i\target\%%i.jar" %~dp0bears\server\%%i\
	   ping -n 1 127.1 >nul
       set modules="%%j"	  
	   
     goto GOON
)

rem 拷贝war到web目录
rem copy  %cd%\bear-system-admin\target\*.war  %~dp0bears\web\
   
  
echo Build succeed,Press Space Key to Exit...
:end
pause