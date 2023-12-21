@echo off

REM Delete the existing virtual environment
IF EXIST "venv" (
    echo Removing existing virtual environment...
    rmdir /s /q venv
)

REM Create a new virtual environment
echo Creating a new virtual environment...
python -m venv venv
echo.

REM Activate the virtual environment
echo Activating virtual environment...
call venv\Scripts\activate

REM Install required packages
echo Installing required packages...
pip install fastapi uvicorn

REM Check for requirements.txt and install any additional dependencies
IF EXIST "requirements.txt" (
    echo Installing additional dependencies from requirements.txt...
    pip install -r requirements.txt
) ELSE (
    echo No requirements.txt found. Skipping additional dependencies.
)

echo Setup is complete. Your environment is now fresh and ready for development.
echo To activate the virtual environment manually, use 'venv\Scripts\activate'.
