FROM openjdk:8




#ADD target/online_shop-0.0.1-SNAPSHOT.jar /online_shop.jar



ADD src /src
ADD pom.xml /
ADD entrypoint.sh /

RUN chmod +x /entrypoint.sh && export http_proxy= && https_proxy= && apt update && apt install -y maven

EXPOSE 8080

ENTRYPOINT /entrypoint.sh

#ENTRYPOINT java -jar online_shop.jar
#/usr/bin/wait-for-it mysql_container:3306 ;