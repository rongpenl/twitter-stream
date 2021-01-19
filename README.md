# Kafka producer app for Twitter filtered stream API.
- [Kafka producer app for Twitter filtered stream API.](#kafka-producer-app-for-twitter-filtered-stream-api)
  - [Local environment](#local-environment)
  - [Cloud environment](#cloud-environment)
    - [Deploying to Google App Engine](#deploying-to-google-app-engine)

I am trying to replicate the product in this [blog post](https://qulia.medium.com/realtime-dashboard-app-with-kafka-beam-dataflow-bigquery-data-studio-and-streamlit-c9f63d44e417). 

This readme only documents my personal learning experience. Treat it as a diary.

Where I started:

1. Extensive Python programming knowledge but little Java knowledge. Can read Java code but not familiar with the tools/IDE, etc.
2. Extensive Machine learning knowledge. 
3. Have experience working with Twitter API using `twarc` and `tweepy`.
4. Some practical experience working with Docker.
5. Some theoretical knowledge about distributed systems.
6. Some practical experience with AWS EC2, S3, networking, etc.

Where I want to go:

1. Practical knowledge and skills working with `maven`.
2. Practical experience with Kafka.
3. Reproduce the original product on Google Cloud Platform.
4. Migrate the product to Amazon Web Services.
5. Add ElasticSearch full-text function.

In order to honor Twitter [TOS](https://twitter.com/en/tos), I am not going to share sample data in this repo.

## Local environment

![Original Components](https://miro.medium.com/max/1400/1*UPT1tKGFIvP6RhnY72-HuA.png)

TODO

## Cloud environment

### Deploying to Google App Engine

- Create params.yaml file with the following content replacing the values accordingly.

```text
env_variables:
  TWITTER_KAFKA_CONFIG: "[Twitter API credentials] [filter topics] [bootstrap server]"
```

- Deploy to Google App Engine with:

```shell
make gcloud-deploy
```