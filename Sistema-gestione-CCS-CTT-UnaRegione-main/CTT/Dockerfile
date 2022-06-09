FROM maven:3.6-jdk-11-slim
ADD target/CTT_DataBaseRest-0.0.1-SNAPSHOT.jar CTT_DataBaseRest-0.0.1-SNAPSHOT.jar
COPY src /home/app/src
COPY pom.xml /home/app
COPY selenium-server-4.1.4.jar /home/app
COPY microsoft-edge-stable_101.0.1210.32-1_amd64.deb /home
COPY src/test/resources/Selenium_WebDrivers/msedgedriver /home/app
RUN apt-get update
RUN apt-get -y install sudo
RUN sudo apt-get clean
RUN sudo apt-get update
RUN apt-get -y install fonts-liberation
RUN apt-get -y install libasound2
RUN apt-get -y install libatk-bridge2.0-0
RUN apt-get -y install libatk1.0-0
RUN apt-get -y install libatomic1
RUN apt-get -y install libatspi2.0-0
RUN apt-get -y install libcairo2
RUN apt-get -y install libcups2
RUN apt-get -y install libdbus-1-3
RUN apt-get -y install libdrm2
RUN apt-get -y install libexpat1
RUN apt-get -y install libgbm1
RUN apt-get -y install libglib2.0-0
RUN apt-get -y install libgtk-3-0
RUN apt-get -y install libnspr4
RUN apt-get -y install libxrandr2
RUN apt-get -y install wget
RUN apt-get -y install xdg-utils
RUN apt-get -y install libnss3

RUN sudo dpkg -i /home/microsoft-edge-stable_101.0.1210.32-1_amd64.deb
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "CTT_DataBaseRest-0.0.1-SNAPSHOT.jar"]