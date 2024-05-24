#!/bin/bash

./gradlew clean build -x test
nohup java -Dspring.profiles.active=sandbox -jar build/libs/OneDayOneBaek-0.0.1-SNAPSHOT.jar > log.txt 2>&1 &