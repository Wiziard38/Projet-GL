#!/bin/bash

# Script pour tester la méthode decompile
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

# On prend tous les programmes deca valides en entrée se trouvant dans le répertoire donné en argument
# On fait l'arbre, puis on applique decompile à l'arbre
# On refait l'étape au-dessus avec le programme généré par decompile
# On compare les 2 fichiers

# Obtenir le chemin absolu du répertoire du script
cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"

# Nom du répertoire de recherche des programmes .deca
base_dir="$source_dir/src/test/deca/syntax/valid"

# Définir le répertoire de sortie
output_dir="$source_dir/target/test-decompile"

# Vérifier si le répertoire de sortie existe, et le créer s'il n'existe pas
if [ ! -d $output_dir ]; then
    mkdir -p $output_dir
fi

# Variables pour connaitre le nombre de tests valides
total_test=$(find $base_dir -type f -name "*.deca" ! -path "*lexer*" | wc -l)
total_valid=0
total_failed=0

echo ""
echo "                  DECOMPILE TESTS                      "


test_decompile_valide () {
    filename="$1"
    output_file1="$output_dir/output1_$(basename $filename)"
    output_file2="$output_dir/output2_$(basename $filename)"
    output_err="$output_dir/outputErr_$(basename $filename)"

    $(test_decompile $filename > $output_file1 2>$output_err)
    res_inter=$(cat $output_err)

    if [ ! "$res_inter" = "" ]; then
        printf "\033[1A"
        echo -e "${RED}Ce fichier ne peut etre parsé correctement.${NC} $filename"
        echo ""
        echo ""
        total_failed=$((total_failed+1))
        # exit 1
    else
            
        res_inter=$(test_decompile $output_file1 > $output_file2 2> $output_err)
        res_inter=$(cat $output_err)

        if echo "$res_inter" | grep -q -e "$output_file1:[0-9][0-9]*:"; then
            printf "\033[1A"
            echo -e "${RED}Erreur de parsing du fichier decompilé une premiere fois ${NC} $output_file1"
            echo ""
            echo ""
            total_failed=$((total_failed+1))
            rm $output_file2 $output_err
            # exit 1
        else
            test_result=$(diff -u <(sort $output_file1) <(sort $output_file2))
            
            if echo "$test_result" | grep -q -e "$filename:[0-9][0-9]*:"; then
                printf "\033[1A"
                echo -e "${RED}Echec inattendu pour test_context sur ${NC} $filename"
                echo ""
                echo ""
                total_failed=$((total_failed+1))
                # exit 1
            elif echo "$test_result" | grep -q -e "[Ee]rror|[Ee]xception"; then
                printf "\033[1A"
                echo -e "${RED}Erreur non soulevée pour $filename.${NC}"
                echo ""
                echo ""
                total_failed=$((total_failed+1))
                # exit 1
            else
                # echo "Succes attendu de test_context sur $1."
                total_valid=$((total_valid+1))
                rm $output_file1 $output_file2 $output_err
            fi
        fi
    fi
    #rm $output_file1 $output_file2 $output_err
}    

echo "-------------------------------------------------------"
echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"

for cas_de_test in $(find $base_dir -type f -name "*.deca" ! -path "*lexer*")
do
    test_decompile_valide "$cas_de_test"
    printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"

done

echo "-------------------------------------------------------"
