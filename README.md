# simple-blog

This is a simple blog created using Spring Boot 2, JDK 11, that uses Postgres SQL

### How to run

**1. Create Postgres instance:**

docker run --name my-postgres -p 5433:5432 -e POSTGRES_PASSWORD=mysecretpassword -d postgres


**2. Setup Postgres instance:**

Create DATABASE blog;


**3. Jump to project folder**


**4. Run commands:**

./gradlew bootJar

java -jar build/libs/simple-blog-0.0.1-SNAPSHOT.jar

### Development
The Exposed API can be viewed here: http://localhost:8180/swagger-ui.html