#!/bin/sh
if [ -z "$1" ] || [  -z "$2" ]; then
    echo "Input format is <filename>.lts <dirname>"; exit -1;
fi

FILES=$(ls $2)
SUFFIX=".mcf"

for file in $FILES; do
    case "$file" in
        *".mcf"*)
            FILE=$file
            FILE=${FILE%$SUFFIX}
            sh ./$(dirname $0)/verify.sh $1 $2/$FILE $3
        ;;
    esac
done
