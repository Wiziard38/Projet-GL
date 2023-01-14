#!/bin/bash

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"
echo "Début des tests valid"
echo ""
echo "---------------------------------------"
echo ""

i=0
nb_fichier=${ls-l | wc-l}
export nb_fichier
export i
for fichier in ./src/test/deca/codegen/valid/homemade/test/*.deca
do
    echo "$fichier"
    nom=${fichier##*/}
    export nom
    
    deca $fichier
    ima "$fichier" 2>&1 > actual
    if ! diff -w actual "./src/test/deca/codegen/valid/homemade/resultat/${nom%.deca}_resultat.txt"
    then
        echo "erreur innatendu"
        echo "On s'attendait à"
        cat actual
        echo "Et ima nous a retourné:"
        ima $fichier
    else
        i=$i+1
        echo "\r$i/$nb_fichier"
    fi
    

done

