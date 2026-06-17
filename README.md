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

**6. Why do developers use dependency management tools such as
