#! /bin/sh

# Test de l'interface en ligne de commande de decac.
# On ne met ici qu'un test trivial, a vous d'en ecrire de meilleurs.

cd "$(dirname "$0")"/../../.. || exit 1

PATH=./src/main/bin:"$PATH"

# ====================================================================================================
path='src/test/deca/compiler'
mkdir $path > /dev/null 2>&1
echo "{ int x = 0; print(x+1); }" > $path/test.deca
touch $path/file1.deca 
touch $path/file2.deca 
touch $path/file3.deca 
touch $path/fileErr.deca

# Function to run a test and check the exit code if valid
run_valid_test() {
    # Run the decac command with the specified arguments
    decac "$@" > /dev/null 2>&1
    # Save the exit code of the decac command
    exit_code=$?
    # Check the exit code
    if [[ $exit_code -ne 0 ]]; then
        echo "ERREUR decac $@ devrait marcher"
        exit 1
    fi
}

# Function to run a test and check the exit code if invalid
run_invalid_test() {
    # Run the decac command with the specified arguments
    decac "$@" > /dev/null 2>&1
    # Save the exit code of the decac command
    exit_code=$?
    # Check the exit code
    if [[ $exit_code -eq 0 ]]; then
        echo "ERREUR decac $@ ne devrait pas marcher"
        exit 1
    fi
}

# ====================================================================================================

run_valid_test

decac=$(decac)

if [ "$decac" = "" ]; then
    echo "ERREUR: decac n'a produit aucune sortie"
    exit 1
fi

echo "$decac" | grep -c "usage: decac *" > /dev/null 2>&1
if [ $? != 0 ] ; then
    echo "ERREUR: La sortie de decac ne specifie pas l'usage de la commande"
    exit 1
fi

echo "Pas de probleme detecte avec decac"

# ====================================================================================================

decac_moins_b=$(decac -b)

if [ "$?" -ne 0 ]; then
    echo "ERREUR: decac -b a termine avec un status different de zero."
    exit 1
fi

if [ "$decac_moins_b" = "" ]; then
    echo "ERREUR: decac -b n'a produit aucune sortie"
    exit 1
fi

if echo "$decac_moins_b" | grep -i -e "erreur" -e "error"; then
    echo "ERREUR: La sortie de decac -b contient erreur ou error"
    exit 1
fi

run_invalid_test -b -p
run_invalid_test -b -v
run_invalid_test -b $path/file1.deca

echo "Pas de probleme detecte avec decac -b"

# ====================================================================================================

run_invalid_test -p -v
run_valid_test -p $path/file1.deca

echo "Pas de probleme detecte avec decac -p"

# ====================================================================================================

run_invalid_test -v -p
run_valid_test -v $path/file1.deca
decac_moins_v=$(decac -v $path/test.deca)

if [ "$decac_moins_v" != "" ]; then
    echo "ERREUR: decac -v a produit une sortie."
    exit 1
fi

echo "Pas de probleme detecte avec decac -v"

# ====================================================================================================

run_valid_test -d $path/file1.deca
run_valid_test -d -d $path/file1.deca
run_valid_test -d -d -d -d $path/file1.deca
run_valid_test -d -d -d -d -d -d $path/file1.deca
run_valid_test -p -d -d $path/file1.deca
run_valid_test -v -d -d $path/file1.deca

echo "Pas de probleme detecte avec decac -d*"

# ====================================================================================================

run_valid_test -p $path/file1.deca $path/file2.deca
run_valid_test -p $path/file1.deca $path/file2.deca $path/file1.deca
run_valid_test -v $path/file1.deca $path/file2.deca
run_valid_test -v $path/file1.deca $path/file2.deca $path/file1.deca
run_valid_test $path/file1.deca $path/file2.deca
run_valid_test $path/file1.deca $path/file2.deca $path/file1.deca
run_invalid_test $path/fileErr.dec
run_invalid_test $path/deca.dec
run_invalid_test $path/test.deca.test
run_invalid_test $path/deca

echo "Pas de probleme detecte avec divers fichiers en parametre"

# ====================================================================================================

run_valid_test -r 7 $path/file1.deca
run_valid_test -r 4 $path/file1.deca
run_valid_test -r 16 $path/file1.deca
run_invalid_test -r 17 $path/file1.deca
run_invalid_test -r 3 $path/file1.deca

echo "Pas de probleme detecte avec decac -r X"

# ====================================================================================================

rm -r $path/
