FROM openjdk:17-jdk-alpine
EXPOSE 8080
ADD target/ChatServer-0.1-SNAPSHOT.jar ChatServer.jar
ENTRYPOINT ["java","-jar","/ChatServer-0.1-SNAPSHOT"]