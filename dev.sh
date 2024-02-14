#!/bin/bash
./gradlew clean build
sudo docker compose --env-file .env.dev up -d --build
