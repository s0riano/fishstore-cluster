import json
import uvicorn

# Load config
with open('config.json', 'r') as config_file:
    config = json.load(config_file)

host = config.get('FASTAPI_HOST', 'localhost')
port = config.get('FASTAPI_PORT', 8000)

if __name__ == "__main__":
    uvicorn.run("app.main:app", host=host, port=port)


# uvicorn app.main:app --reload