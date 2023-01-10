#! /bin/sh

# Auteur : gl39
# Version initiale : 01/01/2023

# On lance test_synt sur les tests invalides et on vérifie qu'ils sont bien invalides

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)
# Définir le répertoire d'entrées
input_dir="$script_dir/../deca/syntax/invalid/"

# exemple de définition d'une fonction
test_synt_invalide () {
    # $1 = premier argument.
    if test_synt "$1" 2>&1 | grep -q -e "$1:[0-9][0-9]*:"
    then
        echo "Echec attendu pour test_synt sur $1."
    else
        echo "Succes inattendu de test_synt sur $1."
    fi
}    

for cas_de_test in $(find $input_dir -name "*.deca")
do
    test_synt_invalide "$cas_de_test"
done
