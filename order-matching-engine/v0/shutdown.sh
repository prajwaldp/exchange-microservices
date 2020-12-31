#!/bin/sh

export KAFKA_HOME=$HOME/share/kafka_2.13-2.6.0/
$KAFKA_HOME/bin/kafka-server-stop.sh
$KAFKA_HOME/bin/zookeeper-server-stop.sh
