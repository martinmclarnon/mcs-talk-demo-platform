# Demo e-commerce Platform

This project demonstrates a simple bookstore using Kotlin, Spring Boot, and JPA. 

## Table of Contents
- [Getting Started](#getting-started)
- [Requirements](#requirements)
- [API Overview](#api-overview)
- [Tiltfile Setup](#to-use-the-tiltfile)
- [Testing](#testing)
- [License](#license)

## Getting Started

At the current state of development the "team" have developed
- a web frontend,
- two PostgreSQL databases,
- one Mongo database,
- two query backend-for-frontend (bff) services,
  - one with a response of a book inventory list,
  - one with a response of blog entries that could be used by a feed,
- a "Create-Order-Command" service that will produce a message onto a topic, "Create-Order" in a Kafka message queue,
- a "Command-Event-Action-Audit" service that will listen to the "Create-Order" topic and write these messages to a Mongo database.

### Requirements

- Java 17 or later
- Kotlin 1.9.25 or later
- Spring Boot 3.3.4
- Maven Central for dependency management

### Installing Dependencies

The required dependencies are listed in the `build.gradle.kts` files and include some of the following key libraries:

- Spring Boot Starter Data JPA
- Spring Boot Starter Web
- Jackson Module Kotlin
- Cucumber for BDD testing
- JUnit for unit testing
- Mockito for mocking in tests

You can build individual projects and install dependencies with:

```bash
./gradlew build
```

### API Overview

```bash
GET /v1/vqjc110wa0tel38k75lw
```

This API endpoint returns a paginated list of blogs.

Parameters:
size (optional): Number of blog entries to return (default is 10).
page (optional): Page number to return (default is 0).

Response:
HTTP Status: 200 OK
Response Body: JSON object containing the list of blog posts.

```json
{
  "code": 200,
  "status": "OK",
  "data": [
    {
      "id": "12345",
      "post": "Sample blog post content"
    }
  ]
}
```

```bash
GET /v1/q91ts0d5hix47glh552o
```

This API endpoint returns a paginated list of books.

Parameters:
size (optional): Number of books to return (default is 10).
page (optional): Page number to return (default is 0).

Response:
HTTP Status: 200 OK
Response Body: JSON object containing the list of books.

```json
{
  "code": 200,
  "status": "OK",
  "data": [    
    {
        "id": "{{$randomUUID}}",
        "title": "Sample book title",
        "isbn": "Sample book isbn",
        "author": "Sample book author",
        "publisher": "Sample book publisher",
        "publishedDate": "Sample book published date",
        "numberOfPages": 100,
        "languageWrittenIn": "The language the Sample book is written in",
        "review": "Sample book review"
    }
  ]
}
```

```bash
POST /v1/x1wwg6t2hdc5ukvejdf5
```

This API endpoint accepts a request payload.

Request:

```json
{
  "bookId": "{{$randomUUID}}",
  "userId": "{{$randomUUID}}",
  "quantity" : "{{$randomInt}}",
  "orderDate" : "{{$randomDatePast}}"
}
```

This payload will be added to a Kafka queue and the create-order-audit application will write to a collection in a MongoDB

### Tiltfile Setup

To run local Kubernetes cluster in Docker:
```bash
$ brew install kind
```

To define your dev environment as code:
```bash
$ brew install tilt
```
#### Local CICD

Run the following commands:
```bash
$ kind create cluster --name mcs-talk-demo-platform
```

At the root of the Tiltfile. The argument --stream will output logs
```bash
$ tilt up --stream
```
To view Tilt dashboard, navigate to;
```bash
http://localhost:10350
```
```shell
$ curl http://localhost:8082/n86e5252c4bve2egkraw/v1/q91ts0d5hix47glh552o
```

```shell
$ curl http://localhost:8081/q824phylj42i77kdpvrq/v1/vqjc110wa0tel38k75lw
```

### Testing

The projects are configured for behaviour-driven development (BDD) using Cucumber and unit testing with JUnit. Mocking is handled by Mockito.

#### Running Unit Tests
To run the unit tests, use the following command:

```bash
$ ./gradlew test
```

#### Running BDD Tests
To execute the Cucumber tests, use:

```bash
$ ./gradlew execute-bdd-tests
```
This will run all feature files located in src/test/resources/features.

Tilt will manage the tests being run via the Dockerfiles.

### License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.