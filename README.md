# quarkus-first-app
## First Application Quarkus
### hello endpoint rest, Compilation + packaging + run : jvm et native

Prérequis : java 17 minimum et docker desktop

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
docker build -f src/main/docker/Dockerfile.native -t first-app-native:first-app-1.0.0-SNAPSHOT .
```

- Packaging de l'app jvm dans une image docker
```shell
docker build -f src/main/docker/Dockerfile.jvm -t first-app-jvm:first-app-1.0.0-SNAPSHOT .
```

- Execution de l'app
  - jvm :
```shell
docker run -i --rm -p 8080:8080 first-app-jvm:first-app-1.0.0-SNAPSHOT
```
  - native :
```shell
docker run -i --rm -p 8080:8080 first-app-native:first-app-1.0.0-SNAPSHOT
```

- Test de l'app :
```shell
curl http://localhost:8080/hello
```

## Perfomance testing avec Siege
Installation de siege :
- https://github.com/ewwink/siege-windows
- https://github.com/JoeDog/siege
- https://hub.docker.com/r/yokogawa/siege

```shell
siege -t10S -c100 http://localhost:8080/hello
```

## Performance testing avec hyperfoil
- https://hyperfoil.io
```shell
docker run -it --rm -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil:/benchmarks:Z -v /Users/fredericmencier/Projects/quarkus-first-app/hyperfoil/reports:/tmp/reports:Z quay.io/hyperfoil/hyperfoil cli
start-local
upload /benchmarks/first-app-hello.yml
run hello-benchmark
stats
report --destination=/tmp/reports
```