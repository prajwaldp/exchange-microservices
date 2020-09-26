# Content Management Service

A Java/Spring microservice with RESTful APIs to perform CRUD operations on one resource (articles in this codebase, but can be expanded to any posts, products, tickets, etc.)

## API

- `GET /articles` - Get all articles
- `POST /articles` - Create a new article
- `GET /articles/:id` - Get article with ID 1
- `PUT /articles/:id` - Modify article with ID 1
- `DELETE /articles/:id` - Delete article with ID 1

## Database Setup

The content-service is built with Spring Data Cassandra integrated (for its high availability, fault tolerance, and performance, assuming the application has to handle a large amount of content).

```sh
$ cassandra -f # Starts cassandra as a foreground process
```

Create the default keyspace

```sh
$ cqlsh
```

```sql
CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE mykeyspace;
```

## Running the service

```sh
$ ./mvn compile
$ ./mvn spring-boot:run
```
