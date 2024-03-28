#!/bin/bash

./env.sh .env.test
./gradlew test
echo "[test.sh] test complete"