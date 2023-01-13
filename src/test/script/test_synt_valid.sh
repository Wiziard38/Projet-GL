#! /bin/sh

# Script pour tester tous les test de syntaxes qui sont valides
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'
# On lance test_synt sur les tests valides et on vérifie qu'ils sont bien valides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/syntax/valid"

# Variables pour connaitre le nombre de tests valides
total_test=($(find $input_dir -type f -name "*.deca" | wc -l))
total_valid=0

# exemple de définition d'une fonction
test_synt_valide () {
    test_result=$(test_synt "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        echo "${RED}Echec inattendu pour test_synt sur $1.${NC}"
        exit 1
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        echo "${RED}Erreur non soulevée pour $1.${NC}"
        exit 1
    else
        # echo "Succes attendu de test_synt sur $1."
        total_valid=$((total_valid+1))
    fi
}    

echo -en "\r${GREEN}PASSED: $total_valid ${NC}      TOTAL: $total_test"
echo "--------------------------------------"

for cas_de_test in $(find $input_dir -name "*.deca" ! -path "*lexer*")
do
    test_synt_valide "$cas_de_test"
    printf "\033[1A"
    echo -en "\r${GREEN}PASSED: $total_valid ${NC}      TOTAL: $total_test"
done

echo "--------------------------------------"
