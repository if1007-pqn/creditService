# credit service

a credit microservice over spring boot and mongo

## running only local application (dev)
* requires storeservice running in same local
<pre> ./gradlew run </pre>

## running container local (prod)
* used to test communication with logservice local, firstly run the run.sh of logservice, then do it:
<pre> ./run.sh </pre>

## running tests (test)
<pre> ENV=test ./run.sh </pre>

## build and push to AWS EKS
* this script requires account in docker hub:
<pre> bash eks/push-eks.sh $STORESERVICE_ADDR $LOGSERVICE_ADDR</pre>
STORESERVICE_ADDR is the storeservice address (with port), used to get levels of store for calculate credit received by purchase.
LOGSERVICE_ADDR is the logservice address (with port), used to logstash and packetbeat in creditservice container, send the logs and metrics respectively.


## docs/api
run the project and access the swagger page:
<pre> GET /swagger-ui.html </pre>
or
[here](https://github.com/if1007/creditService/wiki/api) (may be outdated)

