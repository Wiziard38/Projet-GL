#! /bin/bash

# Script pour tester tous les test de syntaxes qui sont valides
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# On lance test_synt sur les tests valides et on vérifie qu'ils sont bien valides

# Obtenir le chemin absolu du répertoire du script
cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"
input_dir="$source_dir/src/test/deca/syntax/valid/homemade/parser"

# Variables pour connaitre le nombre de tests valides
total_test=$(find $input_dir -type f -name "*.deca" | wc -l)
total_test_res=$(find $input_dir -name "*_resultat.txt" | wc -l)
total_test=$((total_test+total_test_res))

total_valid=0
total_failed=0

# exemple de définition d'une fonction
test_synt_valide () {

    test_result=$(test_synt "$1" 2>&1)

    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        printf "\033[1A"
        echo "${RED}Echec inattendu pour test_synt sur ${NC}$1"
        echo ""
        echo ""
        total_failed=$((total_failed+1))
        
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        printf "\033[1A"
        echo "${RED}Erreur non soulevée pour ${NC}$1"
        echo ""
        echo ""
        total_failed=$((total_failed+1))
        
    else
        # echo "Succes attendu de test_synt sur $1."
        total_valid=$((total_valid+1))
    fi
}


echo ""
echo "                SYNTAX - VALID TESTS                   "
echo "-------------------------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_synt_valide "$cas_de_test"
    # printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"

done

# Test pour les fichiers ayant un resultat

for fichier_res in $(find $input_dir -name "*_resultat.txt")
do
    nom=${fichier_res##*/}

    test_synt "$input_dir/test/${nom%_resultat.txt}.deca" &> actual
    echo ""
    if ! diff -w actual "$fichier_res" #&> /dev/null
    then
        total_failed=$((total_failed+1))
        printf "\033[1A"
        echo -e "${RED}Erreur non soulevée pour ${NC}$input_dir/test/${nom%_resultat.txt}.deca"
        echo ""
        echo ""
        exit 1
    else 
        total_valid=$((total_valid+1))
    fi

    printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
    printf "\033[1A"

done

echo ""


echo "-------------------------------------------------------"
