#!/bin/bash

# Check if Python 3 is installed
if ! command -v python3 &> /dev/null
then
    echo "Python 3 could not be found. Please install it first."
    exit
fi

# Create a virtual environment
echo "Creating virtual environment..."
python3 -m venv venv
source venv/bin/activate

# Install FastAPI and Uvicorn
echo "Installing FastAPI, Uvicorn..."
pip install fastapi uvicorn

# Check for requirements.txt and install additional dependencies
if [ -f "requirements.txt" ]; then
    echo "Installing additional dependencies from requirements.txt..."
    pip install -r requirements.txt
else
    echo "No requirements.txt found. Skipping additional dependencies."
fi

echo "Setup complete. Virtual environment created and dependencies installed."
echo "To activate the virtual environment, run 'source venv/bin/activate' on Unix or 'venv\Scripts\activate' on Windows."
