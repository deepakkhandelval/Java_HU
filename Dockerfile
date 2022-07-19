FROM openjdk:11.0.14-jdk-slim-buster as build
ADD out/artifacts/hu22_jar/hu22.jar hu22.jar
EXPOSE 8989
ENTRYPOINT ["java", "-jar", "hu22.jar"]
#COPY .mvn/ .mvn
#COPY mvnw pom.xml ./
#RUN ./mvnw dependency:go-offline
#COPY src ./src
#RUN ./mvnw install
#
#FROM tomcat:9.0 as production-stage
#RUN apt-get update -y
#COPY --from=build /app/target/hu22.war /usr/local/tomcat/webapps/
#COPY ./tomcat-users.xml /usr/local/tomcat/conf/tomcat-users.xml
