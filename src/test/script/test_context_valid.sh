#! /bin/sh

# Script pour tester tous les test de contextes qui sont valides
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# On lance test_context sur les tests valides et on vérifie qu'ils sont bien valides


# Obtenir le répertoire où sont les tests
cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"
input_dir="$source_dir/src/test/deca/context/valid"


# Variables pour connaitre le nombre de tests valides
total_test=$(find $input_dir -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0

# exemple de définition d'une fonction
test_context_valide () {
    test_result=$(test_context "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        printf "\033[1A"
        echo "${RED}Echec inattendu pour test_context${NC}               "
        echo "$test_result"
        echo ""
        total_failed=$((total_failed+1))
        # exit 1
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        printf "\033[1A"
        echo "${RED}Erreur non soulevée ${NC}                            "
        echo "$test_result"
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
echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_valide "$cas_de_test"
    printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
done

echo "-------------------------------------------------------"
