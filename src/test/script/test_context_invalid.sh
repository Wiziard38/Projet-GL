#! /bin/sh

# Script pour tester tous les test de contextes qui sont invalides

# On lance test_context sur les tests invalides et on vérifie qu'ils sont bien invalides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/context/invalid"

# Variables pour connaitre le nombre de tests valides
total_test=0
total_valid=0

test_context_invalide () {
    test_result=$(test_context "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:"; then
        echo "Echec attendu pour test_context sur $1."
        total_valid=$((total_valid+1))
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        echo "Erreur non soulevée pour $1."
    else
        echo "Succes inattendu de test_context sur $1."
    fi
}    

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_invalide "$cas_de_test"
    total_test=$((total_test+1))
done

echo "--------------------------------------"
echo "Tests passés: $total_valid / $total_test."
echo "--------------------------------------"