# Spring Boot Employee API

This standalone Spring Boot project lives inside the `Java_1` learning
repository. It includes both `pom.xml` and `build.gradle`, so the same source
can be built with Maven or Gradle for the build-tool assignments.

The separate beginner fundamentals and annotations assignment is in
`assignments/spring-boot-fundamentals/`. It runs on port `8081` and documents
how to test `/hello` and `/employee` with Postman and verify its database table.

## Run with IntelliJ

Open this folder as a project, wait for IntelliJ to import Maven or Gradle,
then run `EmployeeApiApplication`. The default profile uses an in-memory H2
database, so MySQL is not required to try the HTTP requests. Data is cleared
when the application stops.

## Run from a terminal

From this directory, use either installed build tool:

```text
mvn spring-boot:run
```

or:

```text
gradle bootRun
```

The application listens on `http://localhost:8080`, matching
`../../database-http/assignments/http/EmployeeApiRequests.http`.

Maven build exercises:

```text
mvn clean
mvn compile
mvn test
mvn package
```

Gradle build exercises:

```text
gradle clean
gradle test
gradle build
gradle bootRun
```

The packaged JAR is under `target/` for Maven and `build/libs/` for Gradle.
The Maven and Gradle dependency declarations use the same coordinates:

| Dependency | Group ID | Artifact ID |
|---|---|---|
| Spring MVC / embedded server | `org.springframework.boot` | `spring-boot-starter-web` |
| JPA persistence | `org.springframework.boot` | `spring-boot-starter-data-jpa` |
| MySQL JDBC driver | `com.mysql` | `mysql-connector-j` |
| Local default database | `com.h2database` | `h2` |

The MySQL driver is included for the database assignment. To run against your
local MySQL server, make sure the `employee_db` database exists and enter your
MySQL username and password in the root `.env` file. The `.env` file is ignored
by Git so your password is not committed. Start the app with the `mysql` profile:

```text
mvn spring-boot:run -Dspring-boot.run.profiles=mysql
```

or:

```text
gradle bootRun --args='--spring.profiles.active=mysql'
```

With the Gradle wrapper on Windows, run:

```text
.\gradlew.bat bootRun --args="--spring.profiles.active=mysql"
```

The MySQL profile creates or updates the `employees` table in `employee_db`.

The API supports `GET`, `POST`, `PUT`, `PATCH` (salary only), and `DELETE`
under `/api/employees`. It validates request fields and returns `404` for a
missing ID and `409` for a duplicate email.
