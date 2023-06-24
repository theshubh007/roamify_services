From openjdk:17
EXPOSE 8090
ADD target/RoamifyServices.jar RoamifyServices.jar
ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]