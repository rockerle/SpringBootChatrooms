FROM openjdk:17-jdk-alpine
EXPOSE 8080
COPY target/build/libs/ChatServer-0.1-SNAPSHOT.jar ChatServer-0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/ChatServer-0.1-SNAPSHOT.jar"]