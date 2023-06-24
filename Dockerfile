# From openjdk:17
# EXPOSE 8090
# COPY --from=build target/RoamifyServices.jar RoamifyServices.jar
# ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]
From ubuntu:latest AS build
RUN apt-get update -y
RUN apt-get install openjdk-17-jdk -y
RUN apt-get install maven -y
COPY . .
RUN mvn clean install -DskipTests

FROM openjdk:17-jdk-slim
EXPOSE 8090
COPY --from=build target/RoamifyServices.jar RoamifyServices.jar
ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]