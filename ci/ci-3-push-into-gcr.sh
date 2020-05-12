#!/usr/bin/env bash
cd ..      
docker tag bhits/staff-ui-api gcr.io/collabkare-250512/virtual-staff-ui-api-cndev:${BITBUCKET_COMMIT}
docker push gcr.io/collabkare-250512/virtual-staff-ui-api-cndev:$BITBUCKET_COMMIT
echo "Pushed image into gcr"
