FROM maven:3.6-jdk-11-slim
ADD target/CCS_DataBaseRest-0.0.1-SNAPSHOT.jar CCS_DataBaseRest-0.0.1-SNAPSHOT.jar
COPY src /home/app/src
COPY pom.xml /home/app
COPY selenium-server-standalone-3.9.0.jar /home/app
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "CCS_DataBaseRest-0.0.1-SNAPSHOT.jar"]