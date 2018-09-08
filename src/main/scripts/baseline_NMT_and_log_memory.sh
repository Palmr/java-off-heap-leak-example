#!/usr/bin/env bash

APP_MAIN="uk.co.palmr.offheapleakexample.Main"
MATCHED_PROCS=$(ps -C java -o pid,cmd | grep ${APP_MAIN})
PID=$(awk '{print $1}' <<< $MATCHED_PROCS)
RESIDENT_SET_LOG_FILE=./memory.log

if [ -z "${PID}" ]
then
    echo "[ERROR] Could not find PID of application, are you sure it's running?"
    exit 1
fi
if [ $(wc -l <<< ${MATCHED_PROCS}) != 1 ]
then
    echo "[ERROR] Found multiple PIDs for application, check only one instance of ${APP_MAIN}"
    exit 2
fi

echo "Application found with PID=${PID}"

echo "Taking a NMT baseline"
jcmd ${PID} VM.native_memory baseline

echo "Logging Resident Set Memory"
while [ -d "/proc/${PID}" ]
do
    awk '($1 == "VmRSS:"){print strftime("%Y-%m-%d_%H:%M:%S"), $2, "kB"}' /proc/${PID}/status | tee -a ${RESIDENT_SET_LOG_FILE}
    sleep 0.5
done
