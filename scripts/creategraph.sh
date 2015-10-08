#!/bin/sh
#sh ./makemodel.sh $1 && 

LTS_FILENAME="$1.lts"

if [ ! -e ${LTS_FILENAME} ]; then
    echo "Input format <filename>.lts";
    exit -1;
fi

ltsgraph ${LTS_FILENAME}
