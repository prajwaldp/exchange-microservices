# ClickStream Analytics Service

## Instructions

1. Start ZooKeeper and Apache Kafka.

```sh
$ ./$KAFKA_BIN/bin/zookeeper-server-start.sh ./$KAFKA_BIN/config/zookeeper.properties
$ ./$KAFKA_BIN/bin/kafka-server-start.sh ./$KAFKA_BIN/config/server.properties
```

2. Write the clickstream into the `clickstream` Kafka topic.

3. Compile and run the Spark application using `sbt`

```sh
$ cd stream-analytics-engine
$ sbt compile
$ sbt run
```

## Generate a simulated clickstream

1. Compile the `clickstream-simulator` application using maven:

```sh
$ cd clickstream-simulator
$ mvn clean compile
$ mvn package
```

2. Download a clickstream dataset from [here](https://dumps.wikimedia.org/other/clickstream/). I used [this](https://dumps.wikimedia.org/other/clickstream/2020-08/clickstream-enwiki-2020-08.tsv.gz) one.

3. Create a `clickstream` topic to write to.

```sh
$ ./$KAFKA_BIN/bin/kafka-topics.sh --create \
--topic clickstream \
--bootstrap-server localhost:9092

$ ./$KAFKA_BIN/bin/kafka-topics.sh --list \
--bootstrap-server localhost:9092
```

4. Start a console consumer

```sh
$ ./$KAFKA_BIN/bin/kafka-console-consumer.sh --topic clickstream \
--from-beginning \
--bootstrap-server localhost:9092
```

5. Start the clickstream simulator

```sh
$ java -jar target/clickstreamproducer-0.1.0.jar
```
