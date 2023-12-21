#!/bin/bash

# Before running this script, ensure you update the following path to reflect the location
# where you have saved the FishStoreCluster folder on your system.
# This step is necessary to set the script as executable, which allows it to run.

# Replace '/Users/alexanderaarseth/FishStoreCluster/scripts/start_microservices.sh'
# with the correct path to 'start_microservices.sh' on your system.
# After setting the correct path, run this command in the terminal:

# chmod +x /Users/alexanderaarseth/FishStoreCluster/scripts/start_microservices.sh

# Navigate to the directory and write this command in your terminal to start the services without #
# ./start_microservices.sh


echo "Starting FishStoreCluster microservices..."


ROOT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")"/.. && pwd)"

start_service() {
    echo "Starting $1..."
    cd "$ROOT_DIR/$1" || exit
    mvn spring-boot:run &
}

#start_service "boat"
#start_service "buyer-management"
#start_service "clientr"
#start_service "fishstore-service"
start_service "gateway"
start_service "inventory"
#start_service "price"
#start_service "seller-management"
start_service "shop"
#start_service "staticinventory"
#start_service "transaction"
#start_service "uuid"
#start_service "vipps-payment-service"
#start_service "weather"

wait
echo "All FishStoreCluster services, not marked with #, have been started."


# lsof -i :8082
#kill [-9 (optional, usually if kill itself doesnt work)] 'PID'
