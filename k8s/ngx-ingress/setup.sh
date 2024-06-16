#!/bin/sh

kubectl apply -f common/ns-and-sa.yaml
kubectl apply -f rbac

kubectl apply -f common
kubectl apply -f common/crds

##  启动只要需要下面几个文件
# kubectl apply -f common/ns-and-sa.yaml
# kubectl apply -f rbac/rbac.yaml
# kubectl apply -f common/nginx-config.yaml
# kubectl apply -f common/default-server-secret.yaml


