@echo off
kubectl apply -f k8s
kubectl apply -f ms-admin/k8s
kubectl apply -f ms-api-gateway/k8s
kubectl apply -f ms-cms-service/k8s
kubectl apply -f ms-stock-service/k8s


