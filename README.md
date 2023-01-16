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
