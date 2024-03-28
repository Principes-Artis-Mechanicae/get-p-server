#!/bin/bash

ENV_FILE="$1"

# 스크립트를 실행할 때 환경 변수 파일을 명시하지 않은 경우에 대한 처리
if [ -z "$ENV_FILE" ]; then
    echo "[env.sh] usage: $0 <env_file>"
    exit 1
fi

echo "[env.sh] environment variable file: $ENV_FILE"

# 환경 변수 파일이 존재하는지 확인
if [ -f "$ENV_FILE" ]; then
    # 파일에서 각 줄을 읽어옴
    while IFS= read -r line || [ -n "$line" ]; do
        # 주석이 아닌 줄이고, key=value 형태인 경우에만 처리
        if [[ $line != \#* && $line == *"="* ]]; then
            # key와 value를 분리
            key="${line%%=*}"
            value="${line#*=}"
            value=$(sed 's/^"\|"$//g' <<< "$value")
            # 환경 변수로 등록
            export "$key"="$value"
        fi
    done < "$ENV_FILE"
else
    echo "[env.sh] $ENV_FILE not found."
fi

echo "[env.sh] environment variable load complete"