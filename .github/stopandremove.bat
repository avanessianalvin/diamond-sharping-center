@echo off
echo === [Stopping old app on port 8090] ===

cd C:\root\hama
md dir1

rem Kill process using port 8090 (if any)
for /f "tokens=5" %%a in ('netstat -ano ^| find ":8090"') do (
    echo Killing PID %%a ...
    taskkill /F /PID %%a >nul 2>&1
)

timeout /t 5 >nul

echo === [Starting new app] ===
cd C:\root\hama\backend\target
rem Run the only JAR file in folder
for %%f in (*.jar) do (
    echo Starting %%f ...
    start "" java -jar "%%f" --server.port=8090
)
cd C:\root\hama
md dir2

echo === [Done] ===
exit /b