package com.saurzcode.twitter.config;

public class TwitterKafkaConfig {
    public static  String CONFIG_ENV_VAR = "TWITTER_KAFKA_CONFIG";
    public static class KafkaConfig {
        public static String SERVERS = "localhost:9092";
        public static String TOPIC = "twitter-topic";
        public static String USER_NAME = "";
        public static String PASSWORD = "";
    }

    public static class TwitterConfig {
        public static String CONSUMER_KEY = "";
        public static String CONSUMER_SECRET = "";
        public static  String TOKEN = "";
        public static  String SECRET = "";
        public static  String TERM = "";
    }
}
