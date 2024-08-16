# quarkus-first-app
## First Application Quarkus
### hello endpoint rest, Compilation + packaging + run : jvm et native

- Démarrage de l'app en mode dev
  - mvn clean compile quarkus:dev
- Création de l'executable jar 
  - mvn clean package
- Execution en mode JVM
  - java -jar target/quarkus-app/quarkus-run.jar

- Creation de l'executable native (necessite l'install de GraalVM)
  - mvn clean package -Pnative
- Création de l'executable native Linux (necessite docker, pas besoin de GraalVM)
  - mvn clean package -Dnative -Dquarkus.native.container-build=true
- Packaging de l'app native dans une image docker
  - docker build -f src/main/docker/Dockerfile.native -t first-app-native:first-app-1.0.0-SNAPSHOT .
- Packaging de l'app jvm dans une image docker
  - docker build -f src/main/docker/Dockerfile.jvm -t first-app-jvm:first-app-1.0.0-SNAPSHOT .
- Execution de l'app
  - jvm : docker run -i --rm -p 8080:8080 first-app-jvm:first-app-1.0.0-SNAPSHOT
  - native : docker run -i --rm -p 8080:8080 first-app-native:first-app-1.0.0-SNAPSHOT
