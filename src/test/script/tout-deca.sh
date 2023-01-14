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
    
    decac $fichier
    ima "./src/test/deca/codegen/valid/homemade/test/${nom%.deca}.ass" 2>&1 > actuel
    # actuel=$(ima "./src/test/deca/codegen/valid/homemade/test/${nom%.deca}.ass")
    if ! diff -Z actuel "./src/test/deca/codegen/valid/homemade/resultat/${nom%.deca}_resultat.txt"
    then
        echo "erreur innatendu"
        echo "On s'attendait à"
        cat ./src/test/deca/codegen/valid/homemade/resultat/${nom%.deca}_resultat.txt
        echo ""
        echo "Et ima nous a retourné:"
        cat actuel
    else
        i=$i+1
        echo -e "\r$i/$nb_fichier"
    fi

    rm "./src/test/deca/codegen/valid/homemade/test/${nom%.deca}.ass"
    

done

echo "Début des tests invalides"
echo ""
echo "---------------------------------------"
echo ""

i=0
nb_fichier=${ls-l | wc-l}
export nb_fichier
export i
for file in ./src/test/deca/codegen/invalid/homemade/test/*.deca
do
    echo "$file"
    nom=${file##*/}
    export nom
    
    decac $file
    ima "./src/test/deca/codegen/invalid/homemade/test/${nom%.deca}.ass" 2>&1 > actuel
    # actuel=$(ima "./src/test/deca/codegen/valid/homemade/test/${nom%.deca}.ass")
    if ! diff -Z actuel "./src/test/deca/codegen/invalid/homemade/resultat/${nom%.deca}_resultat.txt"
    then
        echo "erreur innatendu"
        echo "On s'attendait à"
        cat ./src/test/deca/codegen/invalid/homemade/resultat/${nom%.deca}_resultat.txt
        echo ""
        echo "Et ima nous a retourné:"
        cat actuel
    else
        i=$i+1
        echo -e "\r$i/$nb_fichier"
    fi

    rm "./src/test/deca/codegen/invalid/homemade/test/${nom%.deca}.ass"
    

done

rm actuel
