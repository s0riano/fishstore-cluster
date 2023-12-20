import logging
from fastapi.responses import JSONResponse
from fastapi import APIRouter, HTTPException
from typing import List, Dict
from datetime import datetime, timedelta
from app.services.user_data import get_user_data
from app.services.weather_service import fetch_weather_data
from typing import Optional

router = APIRouter()
logger = logging.getLogger(__name__)


@router.get("/weather/{userid}")
def get_weather(userid: int) -> List[Dict]:
    if userid not in [1, 2, 3]:
        raise HTTPException(status_code=400, detail="Invalid userID")
    user_data = get_user_data(userid)
    valid_user_data = []
    current_date = datetime.now().date()
    for entry in user_data:
        entry_date = datetime.strptime(entry['date'], '%Y-%m-%d').date()
        if current_date <= entry_date <= current_date + timedelta(days=2):
            valid_user_data.append(entry)
        else:
            logger.info(f"Shop is not open on {entry['date']} for user ID {userid}")

    if not valid_user_data:
        return JSONResponse(status_code=404, content=[{'error': 'No shop open dates within the valid range'}])

    weather_data = fetch_weather_data(valid_user_data)
    if not weather_data or 'error' in weather_data[0]:
        return JSONResponse(status_code=404, content=weather_data)

    return weather_data
