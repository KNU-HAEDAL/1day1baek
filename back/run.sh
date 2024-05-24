#!/bin/bash

./gradlew build
nohup java -Dspring.profiles.active=prod -jar build/libs/OneDayOneBaek-0.0.1-SNAPSHOT.jar > log.txt 2>&1 &