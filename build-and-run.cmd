@echo off
REM Build and Run Appointments App
REM This script compiles the project and runs the application

echo Building the project...
call mvn clean package >> build.log 2>&1

if %ERRORLEVEL% neq 0 (
    echo Build failed!
    exit /b 1
)

echo.
echo Build successful! Running the application...
echo.

REM Run the jar file
java -jar target/appointments-app-1.0-SNAPSHOT.jar

pause











