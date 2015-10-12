#!/bin/sh
sh ./$(dirname $0)/makemodel.sh $1

LTS_FILENAME="$1.lts"
LTS_REDUCED_FILENAME="$1.reduced.lps" # A linear process specification file.

if [ ! -e ${LTS_FILENAME} ]; then
    echo "Input format <filename>.lts";
    exit -1;
fi

ltsconvert ${LTS_FILENAME} ${LTS_REDUCED_FILENAME} --equivalence=branching-bisim &&
ltsgraph ${LTS_REDUCED_FILENAME}