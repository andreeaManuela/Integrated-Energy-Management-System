FROM maven:3.8.3-openjdk-17 AS builder
COPY ./src/ /root/src
COPY ./pom.xml /root/
COPY ./checkstyle.xml /root/
WORKDIR /root
RUN mvn package -DskipTests

RUN mkdir -p /developments
ADD ./target/BackendChat-0.0.1-SNAPSHOT.jar /developments/

EXPOSE 8083

FROM openjdk:17-jdk-slim-buster

ENV TZ=UTC

COPY --from=builder /developments/BackendChat-0.0.1-SNAPSHOT.jar /BackendChat-0.0.1-SNAPSHOT.jar

ENTRYPOINT ["java", "-jar", "/BackendChat-0.0.1-SNAPSHOT.jar", "-XX:+UseContainerSupport", "-XX:+UnlockExperimentalVMOptions", "-XX:+UseCGroupMemoryLimitForHeap", "-XX:MaxRAMFraction=1", "-Xms512m", "-Xmx512m", "-XX:+UseG1GC", "-XX:+UseSerialGC", "-Xss512k", "-XX:MaxRAM=72m"]
