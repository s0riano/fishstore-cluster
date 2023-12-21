#!/bin/bash
# chmod +x /path_to_your_project/scripts/start_consul.sh
echo "Starting Consul..."
consul agent -dev &
