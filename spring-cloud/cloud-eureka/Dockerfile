FROM openjdk:8u171-jdk-slim
LABEL maintainer="Halozhang@gmail.com"
COPY target/*.jar /cloud-server.jar
ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
CMD ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar cloud-server.jar"]
EXPOSE 8080