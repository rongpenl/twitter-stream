package com.saurzcode.twitter.producer;

import com.google.common.collect.Lists;
import com.saurzcode.twitter.config.TwitterKafkaConfig;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Producer to read from twitter for a specifc term and put on Kafka Topic.
 * Needs Twitter app configuration and access keys, token, and consumer key and secret to run.
 */
public class TwitterKafkaProducer {


    private static void run() {

        BlockingQueue<String> queue = new LinkedBlockingQueue<>(10000);
        StatusesFilterEndpoint endpoint = new StatusesFilterEndpoint();
        endpoint.trackTerms(Lists.newArrayList(
                TwitterKafkaConfig.TwitterConfig.TERM));

        Authentication auth = new OAuth1(
                TwitterKafkaConfig.TwitterConfig.CONSUMER_KEY,
                TwitterKafkaConfig.TwitterConfig.CONSUMER_SECRET,
                TwitterKafkaConfig.TwitterConfig.TOKEN,
                TwitterKafkaConfig.TwitterConfig.SECRET);

        Client client = new ClientBuilder().hosts(Constants.STREAM_HOST)
                .endpoint(endpoint).authentication(auth)
                .processor(new StringDelimitedProcessor(queue)).build();


        client.connect();
        java.util.logging.Logger.getLogger("class").info("connected");
        try (Producer<Long, String> producer = getProducer()) {
            while (true) {
                ProducerRecord<Long, String> message = new ProducerRecord<>(
                        TwitterKafkaConfig.KafkaConfig.TOPIC,
                        queue.take());
                producer.send(message);
                java.util.logging.Logger.getLogger("class").info("sending message");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            client.stop();
        }
    }

    private static Producer<Long, String> getProducer() {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, TwitterKafkaConfig.KafkaConfig.SERVERS);
        properties.put(ProducerConfig.ACKS_CONFIG, "1");
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 500);
        properties.put(ProducerConfig.RETRIES_CONFIG, 0);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        if (!TwitterKafkaConfig.KafkaConfig.USER_NAME.isEmpty()) {
            String jaasTemplate = "org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";";
            String jaasCfg = String.format(jaasTemplate,
                    TwitterKafkaConfig.KafkaConfig.USER_NAME, TwitterKafkaConfig.KafkaConfig.PASSWORD);
            properties.put("security.protocol", "SASL_SSL");
            properties.put("sasl.mechanism", "SCRAM-SHA-256");
            properties.put("sasl.jaas.config", jaasCfg);
            properties.put("compression.codec", "1");
        }

        return new KafkaProducer<>(properties);
    }

    public static void main(String[] args) {
        String configVar = System.getenv(TwitterKafkaConfig.CONFIG_ENV_VAR);
        if (configVar != null && !configVar.isEmpty()) {
            args = configVar.split(" ");
        }
        if (args.length < 5)
            throw new IllegalArgumentException("Please Pass 5 arguments, " +
                    "in order - consumerKey, consumerSecret, token, secret, and term + " +
                    "If using remote kafka pass servers followed by username and password");
        //These should be passed in VM arguments for the application.
        TwitterKafkaConfig.TwitterConfig.CONSUMER_KEY = args[0];
        TwitterKafkaConfig.TwitterConfig.CONSUMER_SECRET = args[1];
        TwitterKafkaConfig.TwitterConfig.TOKEN = args[2];
        TwitterKafkaConfig.TwitterConfig.SECRET = args[3];
        TwitterKafkaConfig.TwitterConfig.TERM = args[4];
        TwitterKafkaConfig.KafkaConfig.TOPIC = String.format("%s-%s", TwitterKafkaConfig.KafkaConfig.TOPIC, TwitterKafkaConfig.TwitterConfig.TERM);
        TwitterKafkaConfig.TwitterConfig.TERM = TwitterKafkaConfig.TwitterConfig.TERM.replace('_', ' '); // term on twitter on which you want to filter the results on.
        if (args.length > 5) {
            TwitterKafkaConfig.KafkaConfig.SERVERS = args[5];
        }

        if (args.length > 6) {
            TwitterKafkaConfig.KafkaConfig.USER_NAME = args[6];
            TwitterKafkaConfig.KafkaConfig.PASSWORD = args[7];
            TwitterKafkaConfig.KafkaConfig.TOPIC = String.format("%s-%s",
                    TwitterKafkaConfig.KafkaConfig.USER_NAME,
                    TwitterKafkaConfig.KafkaConfig.TOPIC);
        }
        TwitterKafkaProducer.run();

    }
}
