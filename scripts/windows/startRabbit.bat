@echo off
echo Checking for processes using port 5672...

for /f "tokens=5" %%a in ('netstat -ano ^| findstr :5672') do (
    set /a "pid=%%a"
    goto :killProcess
)

:killProcess
if not defined pid (
    echo No process is using port 5672.
) else (
    echo Process %pid% is using port 5672. Attempting to terminate...
    taskkill /F /PID %pid%
)

echo Starting RabbitMQ...
.\rabbitmq-service.bat start
pause