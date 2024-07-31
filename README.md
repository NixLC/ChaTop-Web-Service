# Rentals Service

## Description
This is a rental management web service

## Requirements
### Execution environment
Java 17 JRE\
MariaDB or other compatible database (see [parameters](#datasource_driver))

### Initialize Database
- As a privileged user, use `rentals-service-schemas.sql` to create the database schema before running the app.
- Create a database user to access the newly created schema (you can adapt `db-user-create.sql`)
- Then, you might use `rentals-service-sample-data.sql` to insert some sample data to play with.

### Security Notice
**Important:** For security reasons, do not use the default username, password and schema name provided in provided scripts.\
Always check sql scripts before running them.
Ensure you create a unique and strong username and password for the database user and use a custom schema name.\
Additionally, review and adjust the default permissions to follow the principle of least privilege.
**Default values provided in those scripts should be used for testing only !**

## Run commands
Before executing any of the commands below, make sure you are in the root directory of the application.\
```
cd rentals-service
```

## Build
Package the application with the provided maven wrapper executable :

Linux :
```
./mvnw package
```
Windows:
```
mvnw.cmd package
```
The executable JAR file will be put inside `target` directory

## Run

### Parameters
All parameters listed below are mandatory for the application to work :

| Parameter                                                          | Description                                                                         | Example                                                            |
|--------------------------------------------------------------------|-------------------------------------------------------------------------------------|--------------------------------------------------------------------|
| `--security.jwt.secret_key`                                        | HMAC secret key used for signing JWT tokens                                         | `c55272a8769cb4a7d777616d72e7835c7364f472fdecf1a264c36e22a802b9a3` |
| `--security.jwt.expiration_time`                                   | JWT token expiration time (in milliseconds)                                         | `86400000`                                                         |
| <a id="datasource_driver"/>`--spring.datasource.driver-class-name` | Database driver class name (should match your DBMS)                                 | `org.mariadb.jdbc.Driver`                                          |
| `--spring.datasource.url`                                          | Database connection URL                                                             | `jdbc:mariadb://localhost:3306/rentals`                            |
| `--spring.datasource.username`                                     | Username for the database connection                                                | `rentals`                                                          |
| `--spring.datasource.password`                                     | Password for the database connection                                                | `rentals`                                                          |
| `--snx.app.host`                                                   | Host where the application is deployed                                              | `localhost`                                                        |
| `--snx.app.protocol`                                               | Protocol used by the application                                                    | `http`                                                             |
| `--server.port`                                                    | Port on which the application listens                                               | `3001`                                                             |
| `--snx.app.upload_dir`                                             | Directory for storing uploaded files<br>(must be an absolute path, no trailing `/`) | `/var/www-data/rentals-service/uploads`                            |

### Important Information

- To generate a HMAC secret key, you can use a tool like [freeformatter.com](https://www.freeformatter.com/hmac-generator.html). Ensure that your secret key is long and random to enhance security.
- The upload directory specified by `--snx.app.upload_dir` must be writable by the user running the application. Ensure the directory has the appropriate permissions set.

### Example
A valid HMAC secret key is given here **as an example** to run the application. **Do not use in production environment.**

```
java -jar target/rentals-service-1.0.0.jar \
--security.jwt.secret_key=c55272a8769cb4a7d777616d72e7835c7364f472fdecf1a264c36e22a802b9a3 \
--security.jwt.expiration_time=86400000 \
--spring.datasource.driver-class-name=org.mariadb.jdbc.Driver \
--spring.datasource.url=jdbc:mariadb://localhost:3306/rentals \
--spring.datasource.username=rentals_admin \
--spring.datasource.password=your_secure_password_here \
--snx.app.host=localhost \
--snx.app.protocol=http \
--server.port=3001 \
--snx.app.upload_dir=/var/www-data/rentals-service/uploads
```

## Make requests

The application should be up and running, some OpenApi documentation is available at the specified protocol://host:port/swagger-ui.html.
For the example above, it would be [http://localhost:3001/swagger-ui.html](http://localhost:3001/swagger-ui.html)

### Application users
With the provided sample data, you will be able to log in to the application with the following credentials :

| Username               | Password |
|------------------------|----------|
| test@test.com          | test!31  |
| john.smith@bigmail.com | john     |

Use those credentials to get a JWT token and access authorized routes.