@echo off
echo Checking for processes using port 8301...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8301') do (
    set /a "pid=%%a"
    goto :killProcess
)

:killProcess
if not defined pid (
    echo No process is using port 8301.
) else (
    echo Process %pid% is using port 8301. Attempting to terminate...
    taskkill /F /PID %pid%
)

echo Starting Consul...
start consul agent -dev

