# Deploying to Google App Engine

- Create params.yaml file with the following content replacing the values accordingly.

```text
env_variables:
  TWITTER_KAFKA_CONFIG: "[Twitter API credentials] [filter topics] [bootstrap server]"
```

- Deploy to Google App Engine with:

```shell
make gcloud-deploy
```