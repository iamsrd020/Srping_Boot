# Spring Boot Fundamentals — Mini Assignment

This is a separate beginner exercise. It has its own `pom.xml` and
`build.gradle`, and uses port `8081`, so it can run alongside the employee API
on port `8080`. Its database table is `fundamentals_employees`; it does not
change the existing `employees` table.

## 1–2. Create the project with Spring Initializr

At [start.spring.io](https://start.spring.io), choose:

| Setting | Selection |
|---|---|
| Project | Maven |
| Language | Java |
| Spring Boot | 3.4.x |
| Group | `com.java1` |
| Artifact | `spring-boot-fundamentals` |
| Packaging | JAR |
| Java | 17 |
| Dependencies | Spring Web, Spring Data JPA, Validation, MySQL Driver |

This folder contains the equivalent generated project plus a Gradle build file
for practice. The main class is
`src/main/java/com/java1/fundamentals/FundamentalsApplication.java`. Its
`@SpringBootApplication` annotation enables configuration, component scanning,
and Spring Boot auto-configuration.

## 3–5. Configure and run

`src/main/resources/application.properties` sets `server.port=8081`. It also
configures the MySQL connection. The project reads the root `.env` file at
`../../.env`; fill in `DB_USERNAME` and `DB_PASSWORD` there. Make sure MySQL is
running and that the `employee_db` database exists.

From the repository root, run the app with the existing Gradle wrapper:

```powershell
.\gradlew.bat -p assignments\spring-boot-fundamentals bootRun
```

Or, from this assignment folder, if Maven is installed:

```powershell
mvn spring-boot:run
```

The console should report that `FundamentalsApplication` started and that the
server is listening on port `8081`. Stop it with Ctrl+C.

## 6. Test the endpoints with Postman

With the app running, create these requests:

| Method | URL | Body |
|---|---|---|
| GET | `http://localhost:8081/hello` | None; response is `Hello Spring Boot` |
| GET | `http://localhost:8081/employee` | None; response is a JSON list |
| POST | `http://localhost:8081/employee` | Select **Body → raw → JSON** and send the JSON below |

```json
{
  "name": "Dboss",
  "email": "dboss-fundamentals@example.test"
}
```

The POST response is `Employee Created` with HTTP status `201 Created`. Use a
new email for each POST because the email column must be unique.

## Verify the saved employee in MySQL

Run these SQL statements in MySQL Workbench or the MySQL terminal:

```sql
USE employee_db;
SHOW TABLES LIKE 'fundamentals_employees';
SELECT * FROM fundamentals_employees;
SELECT * FROM fundamentals_employees
WHERE email = 'dboss-fundamentals@example.test';
```

The `fundamentals_employees` table is created/updated by JPA when the app
starts. `GET /employee` should return the same stored records. This assignment
only asks for GET and POST; it does not add update or delete endpoints.

## 7. Purpose of `pom.xml`

`pom.xml` is Maven's project/build configuration. It identifies the project,
lists dependencies (such as Spring Web and the MySQL driver), selects the Java
version, and configures build plugins. Maven uses it to resolve libraries,
compile, test, and package the application.

## 8. Purpose of `application.properties`

`application.properties` stores external application settings, separate from
Java code. Here it sets the server port, database connection, and JPA schema
behavior. Changing `server.port` changes the listening port without changing a
controller.

## 02. Spring Boot Annotations

The employee code is under
`src/main/java/com/java1/fundamentals/employee/`.

- `@RestController` marks `EmployeeController` as a web controller whose return
  values are written as response bodies (JSON for the employee list).
- `@GetMapping("/employee")` maps GET requests for that path to
  `getEmployees()`. `@PostMapping("/employee")` maps POST requests to
  `createEmployee()`.
- `@Service` marks `EmployeeService` as the application/business layer.
- `@Repository` marks `EmployeeRepository` as the persistence layer;
  `JpaRepository` provides common database operations.
- `@Autowired` asks Spring to provide the `EmployeeService` when it creates
  `EmployeeController`. Constructor injection makes the required dependency
  explicit.
- `@Component` marks `EmployeeUtility` as a Spring-managed helper; the
  `/hello` controller uses it to supply the greeting.

### GET request flow

```text
Postman
  -> GET /employee
  -> EmployeeController (@RestController, @GetMapping)
  -> EmployeeService (@Service)
  -> EmployeeRepository (@Repository / JpaRepository)
  -> MySQL fundamentals_employees table
  -> JSON response returned through the same layers
```

### 10. Which annotation makes a class Spring-managed?

`@Component` is the general annotation for a Spring-managed component.
`@Service`, `@Repository`, and `@Controller`/`@RestController` are specialized
component annotations for particular roles. Spring discovers them through
component scanning, which is enabled by `@SpringBootApplication`.
