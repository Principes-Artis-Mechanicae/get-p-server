#!/bin/bash
./gradlew clean build
sudo docker-compose up -d --build 