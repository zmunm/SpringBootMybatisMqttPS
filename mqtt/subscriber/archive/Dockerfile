FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY option.yml .
COPY SampleSubscriber.jar .
ENTRYPOINT ["java","-jar","SampleSubscriber.jar","-yml"]