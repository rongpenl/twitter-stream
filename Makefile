.PHONY: run run-container gcloud-deploy

run:
	@java -jar target/twitter-stream-0.0.1-SNAPSHOT-jar-with-dependencies.jar

run-container:
	@docker build . -t twitter-kafka-stream
	@docker run --env TWITTER_KAFKA_CONFIG twitter-kafka-stream

gcloud-deploy:
	@mvn package
	@gcloud app deploy app.yaml