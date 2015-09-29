#!/bin/sh
MCRL2_FILENAME=$1.mcrl2 # The mcrl2 readable file.
LPS_FILENAME=$1.lps # A linear process specification file.
LTS_FILENAME=$1.lts # A labelled transition system file..

if [ ! -e "${MCRL2_FILENAME}" ]; then
    echo "Requires a input <file>.mcrl2 argument"; exit;
fi

mcrl22lps ${MCRL2_FILENAME} ${LPS_FILENAME} &&
lps2lts ${LPS_FILENAME} ${LTS_FILENAME} &&
ltsgraph ${LTS_FILENAME}
