FROM openjdk:17
ARG JAR_FILE=build/libs/news-api-1.0-SNAPSHOT.jar
COPY ${JAR_FILE} news-api-1.0-SNAPSHOT.jar
ENTRYPOINT ["java","-jar","/news-api-1.0-SNAPSHOT.jar"]