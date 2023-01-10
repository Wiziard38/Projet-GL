#!/bin/bash

# Script pour tester la méthode decompile

# On prend tous les programmes deca valides en entrée se trouvant dans le répertoire donné en argument
# On fait l'arbre, puis on applique decompile à l'arbre
# On refait l'étape au-dessus avec le programme généré par decompile
# On compare les 2 fichiers

# De plus, on peut donner une option aux scripts (doit être le 2ème argument de la commande shell):
# -d : delete -> supprime les fichiers textes générés s'ils sont identiques
# -da : delete all -> supprime tous les fichiers textes générés, qu'ils soient identiques ou pas

# Nom du répertoire de recherche des programmes .deca
base_dir=$1

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Définir le répertoire de sortie
output_dir="$script_dir/../../../target/test-decompile"

# Vérifier si le répertoire de sortie existe, et le créer s'il n'existe pas
if [ ! -d $output_dir ]; then
    mkdir -p $output_dir
fi

# Recherche de tous les fichiers .deca dans le répertoire de base et ses sous-répertoires
for input_file in $(find $base_dir -name "*.deca")
do

    # création des 2 fichiers de sortie de decompile()
    output_file1="$output_dir/output1_$(basename $input_file)"
    output_file2="$output_dir/output2_$(basename $input_file)"

    test_decompile < $input_file > $output_file1
    test_decompile < $output_file1 > $output_file2

    # Comparaison des fichiers
    diff -u <(sort $output_file1) <(sort $output_file2)

    # En cas de différences, afficher un message
    if [ $? -ne 0 ]; then
        echo "Les fichiers $output_file1 et $output_file2 sont différents"

        # Supprime les fichiers générés si l'option -da est activée
        if [ "$2" = "-da" ]; then
            rm $output_file1
            rm $output_file2
        fi

    else
        echo "Les fichiers $output_file1 et $output_file2 sont identiques"

        # Supprime les fichiers générés si l'option -d ou -da est activée
        if [ "$2" = "-d" ] || [ "$2" = "-da" ];  then
            rm $output_file1
            rm $output_file2
        fi
    fi
done