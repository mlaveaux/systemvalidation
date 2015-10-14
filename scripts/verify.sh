#!/bin/sh
sh ./$(dirname $0)/makemodel.sh $1 &&

LTS_FILENAME="$1.lts"
STATEFORMULA_FILENAME="$2.mcf"
PBES_FILENAME="$2.pbes"
BES_FILENAME="$2.bes"

if [ ! -e ${LTS_FILENAME} ] || [ ! -e ${STATEFORMULA_FILENAME} ]; then
    echo "Input format is <filename>.lts <filename2>.mcf";
    exit -1;
fi

echo ${STATEFORMULA_FILENAME}:

lts2pbes --formula=${STATEFORMULA_FILENAME} ${LTS_FILENAME} ${PBES_FILENAME} &&
pbes2bool ${PBES_FILENAME}
