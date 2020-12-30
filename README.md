# About

Kafka producer app for Twitter filtered stream API

# Deploying to Google App Engine

- Create params.yaml file with the following content replacing the values accordingly.

```
env_variables:
  TWITTER_KAFKA_CONFIG: "[Twitter API credentials] [filter topics] [bootstrap server]"

```

- Deploy to Google App Engine with:
```
make gcloud-deploy
```