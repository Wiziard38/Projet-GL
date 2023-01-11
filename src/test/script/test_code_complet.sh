#!/bin/bash

# Script pour vérifier les tests de la partie C

# On prend tous les programmes deca à tester en entrée se trouvant
# dans le répertoire donné en argument.
# Il faut que les tests affichent sur la sortie standard "correct" ou "incorrect"
# On compare donc la sortie standard à ces texts là

# De plus, on peut donner une option aux scripts (doit être le 2ème argument de la commande shell):
# -d : delete -> supprime les fichiers textes générés s'ils sont identiques
# -da : delete all -> supprime tous les fichiers textes générés, qu'ils soient identiques ou pas

# Nom du répertoire de recherche des programmes .deca
base_dir=$1

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Variables pour connaitre le nombre de tests valides
total_test=0
total_valid=0

# Fonction pour comparer les sorties des tests
test_code_valide () {
    test_result=$(test_context "$1" 2>&1)
    
    if echo "$test_result" | grep -q -e "incorrect"; then
        echo "Le test $1 n'est pas concluant."
    elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
        echo "Erreur non soulevée pour $1."
    else
        echo "Le test $1 a fonctionné."
        total_valid=$((total_valid+1))
    fi
}  

# Recherche de tous les fichiers .deca dans le répertoire de base et ses sous-répertoires
for input_file in $(find $base_dir -name "*.deca")
do
    test_code_valide $input_file
    total_test=$((total_test+1))
done

echo "--------------------------------------"
echo "Tests passés: $total_valid / $total_test."
echo "--------------------------------------"
