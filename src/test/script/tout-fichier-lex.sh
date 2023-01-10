
#Un script shell qui lance tous les tests créer pour le lexeur

#Utilisation: On créer des fihiers de tests dans src/test/deca/syntax/[invalid|valid] avec comme nom "fichier_test.deca"
#Dans le dossier src/test/deca/syntax/[invalid|valid]/resultat on met pour chaque fichier test "fichier_test.deca"
#un fichier resultat "fichier_test-resultat.txt" qui contient le résultat attendu par le lexeur.

#Ce script comparera le résultat du lexeur et le fichier résultat, écrira ok si se sont les mêmes et faux sinon


cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/test/script/launchers:"$PATH"
echo "Début des tests valid"
echo ""
echo "---------------------------------------"
echo ""

for fichier in ./src/test/deca/syntax/valid/*.deca
do
    echo "$fichier"
    nom=${fichier##*/}
    export nom
    test_lex "$fichier" 2>&1 > actual
    cat actual
    echo ""
    if diff -w actual "./src/test/deca/syntax/lexer/valid/resultat/${nom%.deca}_resultat.txt"
    then
        echo ""
        echo "ok"
    else
        echo ""
        echo "faux"
    fi
    echo ""
    echo "----------------------------------"
    echo ""
done

echo ""
echo "Début des tests invalids"
echo "--------------------------------------"
echo ""

for fichier in ./src/test/deca/syntax/invalid/*.deca
do
    echo "$fichier"
    nom=${fichier##*/}
    export nom
    test_lex "$fichier" 2>&1 >actual
    if diff -w actual "./src/test/deca/syntax/lexer/invalid/resultat/${nom%.deca}_resultat.txt"
    then
        echo ""
        echo "ok"
    else
        echo ""
        echo "faux"
    fi
    echo ""
    echo "----------------------------------"
    echo ""
done