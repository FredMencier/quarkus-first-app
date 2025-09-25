# quarkus-person
## First Application Quarkus
### 🔖 Application Quarkus REST + DB (MySQL ou H2) + Properties + Timer

Cette démo comporte 2 branches Git :
- 🌿 **main** :
  - contient le code source de l'application et permet simplement de démarrer et tester l'application
- 🌿 **performance_testing** :
  - contient en plus les fichiers docker permettant de builder l'application dans le différentes configurations de tests
  - contient également les fichiers hyperfoil permettant de lancer les tests de performances

Prérequis : java 17 minimum et docker desktop version 20 minimum

---
Comparer les temps de startup selon les différentes configurations

- build + run en mode JVM quarkus:dev
- build + run en mode JVM avec un fat jar
- build + run en mode natif avec GraalVM

---

📌 Tableau récapitulatif des temps de démarrage

| Configuration       | Start Time        | Taille du livrable |
|---------------------|-------------------|--------------------|
| JVM quarkus:dev     | started in 1.998s | NA                 |
| JVM avec un fat jar | started in 1.433s | 696 octets         |
| Natif avec GraalVM  | started in 0.121s | 131.8 Mo           |
| docker JVM          |                   |                    |

---
Par défaut, l'application utilise H2

Utilisation de la base MySql avec le profile : __-Dquarkus-profile=mysql__

---

## Application Quarkus en mode JVM quarkus:dev

- Build de l'application
  ```shell
  mvn clean package
  ```
- Run de l'application
  ```shell
  mvn quarkus:dev
  ```

## Application Quarkus en mode JVM avec un fat jar

- Build de l'application
  ```shell
  mvn clean package
  ```

- Run de l'application
  ```shell
  java -jar target/quarkus-app/quarkus-run.jar
  ```

## Application Quarkus en mode natif avec GraalVM (necessite GraalVM installé)

Ajouter le profile __native__ dans le __pom.xml__

```xml
    <profiles>
        <profile>
            <id>native</id>
            <activation>
                <property>
                    <name>native</name>
                </property>
            </activation>
            <properties>
                <skipITs>false</skipITs>
                <quarkus.native.enabled>true</quarkus.native.enabled>
                <quarkus.native.native-image-xmx>8g</quarkus.native.native-image-xmx>
            </properties>
        </profile>
    </profiles>
```

- Vérification avant le build :

  ```shell
  mvn -version
  ```

  ```log
  Apache Maven 3.9.6 (bc0240f3c744dd6b6ec2920b3cd08dcc295161ae)
  Maven home: /Users/fredericmencier/Projects/apache-maven-3.9.6
  Java version: 21.0.8, vendor: Oracle Corporation, runtime: /Users/fredericmencier/.sdkman/candidates/java/21.0.8-graal
  Default locale: fr_FR, platform encoding: UTF-8
  OS name: "mac os x", version: "14.4.1", arch: "aarch64", family: "mac"
  ```

- Build de l'application
  ```shell
  mvn clean package -Pnative
  ```
  






- Création de l'executable native Linux (necessite docker, pas besoin de GraalVM)
```shell
mvn clean package -Dnative -Dquarkus.native.container-build=true
```

- Packaging de l'app native dans une image docker
```shell
docker build -f src/main/docker/Dockerfile.native -t person-app-native:quarkus-person-app-1.0.0-SNAPSHOT .
```

- Packaging de l'app jvm dans une image docker
```shell
docker build -f src/main/docker/Dockerfile.jvm -t person-app-jvm:quarkus-person-app-1.0.0-SNAPSHOT .
```

- Execution de l'app
  - jvm :
```shell
docker run -i --rm -p 8080:8080 person-app-jvm:quarkus-person-app-1.0.0-SNAPSHOT
```
- native :
```shell
docker run -i --rm -p 8080:8080 person-app-native:quarkus-person-app-1.0.0-SNAPSHOT
```

- Test de l'app :
```shell
curl http://localhost:8080/persons
```

## Perfomance testing avec Siege
Installation de siege :
- https://github.com/ewwink/siege-windows
- https://github.com/JoeDog/siege
- https://hub.docker.com/r/yokogawa/siege

```shell
siege -t10S -c100 http://localhost:8080/persons
```

## Performance testing avec hyperfoil
- https://hyperfoil.io

Utilisation en mode cli :
```shell
docker run -it --rm -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil:/benchmarks:Z -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil/reports:/tmp/reports:Z quay.io/hyperfoil/hyperfoil cli
start-local
upload /benchmarks/personsBenchmark.yml
run persons-benchmark
stats
report --destination=/tmp/reports
```

Utilisation avec un script complet :
```shell
./hyperfoil/startHyperfoil.sh
```