FROM bellsoft/liberica-openjdk-alpine:17
ARG JAR_FILE=build/libs/gateway-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} /usr/src/app/gateway-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-Xmx1048m", "-jar", "/usr/src/app/gateway-0.0.1-SNAPSHOT.jar"]
