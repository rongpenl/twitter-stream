[![Build Status](https://travis-ci.org/rongpenl/twitter-stream.svg?branch=master)](https://travis-ci.org/rongpenl/twitter-stream)
[![GitHub stars](https://img.shields.io/github/stars/rongpenl/twitter-stream.svg?style=social&label=Star&maxAge=2592000)](https://GitHub.com/saurzcode/twitter-stream/stargazers/)
[![GitHub forks](https://img.shields.io/github/forks/rongpenl/twitter-stream.svg?style=social&label=Fork&maxAge=2592000)](https://GitHub.com/saurzcode/twitter-stream/network/)
[![GitHub watchers](https://img.shields.io/github/watchers/rongpenl/twitter-stream.svg?style=social&label=Watch&maxAge=2592000)](https://GitHub.com/saurzcode/twitter-stream/watchers/)
[![GitHub contributors](https://img.shields.io/github/contributors/rongpenl/twitter-stream.svg)](https://GitHub.com/saurzcode/twitter-stream/graphs/contributors/)

# twitter-stream

- [twitter-stream](#twitter-stream)
- [Requirements :](#requirements-)
- [How to Run](#how-to-run)
- [Build Environment :](#build-environment-)

Twitter-Kafka Data Pipeline

# Requirements :

Apache Kafka 2.6.0
Twitter Developer account ( for API Key, Secret etc.)
Apache Zookeeper ( required for Kafka)
Oracle JDK 1.8 (64 bit )


# How to Run
Provide JVM Argument for TwitterKafkaProducer.java in following order

java TwitterKafkaProducer.java <consumer_key> <consumer_secret> <account_token> <account_secret> <hashtag/term>

You can configure name of the topic in [TwittterKafkaConfig.java](src/main/java/com/saurzcode/twitter/config/TwitterKafkaConfig.java)
# Build Environment :
Eclipse/Intellij
Apache Maven

Detailed steps available here -
http://saurzcode.in/2015/02/kafka-producer-using-twitter-stream/

```
java -version
openjdk version "1.8.0_272"
OpenJDK Runtime Environment (AdoptOpenJDK)(build 1.8.0_272-b10)
OpenJDK 64-Bit Server VM (AdoptOpenJDK)(build 25.272-b10, mixed mode)
```

```
brew install maven
mvn --version
Apache Maven 3.6.3 (cecedd343002696d0abb50b32b541b8a6ba2883f)
Maven home: /usr/local/Cellar/maven/3.6.3_1/libexec
Java version: 15.0.1, vendor: N/A, runtime: /usr/local/Cellar/openjdk/15.0.1/libexec/openjdk.jdk/Contents/Home
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.15.7", arch: "x86_64", family: "mac"
```

```
brew install kafka
brew info kafka
kafka: stable 2.6.0 (bottled)
Publish-subscribe messaging rethought as a distributed commit log
https://kafka.apache.org/
/usr/local/Cellar/kafka/2.6.0 (278 files, 64.0MB) *
  Poured from bottle on 2020-11-04 at 13:41:28
From: https://github.com/Homebrew/homebrew-core/blob/HEAD/Formula/kafka.rb
License: Apache-2.0
==> Dependencies
Required: openjdk ✔, zookeeper ✔
==> Caveats
To have launchd start kafka now and restart at login:
  brew services start kafka
Or, if you don't want/need a background service you can just run:
  zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties & kafka-server-start /usr/local/etc/kafka/server.properties
==> Analytics
install: 5,165 (30 days), 15,412 (90 days), 66,843 (365 days)
install-on-request: 5,143 (30 days), 15,276 (90 days), 65,971 (365 days)
build-error: 0 (30 days)
```

```
# terminal 1
zookeeper-server-start /usr/local/etc/kafka/zookeeper.properties
```

```
# terminal 2
kafka-server-start /usr/local/etc/kafka/server.properties
```

```
# terminal 3
kafka-topics --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic twitter-topic
kafka-topics --describe --zookeeper localhost:2181 --topic twitter-topic

mvn package
java -jar target/twitter-stream-0.0.1-SNAPSHOT-jar-with-dependencies.jar [args]
```

```
# tail
kafka-console-consumer --bootstrap-server localhost:9092 --topic twitter-topic --from-beginning
```