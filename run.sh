#!/usr/bin/env bash
local="$(cd "$(dirname "$0")"; pwd)"
cd "$local"

if [ ! "$ENV" = 'test' ]; then
    extra="-p 9090:9090 --link logservice"
    ENV="prod"
fi

function clean_mongo() {
    docker rm --force mongo 2>/dev/null >&2
}

clean_mongo
if ! docker images | grep -w -E -q "^mongostoreservice$ENV"; then
    docker build mongo -t mongostoreservice$ENV
fi
docker run --name mongo -p 8080:8080 -p 27017:27017 mongostoreservice$ENV &
if ! docker images | grep -w -E -q "^creditservice$ENV"; then
    docker build . -t creditservice$ENV --build-arg ENV=$ENV --build-arg LOG_ADDR='http://logservice:9200' --build-arg STORE_ADDR='http://mongo:8080'
else
    sleep 5 #time to mongo load
fi
if [ "$ENV" = 'test' ]; then
    sleep 20 # time to storeservice load first
fi
docker run --link mongo $extra creditservice$ENV && clean_mongo
