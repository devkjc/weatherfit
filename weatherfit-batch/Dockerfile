FROM openjdk:17-ea-11-jdk-slim
VOLUME /tmp
COPY build/libs/weatherfit-batch.jar weatherfit-batch.jar
ENTRYPOINT ["java", "-jar", "weatherfit-batch.jar"]