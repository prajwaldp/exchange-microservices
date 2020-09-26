# ClickStream Data Simulator for Analytics

This application generates simulated clickstream from a text file.

## Set-up

Compile the application using maven:

```sh
$ mvn clean compile
$ mvn package
```

## Instructions to run the clickstream
1. Download a clickstream dataset from [here](https://dumps.wikimedia.org/other/clickstream/). I used [this](https://dumps.wikimedia.org/other/clickstream/2020-08/clickstream-enwiki-2020-08.tsv.gz) one.
2. Start ZooKeeper and Apache Kafka.

```sh
$ ./$KAFKA_BIN/bin/zookeeper-server-start.sh ./$KAFKA_BIN/config/zookeeper.properties
$ ./$KAFKA_BIN/bin/kafka-server-start.sh ./$KAFKA_BIN/config/server.properties
```

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
