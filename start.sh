#!/usr/bin/env bash


if [ "$ENV" = 'prod' ]; then
    ./log.sh $LOG_ADDR &
fi

if [ "$ENV" = 'test' ]; then 
    ./gradlew test
else
    ./gradlew run
fi
