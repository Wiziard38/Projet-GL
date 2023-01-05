

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
    test_lex "$fichier" > actual
    if diff actual "./src/test/deca/syntax/valid/resultat/${nom%.deca}-resultat.txt"
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
    test_lex "$fichier" > actual
    if diff actual "./src/test/deca/syntax/invalid/resultat/${nom%.deca}-resultat.txt"
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