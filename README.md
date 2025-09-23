# quarkus-person
## First Application Quarkus
### 🔖 Application Quarkus REST + DB (MySQL) + Properties + Timer

Cette démo comporte 2 branches Git :
- 🌿 **main** :
  - contient le code source de l'application et permet simplement de démarrer et tester l'application
- 🌿 **performance_testing** :
  - contient en plus les fichiers docker permettant de builder l'application dans le différentes configurations de tests
  - contient également les fichiers hyperfoil permettant de lancer les tests de performances


Prérequis : java 17 minimum et docker desktop version 20 minimum

- Démarrage de l'app en mode dev
```shell
mvn clean compile quarkus:dev
```

- Création de l'executable jar
```shell
mvn clean package
```

- Execution en mode JVM
```shell
java -jar target/quarkus-app/quarkus-run.jar
```

- Creation de l'executable native (necessite l'install de GraalVM)
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