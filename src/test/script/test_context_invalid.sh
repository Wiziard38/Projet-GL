#! /bin/sh

# Script pour tester tous les test de contextes qui sont invalides

# On lance test_context sur les tests invalides et on vérifie qu'ils sont bien invalides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/context/invalid"

# exemple de définition d'une fonction
test_context_invalide () {
    # $1 = premier argument.
    if test_context "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec attendu pour test_context sur $1."
    else
        echo "Succes inattendu de test_context sur $1."
    fi
}    

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_context_invalide "$cas_de_test"
done
