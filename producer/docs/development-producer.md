# twitter-stream

This is the original development doc. I updated requirements, added comments. and removed redundant and obsolete information.

- [twitter-stream](#twitter-stream)
  - [Requirements](#requirements)
  - [How to Run](#how-to-run)
    - [Start zookeeper and kafka services](#start-zookeeper-and-kafka-services)
    - [Compile and run the binary executable file](#compile-and-run-the-binary-executable-file)
    - [Examine the output](#examine-the-output)

Twitter-Kafka Data Pipeline

## Requirements

The following configuration is provided in the original document.

Apache Kafka 2.6.0
Twitter Developer account ( for API Key, Secret etc.)
Apache Zookeeper ( required for Kafka)
Oracle JDK 1.8 (64 bit )

---

The following configuration is tested on a Ubuntu 20.04 machine as January 2021.

TODO

## How to Run

### Start zookeeper and kafka services

No matter which way you want to use. You have to ensure the `zookeeper` and `kafka` services are available. You can start it as a deamon as described [here](https://computingforgeeks.com/configure-apache-kafka-on-ubuntu/) or start them explicitly as following.

- Step 1: start zookeeper.

```shell
# terminal 1
zookeeper-server-start.sh /usr/local/etc/kafka/zookeeper.properties
```

- Step 2: start kafka.

```shell
# terminal 2
kafka-server-start.sh /usr/local/etc/kafka/server.properties
```

- Step 3: create a topic.

```shell
# terminal 3
kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic twitter-topic
```

Describe the topic you just created (optional).

```shell
kafka-topics.sh --describe --zookeeper localhost:2181 --topic twitter-topic
```

- Additional commands

To remove all topics.

```shell
kafka-topics.sh --delete localhost:9092 --topic <anytopic>
```

### Compile and run the binary executable file

You can configure name of the topic in [TwittterKafkaConfig.java](src/main/java/com/saurzcode/twitter/config/TwitterKafkaConfig.java).

```shell
mvn package
java -jar target/twitter-stream-0.0.1-SNAPSHOT-jar-with-dependencies.jar [args]
```

### Examine the output

```shell
# tail
kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic twitter-topic --from-beginning
```
