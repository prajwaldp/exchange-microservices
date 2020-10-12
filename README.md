# The Fictitious Stock Exchange

A simulation of a fictitious stock exchange.

**What's different from the NYSE?**

The Fictitious Stock Exchange handles companies from all over the world

**Why?**

Because it's a good demonstration of the use of Cassandra partition keys.

## The Flow

1. Companies can register themselves on the Stock Exchange by specifying the
   number of stocks and the ask price.
2. Traders can buy or sell their stocks by providing a bid price or an ask price.
3. The ownership of stocks change in near real-time.

## Cassandra Data Modeling

```sql
CREATE KEYSPACE fictitiousstockexchange
WITH REPLICATION = {
    'class': 'SimpleStrategy',
    'replication_factor':  1
};

USE fictitiousstockexchange;

CREATE TABLE bids (
    bid_time_uuid TIMEUUID,
    initiater_id TEXT,
    ticker_symbol TEXT,
    number_of_shares DOUBLE,
    bid_price DOUBLE,
    country TEXT,
    PRIMARY KEY ((country, ticker_symbol), initiater_id, bid_time_uuid)
);

CREATE TABLE asks (
    ask_time_uuid TIMEUUID,
    initiater_id TEXT,
    ticker_symbol TEXT,
    number_of_shares DOUBLE,
    ask_price DOUBLE,
    country TEXT,
    PRIMARY KEY ((country, ticker_symbol), initiater_id, ask_time_uuid)
);

CREATE TABLE transfers (
    transfer_time_uuid TIMEUUID,
    initiater_id TEXT,
    ticker_symbol TEXT,
    number_of_shares DOUBLE,
    credit BOOLEAN,
    share_price DOUBLE,
    country TEXT,
    PRIMARY KEY ((country, ticker_symbol), initiater_id, transfer_time_uuid)
);
```

## Architecture

![architecture](assets/architecture.png)

*Note: The architecture has chnaged since I made this picture. Will change it once I have a stable version*

## Prerequites

1. Apache Kafka

```sh
~ ./$KAFKA_BIN/bin/zookeeper-server-start.sh ./$KAFKA_BIN/config/zookeeper.properties
~ ./$KAFKA_BIN/bin/kafka-server-start.sh ./$KAFKA_BIN/config/server.properties
```

2. Compile all sub-projects

```sh
~ mvn clean compile
```

This should also build Docker containers for all the services

3. Start all the services

```sh
~ docker-compose up -d
```

## API

Create new bids

```sh
curl -X POST -H "Content-Type: application/json" -d '{"country": "US", "tickerSymbol": "AAPL", "initiaterId": "AAPL", "numberOfShares": 1000, "bidPrice": 40}' localhost:8002/bids
```
