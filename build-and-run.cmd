call mvn clean package >> build.log 2>&1
if %ERRORLEVEL% neq 0 (
    echo Build failed!
    exit /b 1
)
java -jar target/appointments-app-1.0-SNAPSHOT.jar
pause











