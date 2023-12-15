FROM openjdk:8u312-jre-slim-buster
COPY target/demo-*.jar demo.jar
EXPOSE 8777
ENTRYPOINT ["java", "-jar", "demo.jar"]
CMD []