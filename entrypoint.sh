#!/bin/bash
# entrypoint.sh

# Define variables
#export CUCUMBER_RUN_SCRIPT="CUCUMBER_FILTER_TAGS='@flightBooking' mvn test -DENV_NAME=LambdaTest -DsuiteXmlFile=testng.xml -DPARALLEL=2"
#This will be passed from CLI while running the container eg. `docker run --rm -e CUCUMBER_RUN_SCRIPT="<SCRIPT>" automation_framework`

echo "Running command: $CUCUMBER_RUN_SCRIPT"
#cat  /app/scripts/dockerRunScript.sh
ls -lh /app

#ls -lh /app/scripts

echo $CUCUMBER_RUN_SCRIPT

# Execute the command directly
eval $CUCUMBER_RUN_SCRIPT