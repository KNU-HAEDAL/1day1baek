#!/bin/bash

server_process=$(ps -ef | grep OneDayOneBaek-0.0.1-SNAPSHOT.jar | grep java)
server_pid=(${server_process// / })
server_pid=${server_pid[1]}

sudo kill -9 $server_pid