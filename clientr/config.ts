import dotenv from 'dotenv';

// Load environment variables from .env file
dotenv.config();

//const env = process.env.NODE_ENV || 'development';  // Use NODE_ENV for environment indication

const API_URLS = {
    development: 'http://localhost:8000', // where the backend / spring consul gateway are currently serving
    production: 'https://your-production-api-url.com'
};

//export const API_BASE_URL = process.env.BACKEND_URL || API_URLS[env];

const BACKEND_URL = process.env.BACKEND_URL || 'http://localhost:8000';

export const API_BASE_URL = BACKEND_URL;