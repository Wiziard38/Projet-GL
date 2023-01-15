#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"

# Partie valide

input_dir="$source_dir/src/test/deca/codegen/valid/homemade"

total_test=$(find $input_dir/test -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0

echo ""
echo "               CODEGEN - VALID TESTS                   "
echo "-------------------------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    
    decac $fichier
    ima "$input_dir/test/${nom%.deca}.ass" 2>&1 > actuel
    if ! diff -Z actuel "$input_dir/resultat/${nom%.deca}_resultat.txt"
    then
        printf "\033[1A"
        echo -e "${RED}Echec inattendu de compilation pour ${NC} $fichier"
        echo -n "Resultat attendu : "
        $(cat "$input_dir/resultat/${nom%.deca}_resultat.txt")
        echo -n "Resultat obtenu : "
        $(cat $actuel)
        echo ""
        echo ""
        total_failed=$((total_failed+1))
    else
        total_valid=$((total_valid+1))
    fi

    echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    rm "$input_dir/test/${nom%.deca}.ass"
done

echo ""
echo "-------------------------------------------------------"

# Partie invalide

input_dir="$source_dir/src/test/deca/codegen/invalid/homemade"

total_test=$(find $input_dir/test -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0

echo ""
echo "              CODEGEN - INVALID TESTS                  "
echo "-------------------------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    
    decac $fichier
    ima "$input_dir/test/${nom%.deca}.ass" 2>&1 > actuel
    if ! diff -Z actuel "$input_dir/resultat/${nom%.deca}_resultat.txt"
    then
        printf "\033[1A"
        echo -e "${RED}Succes inattendu de compilation pour ${NC} $fichier"
        echo -n "Resultat attendu : "
        $(cat "$input_dir/resultat/${nom%.deca}_resultat.txt")
        echo -n "Resultat obtenu : "
        $(cat $actuel)
        echo ""
        echo ""
        total_failed=$((total_failed+1))
    else
        total_valid=$((total_valid+1))
    fi

    echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    rm "$input_dir/test/${nom%.deca}.ass"
done

echo ""
echo "-------------------------------------------------------"

rm actuel
