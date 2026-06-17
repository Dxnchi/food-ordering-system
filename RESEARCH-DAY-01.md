Section 2\
Q1- CRUD= Create,read,update and delete
Q2- Methods are Post(create a new source), PUT(Used to replace an entire source), Patch(used to update partially a source), delete(Used to remove a source).
Q3- status codes= a. created =201 created, b. deleted successfully 204, c.does not exist 404, d. missing field 400, e. logged in but not allowed 403
Q4- Requestbody tells spring to take the JSON body of the http request and map it to a java object, Pathvariable extracts variables from the url path, request param extracts values from the query string after ?
Q5- Jakarta bean validation, it is a standard framework in java for ensuring data is correct before processing it. valid tells spring to trigger the checks, notblank ensures string isn't empty, size restricts the character length.
Q6-security and privacy the database entity might contain sensitive information clients shouldn't see and stability it provides stability by acting as a buffer if database is changed and you dont want toi break the api contract.
Q7- t is a container object that may or may not contain a value. findById returns it because you might ask for ID 99, but it doesn't exist in the database. It forces the developer to handle the "not found" scenario instead of the code just crashing with a NullPointerException

Section 9
Q1- Returning just an object defaults to a 200 OK status. it allows allows you to take full control of the HTTP response.
Q2- 204 No Content. The operation was successful, but since the resource is destroyed, there is nothing to return.
Q3- PATCH. PUT is strictly defined as replacing the entire resource. PATCH is designed specifically for partial updates.
Q4- Forgot @Valid? Spring will completely ignore your @NotBlank and @Size annotations. Bad data will pass straight through to your database and likely crash it.
Q5-To update or delete, the server must know which specific resource to target among thousands. When creating (POST), the resource doesn't exist yet, so it doesn't have an ID to put in the URL.