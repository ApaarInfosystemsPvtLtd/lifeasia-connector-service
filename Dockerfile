FROM uatdockermsr.pmli.corp/infra/openjdk:8-jre-alpine
#FROM openjdk:8-jre-alpine
ARG JAR_FILE
ADD lifeasia-connector-ws-0.0.1-SNAPSHOT-exec.jar lifeasia-connector-ws-0.0.1-SNAPSHOT-exec.jar
ENTRYPOINT ["java", "-Xmx900M","-jar", "-Dspring.profiles.active=${env_name}", "lifeasia-connector-ws-0.0.1-SNAPSHOT-exec.jar"]