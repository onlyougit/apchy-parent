FROM java:8-jre
MAINTAINER wangwb
ADD ./target/apchy-mq-1.0-SNAPSHOT.jar /app/apchy-mq.jar
CMD ["java", "-Xmx200m", "-jar", "/app/apchy-mq.jar"]
EXPOSE 6666