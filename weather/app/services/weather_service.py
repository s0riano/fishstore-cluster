import requests
import json
import logging
from datetime import datetime

with open('config.json', 'r') as config_file:
    config = json.load(config_file)

logger = logging.getLogger(__name__)


url = WEATHER_API_URL = config['WEATHER_API_URL']
headers = {'User-Agent': 'MyWeatherApp/1.0'}


def fetch_weather_data(user_data):
    logger.info("Starting to fetch weather data")
    forecasts = []

    for data_entry in user_data:
        try:
            params = {"lat": data_entry['latitude'], "lon": data_entry['longitude']}
            response = requests.get(url, headers=headers, params=params)
            response.raise_for_status()
            data = response.json()
            all_forecasts = data['properties']['timeseries']

            # Convert user date and time to datetime object
            user_datetime = datetime.strptime(
                f"{data_entry['date']} {data_entry['openTime']}",
                '%Y-%m-%d %H:%M'
            )

            for entry in all_forecasts:
                entry_datetime = datetime.fromisoformat(entry['time'])

                # Compare dates only, ignoring time
                if entry_datetime.date() == user_datetime.date():
                    temp = entry['data']['instant']['details']['air_temperature']
                    forecasts.append({
                        'date': entry_datetime.strftime('%Y-%m-%d'),
                        'openTime': data_entry['openTime'],
                        'closeTime': data_entry['closeTime'],
                        'temperature': temp
                    })
                    break  # Assuming you want the first match of the day

        except Exception as e:
            logger.error(f"Error fetching weather data for {data_entry}: {e}")
            forecasts.append({'error': f'Error for entry {data_entry}: {e}'})

    return forecasts if forecasts else [{'error': 'No weather data available for open dates'}]

