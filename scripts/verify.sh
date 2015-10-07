#!/bin/sh
LTS_FILENAME="$1.lts"
STATEFORMULA_FILENAME="$2.mu"
PBES_FILENAME="$2.pbes"
BES_FILENAME="$2.bes"

if [ ! -e ${LTS_FILENAME} ] || [ ! -e ${STATEFORMULA_FILENAME} ]; then
    echo "Input format is <filename>.lts <filename2>.mu";
    exit -1;
fi

lts2pbes --formula=${STATEFORMULA_FILENAME} ${LTS_FILENAME} ${PBES_FILENAME}
pbes2bool ${PBES_FILENAME}
