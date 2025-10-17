@echo off
echo === [Stopping old app on port 8090] ===


rem Kill process using port 8090 (if any)
for /f "tokens=5" %%a in ('netstat -ano ^| find ":8090"') do (
    echo Killing PID %%a ...
    taskkill /F /PID %%a >nul 2>&1
)

echo Waiting for port to be released...
timeout /t 5 /nobreak >nul

echo === [Done] ===
exit /b
