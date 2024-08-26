FROM registry.cn-hangzhou.aliyuncs.com/yhdja/openjdk:8-jre-alpine
ENV JAVA_OPTS="-javaagent:/agent/skywalking-agent.jar -Xms128m -Xmx256m -Djava.security.egd=file:/dev/./urandom"
COPY target/demo-*.jar app.jar
EXPOSE 8777
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar $PARAMS"]
curl -sfL https://rancher-mirror.rancher.cn/k3s/k3s-install.sh | INSTALL_K3S_MIRROR=cn K3S_URL=https://114.55.147.178:6443 K3S_TOKEN=K10ef0b0be8bb40e28e36d1ba2757f984d95e8b62649664467b44e79e6569b08856::server:a06a4bbf329d4f38968616a36682a5dd sh - --docker