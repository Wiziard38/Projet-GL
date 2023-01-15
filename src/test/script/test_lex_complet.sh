#! /bin/bash

#Un script shell qui lance tous les tests créer pour le lexeur

#Utilisation: On créer des fihiers de tests dans src/test/deca/syntax/[invalid|valid] avec comme nom "fichier_test.deca"
#Dans le dossier src/test/deca/syntax/[invalid|valid]/resultat on met pour chaque fichier test "fichier_test.deca"
#un fichier resultat "fichier_test-resultat.txt" qui contient le résultat attendu par le lexeur.

#Ce script comparera le résultat du lexeur et le fichier résultat, écrira ok si se sont les mêmes et faux sinon
RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

script_dir=$(cd $(dirname $0) && pwd)
PATH=./src/test/script/launchers:"$PATH"

# Obtenir le répertoire où sont les tests
input_dir="$script_dir/../deca/syntax/valid/homemade/lexer"

total_test=$(find $input_dir/test/ -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0


echo ""
echo "                LEXER - VALID TESTS                    "

echo "-------------------------------------------------------"
echo -e "\r${GREEN}PASSED: $total_valid ${NC}         ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
printf "\033[1A"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    test_lex "$fichier" 2>&1 > actual
    if ! diff -Z actual "$input_dir/resultat/${nom%.deca}_resultat.txt" &> /dev/null
    then
        total_failed=$((total_failed+1))
        echo -e "${RED}Erreur non soulevée pour ${NC}$fichier"
        echo ""
    else 
        total_valid=$((total_valid+1))
    fi
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}         ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    printf "\033[1A"

done

echo ""

input_dir="$script_dir/../deca/syntax/invalid/homemade/lexer"

total_test=$(find $input_dir/test/ -type f -name "*.deca" | wc -l)
total_valid=0
total_failed=0


echo ""
echo "               LEXER - INVALID TESTS                   "

echo "-------------------------------------------------------"
echo -e "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
printf "\033[1A"

for fichier in $input_dir/test/*.deca
do
    nom=${fichier##*/}
    t=$(test_lex "$fichier" 2>&1 >actual)
    if ! diff -Z actual "$input_dir/resultat/${nom%.deca}_resultat.txt" &> /dev/null
    then
        total_failed=$((total_failed+1))
        echo -e "${RED}Erreur non soulevée pour ${NC}$fichier"
        echo ""
    else 
        total_valid=$((total_valid+1))
    fi
    echo -e "\r${GREEN}PASSED: $total_valid ${NC}        ${RED}FAILED: $total_failed  ${NC}         TOTAL: $total_test"
    printf "\033[1A"
done

echo ""

rm actual