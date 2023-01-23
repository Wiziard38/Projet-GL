#! /bin/bash

# Script pour tester tous les tests de contextes qui sont valides
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# On lance test_context sur les tests valides et on vérifie qu'ils sont bien valides


# Obtenir le répertoire où sont les tests
cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"
input_dir="$source_dir/src/test/deca/context/valid/homemade"


# Variables pour connaitre le nombre de tests valides
total_test=$(find $input_dir -type f -name "*.deca" | wc -l)
total_test_res=$(find $input_dir -name "*_resultat.txt" | wc -l)
total_test=$((total_test+total_test_res))
total_valid=0
total_failed=0

# exemple de définition d'une fonction
test_context_valide () {
    test_result=$(decac -v "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        echo -e "${RED}Echec inattendu pour ${NC}$test_result"
        echo ""
        total_failed=$((total_failed+1))
        # exit 1
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        echo -e "${RED}Erreur non soulevée pour ${NC}$test_result"
        echo ""
        total_failed=$((total_failed+1))
        # exit 1
    else
        # echo "Succes attendu de test_context sur $1."
        total_valid=$((total_valid+1))
    fi
}    


echo ""
echo "               CONTEXT - VALID TESTS                   "
echo "-------------------------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_valide "$cas_de_test"
    # printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
    printf "\033[1A"
done


# Test pour les fichiers ayant un resultat

for fichier_res in $(find $input_dir -name "*_resultat.txt")
do
    nom=${fichier_res##*/}

    test_context "$input_dir/test/${nom%_resultat.txt}.deca" &> actual
    if ! diff -w actual "$fichier_res" &> /dev/null
    then
        total_failed=$((total_failed+1))
        echo -e "${RED}Erreur non soulevée pour ${NC}$input_dir/test/${nom%_resultat.txt}.deca"
        echo ""
        echo ""
    else
        total_valid=$((total_valid+1))
    fi

    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
    printf "\033[1A"

done

echo ""
echo "-------------------------------------------------------"

rm actual
