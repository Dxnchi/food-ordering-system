# Food Ordering System

## Part 4: Project Investigation

**1. What is Spring Boot?**
Spring Boot is an extension of the Java Spring framework that eliminates the need for complex XML configurations. It provides pre-configured defaults and an embedded web server to quickly build production-ready applications.

**2. What is Maven?**
Maven is a build automation and project management tool. It standardizes the project structure and automatically downloads and manages all external libraries required to run the application.

**3. What is the purpose of pom.xml?**
The Project Object Model (POM) file is the blueprint of a Maven project. It contains project metadata and the list of dependencies (libraries) the application needs to compile and run.

**4. What is the purpose of application.properties?**
This file is the central configuration hub for the application. It is used to define environment-specific settings like database connection URLs, server ports, and security credentials, keeping them separate from the Java code.

**5. What does @SpringBootApplication do?**
It is a core annotation that triggers three features: it flags the class as a configuration source, tells Spring to automatically configure the application based on the included libraries, and scans the project for other components and services to register.

**6. Why do developers use dependency management tools such as Maven?**
These tools prevent version conflicts and the manual management of JAR files. They automatically fetch the correct library versions from a central repository, ensuring the project builds consistently across different environments.

**7. What is a REST API?**
Representational State Transfer (REST) is an architectural style for APIs. It allows different software systems to communicate over the internet using standard HTTP methods like GET, POST, PUT, and DELETE.

**8. What is JSON?**
JavaScript Object Notation (JSON) is a lightweight, human-readable data format used to transmit information between a server and a web application.

**9. What is Dependency Injection?**
It is a design pattern where an object receives its required dependencies from an external framework (like Spring's IoC container) rather than creating them itself. This makes the code modular, loosely coupled, and easier to test.

---

## Part 5: Package Structure

* **controller:** The entry point for the API. It intercepts incoming HTTP requests, routes them to the correct service, and returns the HTTP response.
* **service:** Contains the core business logic. It processes rules and calculations before interacting with the database.
* **repository:** The data access layer. It interfaces with the database to perform CRUD operations using Spring Data JPA.
* **entity:** Contains Java classes that map directly to database tables.
* **dto:** Data Transfer Objects. These are simple objects used to pass data between layers without exposing the internal database entities to the outside world.
* **config:** Holds configuration classes for custom application settings, such as security rules or CORS policies.
* **exception:** Houses custom error-handling logic to ensure the API returns standardized, clean error messages when a failure occurs.