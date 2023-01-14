#! /bin/sh

# Script pour tester tous les test de contextes qui sont invalides
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# On lance test_context sur les tests invalides et on vérifie qu'ils sont bien invalides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/context/invalid"

# Variables pour connaitre le nombre de tests valides
total_test=$(find $input_dir -type f -name "*.deca" | wc -l)
total_valid=0

test_context_invalide () {
    test_result=$(test_context "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        # echo "Echec attendu pour test_context sur $1."
        total_valid=$((total_valid+1))
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        echo "${RED}Erreur non soulevée pour $1.${NC}"
        # exit 1
    else
        echo "${RED}Succes inattendu de test_context sur $1.${NC}"
        # exit 1
    fi
}    


echo "--------------------------------------"
echo -en "\r${GREEN}PASSED: $total_valid ${NC}      TOTAL: $total_test"

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_invalide "$cas_de_test"
    printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}      TOTAL: $total_test"
done

echo "--------------------------------------"
