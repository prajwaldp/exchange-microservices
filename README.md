# Microservices System Design

Independent microservices for a blogging (or any social CRUD) application.

## Architecture

![architecture](assets/architecture.png)

## Prerequites

1. Apache Kafka

```sh
~ ./$KAFKA_BIN/bin/zookeeper-server-start.sh ./$KAFKA_BIN/config/zookeeper.properties
~ ./$KAFKA_BIN/bin/kafka-server-start.sh ./$KAFKA_BIN/config/server.properties
```

2. Cassandra

```sh
~ cassandra -f
```

Create the default keyspace

```sh
~ cqlsh
```

```sql
CREATE KEYSPACE mykeyspace WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
USE mykeyspace;
```

3. Compile all sub-projects

```sh
~ mvn clean compile
```

This should also build Docker containers for all the services

4. Start all the services

```sh
~ docker-compose up -d
```
