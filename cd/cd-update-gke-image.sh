#!/usr/bin/env bash
cd ..
kubectl config set-cluster gke_collabkare-250512_us-east4_collabkare-private --server=https://35.199.21.127
kubectl config set-credentials gke_collabkare-250512_us-east4_collabkare-private --token=$TOKEN
kubectl config set-context gke_collabkare-250512_us-east4_collabkare-private --cluster=gke_collabkare-250512_us-east4_collabkare-public --user=gke_collabkare-250512_us-east4_collabkare-public
kubectl config use-context gke_collabkare-250512_us-east4_collabkare-private
kubectl set image deployment/virtual-accountgallery virtual-gallery=gcr.io/collabkare-250512/virtual-gallery-cndev:$BITBUCKET_COMMIT --namespace=collabkare-dev --user=gke_collabkare-250512_us-east4_collabkare-private --local=false