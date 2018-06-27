#!/usr/bin/env bash
local="$(cd "$(dirname "$0")"; pwd)"

if [ ! -e ~/.kube/config-cluster1 ]; then
    echo 'Primeiro configure o EKS como descrito aqui: https://docs.aws.amazon.com/pt_br/eks/latest/userguide/getting-started.html'    
    exit 0
fi

if [ ! -e ~/.aws/credentials ]; then
    echo 'É necessário ter credencias de acesso remoto do aws configuradas, pode ser feito com: aws configure'
    exit 0
fi

cd $local
docker build .. -t creditservice --build-arg STORE_ADDR="$1" --build-arg LOG_ADDR="$2"
docker login
user=$(docker info | grep Username: | awk '{print $2}')
docker tag creditservice $user/creditservice
docker push $user/creditservice
sed s/wison27/$user/ rc_.json > rc.json

export KUBECONFIG=~/.kube/config-cluster1
# kubectl delete rc/name 
kubectl apply -f ./rc.json
kubectl apply -f ./svc.json
# kubectl get all  -o wide

