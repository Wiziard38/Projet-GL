#! /bin/sh

# Script pour tester tous les test de contextes

# On lance test_context sur les tests et on vérifie qu'ils sont bien valides ou invalides

echo ""
echo "      CONTEXT - INVALID TESTS         "
test_context_invalid.sh
echo ""
echo "       CONTEXT - VALID TESTS           "
test_context_valid.sh