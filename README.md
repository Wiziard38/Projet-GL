<a name="readme-top"></a>

<h3 align="center">Deca Compiler</h3>

  <p align="center">
    Compilateur réalisé dans le cadre du Projet GL Ensimag
    <br />
    <a href="https://nicolasflamel.ensimag.fr/gl2023/gr8/gl39"><strong>Explore the docs »</strong></a>
    <br />
  </p>
</div>

<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#a-propos">A propos</a>
    </li>
    <li>
      <a href="#pour-commencer">Pour commencer</a>
      <ul>
        <li><a href="#langage-deca">Langage Deca</a></li>
        <li><a href="#fichier-assembleur">Fichier assembleur</a></li>
      </ul>
    </li>
    <li><a href="#maven">Maven</a></li>
    <li><a href="#utilisation">Utilisation</a></li>
    <li>
        <a href="#tests">Tests</a>
        <ul>
            <li><a href="#scripts-de-tests">Scripts de tests</a></li>
            <li><a href="#tests-manuels">Tests manuels</a></li>
        </ul>
    </li>
  </ol>
</details>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ABOUT THE PROJECT -->

## A propos

Ce projet ici présent est le fruit du travail du groupe gl39, réalisé dans le cadre du Projet Génie Logiciel de Grenoble INP - Ensimag, promotion 2024. Ce groupe est composé de : Amaré Baptiste, Fauchon Jules, Gauthier Paul, Girault Mathis et Stevant Guilhem.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->

## Pour commencer

### Langage Deca

Le langage Deca qui est compilé dans ce projet est un langage dérvié de Java, simplifié. Il est implémenté dans ce compilateur dans une version avec objet, mais sans possibilité de réaliser des casts explicites et l'utilisation de instanceof.

### Fichier assembleur

Le compilateur, en supposant sa bonne <a href="#utilisation">utilisation</a> et que le fichier deca passé en argument soit correct, va produire un fichier assembleur. En fonction de l'option choisie, ce fichier sera exécutable soit pour une machine abstraite IMA définie par les organisateurs du projet, soit pour une puce ARM v6, qu'on peut retrouver typiquement dans une carte Rasberry Pi 2.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- Maven -->

## Maven

Notre projet compile sous maven, voici les principaux goals du projet.

-   `$ mvn clean` : permet de supprimer le repértoire 'target/' qui contient les fichiers compilés
-   `$ mvn compile` : permet de compiler les classes java dans 'target/'
-   `$ mvn test-compile` : permet de compiler les fichiers de test java
-   `$ mvn test` : permet de lancer automatiquement les scripts de tests détaillés <a href="#scripts-de-tests">ici</a>
-   `$ mvn -Djacoco.skip=false verify` : permet d'instrumenter les fichiers java pour obtenir une couverture jacoco de notre projet

<!-- USAGE EXAMPLES -->

## Utilisation

```bash
decac [[-p | -v] [-n] [-r X] [-d]* [-P] [file.deca]*]
      | [-b] | [-arm] [-d]* [file.deca]*
```

| Option              | Description                                                                                                                                                                                                                     | Default |         Required?         |
| ------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :-----: | :-----------------------: |
| `-p` (banner)       | Affiche une bannière indiquant le nom de l'équipe                                                                                                                                                                               | `false` |            No             |
| `-p` (parse)        | Arrête decac après l'étape de construction de l'arbre, <br> et affiche la décompilation de ce dernier (i.e. s'il n'y a <br> qu'un fichier source à compiler, la sortie doit être un <br> programme deca syntaxiquement correct) | `false` |            No             |
| `-v` (verification) | Arrête decac après l'étape de vérifications (ne produit <br> aucune sortie en l'absence d'erreur)                                                                                                                               | `false` |            No             |
| `-n` (no check)     | Supprime les tests à l'exécution spécifiés dans les points <br> 11.1 et 11.3 de la sémantique de Deca.                                                                                                                          | `false` |            No             |
| `-r X` (registers)  | Limite les registres banalisés disponibles à R0 ... R{X-1}, <br> avec 4 <= X <= 16                                                                                                                                              |  `16`   |            No             |
| `-d` (debug)        | Active les traces de debug. Répéter l'option plusieurs fois <br> pour avoir plus de traces.                                                                                                                                     | `false` |            No             |
| `-P` (parallel)     | S'il y a plusieurs fichiers sources, lance la compilation <br> des fichiers en parallèle (pour accélérer la compilation)                                                                                                        | `false` |            No             |
| `-arm` (ARM)        | Génère du code assembleur qui sera executable sur un <br> processeur ARM-v6                                                                                                                                                     | `false` |            No             |
| `<file>.deca`       | Liste des fichiers deca qui vont être compilés (Si le nom <br> file revient plusieurs fois, il ne sera compilé qu'une <br> seule fois                                                                                           | `empty` | Yes <br> (except if `-b`) |

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- TESTS -->

## Tests

L'intégralité de nos tests se situe dans le répertoire src/tests/.
Celui-ci contient des tests java, des tests deca et des scripts de tests.
Les tests deca sont ordonnés de la manière suivante : `[partie]/[valid | invalid]/tests`, avec

1. [partie] = syntax pour les tests qui verifient la validité de la syntaxe du langage,
2. [partie] = context pour les tests qui verifient la validité de la vérification contextuelle,
3. [partie] = codegen pour les tests globaux qui visent a etre compilés entièrement.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Scripts de tests

Les scripts de tests sont multiples, et nommés de la forme : `test-[partie]-[complet | valid | invalid].sh`.
Une fois le script exécuté, il va vérifier pour la [partie] du langage concernée tous les tests soit valides, soit invalides, soit les deux.

Il existe également d'autres fichiers de scripts bash, comme `basic-decac.sh` qui permet de vérifier que les commandes decac spécifiées <a href="#utilisation">ici</a> ont un bon fonctionnement.

<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Tests manuels

Des launchers de tests manuels sont aussi présents.

1. `test_lex` permet d'afficher a l'ecran le resultat de l'analyse lexicale du fichier deca
2. `test_synt` permet d'afficher a l'ecran le resultat de l'analyse syntaxique du fichier deca
3. `test_decompile` permet d'afficher a l'ecran le resultat de la décompilation qui suit l'analyse syntaxique du fichier deca
4. `test_context` permet d'afficher a l'ecran le resultat de l'analyse contextuelle du fichier deca
5. `test_ima` permet de lancer a la suite la commande decac et ima sur le fichier passé en paramètre
6. `test_arm` permet de lancer a la suite toutes les commandes nécessaires pour compiler puis executer executer un fichier deca avec l'option -arm. Ce launcher nécessice une installation préalable de packages qui permettent de simuler une Rasberry PI 2 (ARM v6).

Il existe aussi des programmes deca de tests interactifs, se situant dans `src/test/codegen/interactive`, tels qu'un jeu de morpion ou de puissance 4.

<p align="right">(<a href="#readme-top">back to top</a>)</p>
