# Feed Generator for the Exchange Service

A random feed generator to simulate the exchange service.

**Pre-requisites**: Install Apache Kafka and include the `/bin` in `$PATH`.

```
$ zookeeper-server-start.sh ~/share/kafka_2.13-2.6.0/config/zookeeper.properties
$ kafka-server-start.sh ~/share/kafka_2.13-2.6.0/config/server.properties
$ kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092
$ kafka-console-consumer.sh --topic orders --bootstrap-server localhost:9092
```

Generate Java classes for the proto files:
```
$ ./gradlew app:generateProto
```

Run the data feed generator
```
$ ./gradlew datagen
```
