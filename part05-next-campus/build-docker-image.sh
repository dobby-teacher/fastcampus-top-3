#!/bin/bash

# 각 모듈별 Docker 이미지 빌드
docker build -t next-discovery:latest -f next-discovery/Dockerfile .
docker build -t next-gateway:latest -f next-gateway/Dockerfile .

docker build -t next-course-service:latest -f next-course-service/Dockerfile .
docker build -t next-file-manage-service:latest -f next-file-manage-service/Dockerfile .
docker build -t next-user-service:latest -f next-user-service/Dockerfile .

docker build -t next-enrollment-service:latest -f next-enrollment-service/Dockerfile .
docker build -t next-playback-service:latest -f next-playback-service/Dockerfile .
docker build -t next-graphql:latest -f next-graphql/Dockerfile .

