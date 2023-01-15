#! /bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"

# On recupere le directory d'entree
input_dir="$source_dir/src/test/deca/codegen/valid/homemade"

total_test=$(find $input_dir/test -type f -name "*.deca"| wc -l)
total_test_registre=$(find $input_dir/test -type f -name "*.deca" -name "*test_depassement_registre*" | wc -l)
total_test=$((total_test+total_test_registre))

total_valid=0
total_failed=0

echo ""
echo "               CODEGEN - VALID TESTS                   "
echo "-------------------------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"

for fichier in $(find $input_dir/test/ -name "*.deca")
do
    nom=${fichier##*/}
    
    decac $fichier
    ima "$input_dir/test/${nom%.deca}.ass" 2>&1 > actuel
    echo ""
    if ! diff -Z actuel "$input_dir/resultat/${nom%.deca}_resultat.txt"
    then
        printf "\033[1A"
        echo "${RED}Echec inattendu pour${NC} $fichier"
        # echo -e "${RED}Echec inattendu de compilation pour ${NC} $fichier"
        # echo -n "Resultat attendu : "
        # $(cat "$input_dir/resultat/${nom%.deca}_resultat.txt")
        # echo -n "Resultat obtenu : "
        # $(cat $actuel)
        echo ""
        echo ""
        echo ""
        total_failed=$((total_failed+1))
    else
        total_valid=$((total_valid+1))
    fi

    printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    rm "$input_dir/test/${nom%.deca}.ass"
done


for fichier in $(find $input_dir/test/ -name "*.deca" -name "*test_depassement_registre*")
do
    nom=${fichier##*/}
    
    decac -r 4 $fichier
    ima "$input_dir/test/${nom%.deca}.ass" 2>&1 > actuel
    echo ""
    if ! diff -Z actuel "$input_dir/resultat/${nom%.deca}_resultat.txt"
    then
        printf "\033[1A"
        echo "${RED}Echec inattendu avec 4 registres pour${NC} $fichier"
        # echo -e "${RED}Echec inattendu de compilation pour ${NC} $fichier"
        # echo -n "Resultat attendu : "
        # $(cat "$input_dir/resultat/${nom%.deca}_resultat.txt")
        # echo -n "Resultat obtenu : "
        # $(cat $actuel)
        echo ""
        echo ""
        echo ""
        total_failed=$((total_failed+1))
    else
        total_valid=$((total_valid+1))
    fi

    printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    rm "$input_dir/test/${nom%.deca}.ass"
done


echo ""
echo "-------------------------------------------------------"

rm actuel