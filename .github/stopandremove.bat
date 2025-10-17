@echo off
echo === [Stopping old app on port 8090] ===

cd /d C:\root\hama
md dir1

rem Kill process using port 8090 (if any)
for /f "tokens=5" %%a in ('netstat -ano ^| find ":8090"') do (
    echo Killing PID %%a ...
    taskkill /F /PID %%a >nul 2>&1
)

echo Waiting for port to be released...
timeout /t 5 /nobreak >nul

echo === [Starting new app] ===
cd /d C:\root\hama\backend\target

for %%f in (*.jar) do (
    echo Starting %%f ...
    start "" cmd /c "java -jar \"%%f\" --server.port=8090"
)

cd /d C:\root\hama
md dir2

echo === [Done] ===
exit /b
