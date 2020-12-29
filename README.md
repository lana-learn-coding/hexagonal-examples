# Hexagonal Examples

Examples of Hexagonal Architecture across various framework using with shared core domain logic


## Running the application in dev mode
Running Quarkus app on Port: 8080
```shell
./gradlew :library-quarkus:quarkusDev #running quarkus application
```
Running Srping app on Port: 8080
```shell
./gradlew :library-spring:bootRun #running spring application
```

Note: Spring App can start directly from IDE by running LibraryApplication class, but Quarkus can't due to an integration
problem of Quarkus and Gradle.