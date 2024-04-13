FROM openjdk:21-jdk
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dcom.sun.management.jmxremote=true", "-Dcom.sun.management.jmxremote.local.only=false", "-Dcom.sun.management.jmxremote.port=9080", "-Dcom.sun.management.jmxremote.ssl=false", "-Dcom.sun.management.jmxremote.authenticate=false", "-Dcom.sun.management.jmxremote.rmi.port=9080", "-Djava.rmi.server.hostname=192.168.0.104", "-Dspring.profiles.active=prod", "-jar", "/app.jar"]
EXPOSE 8080 9080