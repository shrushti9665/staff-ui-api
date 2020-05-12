#!/usr/bin/env bash


echo $GCLOUD_API_KEYFILE > ~/.gcloud-api-key.json
gcloud auth activate-service-account --key-file ~/.gcloud-api-key.json
gcloud config set project collabkare-250512
gcloud config set compute/region us-east4
gcloud config set compute/zone us-east4-a
gcloud auth configure-docker
echo now starting setting cluster
gcloud container clusters get-credentials collabkare-private --region=us-east4 --project $GCLOUD_PROJECT
echo "now starting setting cluster"
gcloud container clusters list
gcloud auth configure-docker --quiet
