#! /bin/sh

# Script pour tester tous les test de contextes

# On lance test_context sur les tests et on vérifie qu'ils sont bien valides ou invalides


echo "Début des tests invalides"
echo ""
echo "-------------------------------------"
echo ""
test_context_invalid.sh
echo ""
echo ""
echo "Début des tests valides"
echo ""
echo "-------------------------------------"
echo ""
test_context_valid.sh