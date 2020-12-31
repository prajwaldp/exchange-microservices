#! /bin/sh

export KAFKA_HOME=$HOME/share/kafka_2.13-2.6.0
$KAFKA_HOME/bin/zookeeper-server-start.sh -daemon $KAFKA_HOME/config/zookeeper.properties
sleep 10  # how much time does zookeeper need to start on my system?
$KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties

