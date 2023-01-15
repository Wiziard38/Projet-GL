#!/bin/bash

# Script pour autoriser l'execution de tous les autres scripts

# Obtenir le chemin absolu du répertoire des scripts
cd "$(dirname "$0")"/../../.. || exit 1
source_dir=$(pwd)

for script in $(find $source_dir -type f -name "*.sh")
do
    chmod +x $script
done