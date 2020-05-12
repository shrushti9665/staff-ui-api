#!/usr/bin/env bash


cd ..
mvn -B -s settings.xml deploy -DskipTests package docker:build
echo "mvn -B verify"



