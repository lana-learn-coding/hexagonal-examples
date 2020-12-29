# Hexagonal Examples

Examples of Hexagonal Architecture across various framework using with shared core domain logic


## Running the application in dev mode
Running Quarkus app
```shell
./gradlew :library-quarkus:quarkusDev
```
Running Spring app
```shell
./gradlew :library-spring:bootRun
```
Application default port is 8080. You can access the swagger ui at [/docs/swagger](http://localhost:8080/docs/swagger)

Note: Spring App can start directly from IDE by running LibraryApplication class, but Quarkus can't due to an integration
problem of Quarkus and Gradle.