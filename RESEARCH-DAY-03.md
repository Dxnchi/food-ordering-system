Q1 JPA is a set of rules  that governs how object relational mapping should work its just an interface, Hibernate is the actual code and relationship is the rules for jpa.

Q2- entity tells spring to trat the class as a database object and table tells hibernate what  the table should be named in MySQL database.

Q3- A foreign key is a column created from the primary key of another table creating a relationship, MabYToOne is basically defining the relationships.

Q4- It explicitly tells Hibernate to look i for a column named category_id and use that to link categories as the foreign key

Q5- double is a floating-point number, meaning it sacrifices exact precision for speed. When dealing with currency , double can cause rounding errors . BigDecimal guarantees 100% mathematical precision.

Q6- eager hibernate will run a query to fetch category at the same time, lazy= hibernate queries the menu only until you call, the default for OneToMany is eager

Q7- It is a massive performance killer.

Q8- dependency injection  means providing a class with tools needed from the outside, constructor injection is preferred because it allows dependencies to be marked final and makes tests easier.

Q9- it writes a constructor automatically and includes every field that was marked. its the cleanest way and helps avoid boilerplates

Q10- The Service layer holds your core business logic . It must be separate from the Controller so the Controller can focus strictly on handling HTTP requests and responses. This separation also allows other parts of the application to reuse the Service logic.

Q11- If you try to save a Menu with a categoryId of 99, but Category 99 doesn't exist in the database, MySQL will violently reject it and throw a DataIntegrityViolationException. Validating it first allows you to cleanly return a 404 Not Found response instead of crashing.

Q12- save(0 adds  to the hibernate queue and saveandfluch() bypasses queue and forces hibernate to write to MySQL.

Q13- o keep the Service layer code clean and readable. Extracting the mapping logic into a private helper method makes the main methods  easier to understand at a glance


self quiz

Q1- Adding it creates a "bidirectional" relationship. When converting the data to JSON, the Category would load its Menus, and the Menus would load their Category, creating an infinite loop that crashes the application.

Q2- It tells Hibernate to completely delete the database schema when the application shuts down. You would only use this during automated integration testing so every test starts with a completely blank slate.

Q3- The database will throw a foreign key constraint violation and block the deletion. You cannot delete a parent if the children still exist and are pointing to it.

Q4- Because BigDecimal prevents the microscopic mathematical rounding errors inherent to floating-point (double) architecture, ensuring financial calculations are completely accurate.