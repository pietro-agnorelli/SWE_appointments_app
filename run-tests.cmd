@echo off
echo Running tests...
call mvn clean test
if errorlevel 1 (
  echo Tests failed!
  pause
  exit /b 1
)
echo.
echo Tests completed. Results in target\surefire-reports\
pause
