@echo off
echo === [Stopping old app on port 8090] ===

rem Kill process using port 8090 (if any)
for /f "tokens=5" %%a in ('netstat -ano ^| find ":8090"') do (
    echo Killing PID %%a ...
    taskkill /F /PID %%a >nul 2>&1
)

echo === [Starting new app] ===
cd backend\target
rem Run the only JAR file in folder
for %%f in (*.jar) do (
    echo Starting %%f ...
    start "" java -jar "%%f" --server.port=8090
)

echo === [Done] ===
exit /b