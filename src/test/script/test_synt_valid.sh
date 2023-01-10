#! /bin/sh

# Script pour tester tous les test de syntaxes qui sont valides

# On lance test_synt sur les tests valides et on vérifie qu'ils sont bien valides

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/syntax/parser/valid"

# exemple de définition d'une fonction
test_synt_valide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec inattendu pour test_synt sur $1."
    else
        echo "Succes attendu de test_synt sur $1."
    fi
}    

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_synt_valide "$cas_de_test"
done
