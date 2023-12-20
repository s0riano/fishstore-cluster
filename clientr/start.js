const { exec } = require('child_process');

console.log('Your React app is running at: http://localhost:3000');

exec('parcel serve index.html --port 3000');


/* Production File */