from fastapi import FastAPI, HTTPException
from typing import Optional
import logging
from routes.weather_routes import router


logging.basicConfig(
    level=logging.INFO,  # Set the logging level (e.g., INFO, DEBUG, ERROR)
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s",
    datefmt="%Y-%m-%d %H:%M:%S",
)
logger = logging.getLogger(__name__)

app = FastAPI()
app.include_router(router)