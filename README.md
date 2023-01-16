# Projet Génie Logiciel, Ensimag.

gl39, 01/01/2023.

### Pour lancer JaCoCo

version simple qui marche:

`mvn clean`
`mvn -Djacoco.skip=false verify`

version compliqué qui marche 1 fois sur 10:

`mvn clean`
`mvn compile`
`mvn jacoco:instrument`
`mvn test`
`mvn jacoco:restore-instrumented-classes`
`mvn jacoco:report`
`firefox target/site/index.html`

###Pour lancer les tests
Nous avons fait des scripts shell qui lancent tous nos tests, ils sont dans le repertoire `src/test/script`. Les permissions d'éxecution leurs sont accordées au lancement de la commande `mvn compile`. Tous les tests sont éxécutés au lancement de la commande `mvn test`.

###Pour lancer le compilateur
Il suffit d'écrire la commande `decac` qui spécifie les options présentes pour notre compilateur.

###Non implémenté actuellement
L'option -P du compilateur n'est pas encore fonctionelle.
Une erreur n'est pas levé lors d'un arrondi à 0 ou à l'infinie pour toutes les opérations arithmétiques.
La partie avec objet n'est que partiellement implementée.

###Démonstration
Nous avons codé un morpion en déca qui est jouable en tapant la suite de commande suivante:
