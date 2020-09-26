# ClickStream Analytics Service

## Clickstream Simulator

A clickstream can be simulated to test and demo the real-time analytics engine.

The Wikipedia clickstream datasets that can be found
[here](https://dumps.wikimedia.org/other/clickstream/).

The exact dataset used is
[this one](https://dumps.wikimedia.org/other/clickstream/2020-08/clickstream-enwiki-2020-08.tsv.gz).

It is placed in the `data` folder.

### Steps to run the simulation

1. Start ZooKeeper (A Distributed System Coordinator)
2. Start Apache Kafka server
3. Create the `clickstream-events` topic

```bash
$ ./services/kafka_2.13-2.6.0/bin/kafka-topics.sh --create \
--topic clickstream-events \
--bootstrap-server localhost:9092
$ ./services/kafka_2.13-2.6.0/bin/kafka-topics.sh --list \
--bootstrap-server localhost:9092
```

4. Start the application

```bash
$ mvn clean compile
$ mvn package
$ java -jar target/clickstreamproducer-0.1.0.jar
```
