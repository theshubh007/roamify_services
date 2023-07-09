From openjdk:17
EXPOSE 8080
ADD target/RoamifyServices.jar RoamifyServices.jar
ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]
# From ubuntu:latest AS build
# RUN apt-get update -y
# RUN apt-get install openjdk-17-jdk -y
# RUN apt-get install maven -y
# COPY . .
# RUN mvn clean install -DskipTests

# FROM openjdk:17-jdk-slim
# EXPOSE 8090
# COPY --from=build target/RoamifyServices.jar RoamifyServices.jar
# ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]


# Build stage
# FROM maven:3.8.4-openjdk-17-slim AS build
# WORKDIR /app
# COPY pom.xml .
# RUN mvn dependency:go-offline

# COPY src ./src
# RUN mvn clean package -DskipTests

# # Final stage
# FROM openjdk:17-jdk-slim
# EXPOSE 8090
# COPY --from=build /app/target/RoamifyServices.jar RoamifyServices.jar
# ENTRYPOINT ["java", "-jar", "/RoamifyServices.jar"]

# From ubuntu:latest AS build
# RUN apt-get update 
# RUN apt-get install openjdk-17-jdk -y
# COPY . .
# RUN ./gradlew bootJar --no-deamon

# FROM openjdk:17
# EXPOSE 8090
# COPY --from=build target/RoamifyServices.jar RoamifyServices.jar
# ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]