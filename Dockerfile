From openjdk:17
EXPOSE 8090
COPY --from=build target/RoamifyServices.jar RoamifyServices.jar
ENTRYPOINT ["java","-jar","/RoamifyServices.jar"]