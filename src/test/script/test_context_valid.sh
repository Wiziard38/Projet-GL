#! /bin/sh

# Script pour tester tous les test de contextes qui sont valides

# On lance test_context sur les tests valides et on vérifie qu'ils sont bien valides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/context/valid"

# exemple de définition d'une fonction
test_context_valide () {
    test_result = test_context "$1" 2>&1
    
    if echo "$test_result" | grep -q -e "$1:[0-9][0-9]*:" then
        echo "Echec inattendu pour test_context sur $1."
    elif echo "$test_result" | grep -q -e "[Ee]rror" then
        echo "Erreur non soulevée pour $1."
    else
        echo "Succes attendu de test_context sur $1."
    fi
}    

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_valide "$cas_de_test"
done
