#! /bin/sh

# Script pour tester tous les test de syntaxe

# On lance test_syntax sur les tests et on vérifie qu'ils sont bien valides ou invalides


echo "Début des tests invalides"
echo ""
echo "-------------------------------------"
echo ""
test_synt_invalid.sh
echo ""
echo ""
echo "Début des tests valides"
echo ""
echo "-------------------------------------"
echo ""
test_synt_valid.sh