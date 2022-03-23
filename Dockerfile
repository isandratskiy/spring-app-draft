FROM adoptopenjdk/openjdk11
ADD /build/libs/*-SNAPSHOT.jar app.jar
EXPOSE 8080
CMD java -jar app.jar