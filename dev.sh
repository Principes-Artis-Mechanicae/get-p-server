#!/bin/bash

ENV_FILE=".env.dev"

# Check if the file exists
if [ -f "$ENV_FILE" ]; then
    # Read each line and print it
    while IFS= read -r line || [ -n "$line" ]; do
        if [[ $line != \#* && $line == *"="* ]]; then
           # Split the line into key and value
            key=$(echo "$line" | cut -d '=' -f 1)
            value=$(echo "$line" | cut -d '=' -f 2-)
            
            # Trim leading and trailing whitespaces from value
            value=$(echo "$value" | sed -e 's/^[[:space:]]*//' -e 's/[[:space:]]*$//')
            
            export $key="$value"
        fi
    done < $ENV_FILE
else
    echo "$ENV_FILE not found."
fi

./gradlew clean build
sudo docker compose --env-file .env.dev up -d --build
