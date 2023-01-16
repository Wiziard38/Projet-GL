#! /bin/bash

#Un script shell qui lance tous les tests créer pour le lexeur

#Utilisation: On créer des fihiers de tests dans src/test/deca/syntax/[invalid|valid] avec comme nom "fichier_test.deca"
#Dans le dossier src/test/deca/syntax/[invalid|valid]/resultat on met pour chaque fichier test "fichier_test.deca"
#un fichier resultat "fichier_test-resultat.txt" qui contient le résultat attendu par le lexeur.

#Ce script comparera le résultat du lexeur et le fichier résultat, écrira ok si se sont les mêmes et faux sinon
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'


cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)
PATH=$source_dir/src/test/script/launchers:"$PATH"

# On recupere le directory d'entree
input_dir="$source_dir/src/test/deca/syntax/valid/homemade/lexer"


total_test=$(find $input_dir/test/ -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0


echo ""
echo "                LEXER - VALID TESTS                    "

echo "-------------------------------------------------------"
echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
printf "\033[1A"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    test_lex "$fichier" &> actual
    echo ""
    if ! diff -w actual "$input_dir/resultat/${nom%.deca}_resultat.txt" &> /dev/null
    then
        total_failed=$((total_failed+1))
        echo -e "${RED}Erreur non soulevée pour ${NC}$fichier"
        echo ""
    else 
        total_valid=$((total_valid+1))
    fi

    printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
    printf "\033[1A"

done

echo ""

input_dir="$source_dir/src/test/deca/syntax/invalid/homemade/lexer"

total_test=$(find $input_dir/test/ -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0

echo "-------------------------------------------------------"
echo ""
echo "               LEXER - INVALID TESTS                   "

echo "-------------------------------------------------------"
echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
printf "\033[1A"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    t=$(test_lex "$fichier" > actual 2> actualErr)
    echo ""
    if ! diff -w actual "$input_dir/resultat/${nom%.deca}_resultat.txt" >/dev/null; then
        if ! diff -w actualErr "$input_dir/resultat/${nom%.deca}_resultat.txt" >/dev/null; then
            total_valid=$((total_valid+1))
        else
            total_failed=$((total_failed+1))
            echo -e "${RED}Erreur non soulevée pour ${NC}$fichier"
            echo ""
            exit 1
        fi
    else 
        total_valid=$((total_valid+1))
    fi

    printf "\033[1A"
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}            ${RED}FAILED: $total_failed  ${NC}            TOTAL: $total_test"
    printf "\033[1A"
done

echo ""
echo "-------------------------------------------------------"

rm actual actualErr