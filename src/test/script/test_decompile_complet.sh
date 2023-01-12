#!/bin/bash

# Script pour tester la méthode decompile
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# On prend tous les programmes deca valides en entrée se trouvant dans le répertoire donné en argument
# On fait l'arbre, puis on applique decompile à l'arbre
# On refait l'étape au-dessus avec le programme généré par decompile
# On compare les 2 fichiers

# De plus, on peut donner une option aux scripts (doit être le 2ème argument de la commande shell):
# -d : delete -> supprime les fichiers textes générés s'ils sont identiques
# -da : delete all -> supprime tous les fichiers textes générés, qu'ils soient identiques ou pas

# Obtenir le chemin absolu du répertoire du script
script_dir=$(cd $(dirname $0) && pwd)

# Nom du répertoire de recherche des programmes .deca
base_dir="$script_dir/../deca/syntax/valid"

# Définir le répertoire de sortie
output_dir="$script_dir/../../../target/test-decompile"

# Vérifier si le répertoire de sortie existe, et le créer s'il n'existe pas
if [ ! -d $output_dir ]; then
    mkdir -p $output_dir
fi

# Variables pour connaitre le nombre de tests valides
total_test=$(find $base_dir -type f -name "*.deca" ! -path "*lexer*" | wc -l)
total_valid=0


test_decompile_valide () {
    filename="$1"
    output_file1="$output_dir/output1_$(basename $filename)"
    output_file2="$output_dir/output2_$(basename $filename)"
    output_err="$output_dir/outputErr_$(basename $filename)"

    
    $(test_decompile $filename > $output_file1 2>$output_err)
    res_inter=$(cat $output_err)

    if [ ! "$res_inter" = "" ]; then
        echo -e "${RED}Ce fichier ne peut etre parsé correctement. $filename.${NC}"
        exit 1
    else
            
        $(test_decompile $output_file1 > $output_file2)
        test_result=$(diff -u <(sort $output_file1) <(sort $output_file2))
        
        if echo "$test_result" | grep -q -e "$filename:[0-9][0-9]*:"; then
            echo -e "${RED}Echec inattendu pour test_context sur $filename.${NC}"
            exit 1
        elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
            echo -e "${RED}Erreur non soulevée pour $filename.${NC}"
            exit 1
        else
            # echo "Succes attendu de test_context sur $1."
            total_valid=$((total_valid+1))
        fi

    fi
    rm $output_file1 $output_file2 $output_err
}    

echo "--------------------------"
echo -e "\rPASSED: $total_valid       TOTAL: $total_test"

for cas_de_test in $(find $base_dir -type f -name "*.deca" ! -path "*lexer*")
do
    test_decompile_valide "$cas_de_test"
    printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}      TOTAL: $total_test"
done

echo "--------------------------"
