FROM openjdk:11
ADD ./target/crypto-server-0.0.1-SNAPSHOT.jar /usr/src/crypto-server-0.0.1-SNAPSHOT.jar
WORKDIR usr/src
ENTRYPOINT ["java","-jar", "crypto-server-0.0.1-SNAPSHOT.jar"]
