# RabbitMQ Spring Boot Demo

A complete **Spring Boot + RabbitMQ** demonstration project implementing reliable asynchronous messaging patterns, observability, and containerized deployment using Docker Compose.

## Overview

This project demonstrates how to build an event-driven backend application using:

* Java 21
* Spring Boot 3
* RabbitMQ
* Spring AMQP
* H2 Database
* Prometheus
* Grafana
* Docker & Docker Compose

The application exposes REST APIs that publish events through RabbitMQ while implementing reliable messaging patterns such as retries, dead-letter queues, manual acknowledgments, and the Outbox Pattern.

---

# Architecture

```
                  +------------------+
                  |  REST Controller |
                  +--------+---------+
                           |
                           v
                  +------------------+
                  |   Service Layer  |
                  +--------+---------+
                           |
                           v
                  +------------------+
                  | Outbox Pattern   |
                  +--------+---------+
                           |
                           v
                  +------------------+
                  |    RabbitMQ      |
                  +--------+---------+
                           |
          +----------------+----------------+
          |                                 |
          v                                 v
    Email Consumer                   Audit Consumer
          |                                 |
          +---------------+-----------------+
                          |
                          v
                     Dead Letter Queue

Monitoring

Spring Boot
      |
      v
 Prometheus
      |
      v
  Grafana
```

---

# Features

* REST API
* RabbitMQ Exchanges
* Direct Exchange
* Dead Letter Exchange (DLX)
* Dead Letter Queue (DLQ)
* Retry Queues
* Manual Acknowledgment
* Publisher Confirms
* Outbox Pattern
* H2 Database
* Spring Actuator
* Prometheus Metrics
* Grafana Dashboard
* Docker Compose Environment
* Spring Profiles (Local / Docker)

---

# Technologies

| Technology  | Version |
| ----------- | ------- |
| Java        | 21      |
| Spring Boot | 3.x     |
| RabbitMQ    | 3.13    |
| Maven       | 3.x     |
| Docker      | Latest  |
| Prometheus  | Latest  |
| Grafana     | Latest  |
| H2 Database | Latest  |

---

# Project Structure

```
src
 ├── controller
 ├── service
 ├── config
 ├── consumer
 ├── publisher
 ├── entity
 ├── repository
 ├── dto
 └── scheduler
```

---

# Spring Profiles

The project supports two execution profiles.

## Local

Runs directly from the IDE.

```
application.yml
application-local.yml
```

Activate:

```
spring.profiles.active=local
```

---

## Docker

Runs entirely inside Docker Compose.

```
application.yml
application-docker.yml
```

Activate:

```
SPRING_PROFILES_ACTIVE=docker
```

---

# Running Locally

Build the project:

```bash
mvn clean package
```

Run:

```bash
mvn spring-boot:run
```

---

# Running with Docker

Build and start the environment:

```bash
docker compose up --build
```

Stop:

```bash
docker compose down
```

---

# Available Services

| Service             | URL                    |
| ------------------- | ---------------------- |
| Spring Boot         | http://localhost:8080  |
| RabbitMQ Management | http://localhost:15672 |
| Prometheus          | http://localhost:9090  |
| Grafana             | http://localhost:3001  |

---

# Example Request

```
POST /orders
```

```json
{
  "orderId": 1002,
  "customerName": "Ricardo Hernandez",
  "totalAmount": 550000
}
```

---

# Monitoring

The application exposes Prometheus metrics through:

```
/actuator/prometheus
```

Grafana dashboards can be configured to visualize:

* JVM Metrics
* HTTP Requests
* RabbitMQ Metrics
* Spring Boot Metrics
* Custom Business Metrics

---

# Future Improvements

* Kubernetes Deployment
* Helm Charts
* PostgreSQL
* Testcontainers
* OpenTelemetry
* Distributed Tracing
* CI/CD with GitHub Actions
* SonarQube Integration
* Jaeger
* ELK Stack
* RabbitMQ Cluster

---

# Author

**Edgar Ricardo Hernández Fonseca**

Backend Developer

Java | Spring Boot | REST APIs | AWS

LinkedIn: *https://linkedin.com/in/edgar-ricardo-hernandez-fonseca*

GitHub: *https://github.com/EdgarRicardoHernandezFonseca*

---

# License

This project is available for educational and demonstration purposes.
