FROM registry.cn-hangzhou.aliyuncs.com/yhdja/openjdk:8-jre-alpine

ENV JAVA_OPTS="-javaagent:/agent/skywalking-agent.jar -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"

ENV PARAMS=""

COPY target/demo-*.jar app.jar

EXPOSE 8777

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar $PARAMS"]
