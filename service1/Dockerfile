FROM openjdk:11.0.10-jre-slim
COPY target/service1-0.0.1-SNAPSHOT.jar .
CMD java -Xmx300m -Xms300m -XX:TieredStopAtLevel=1 -noverify -jar service1-0.0.1-SNAPSHOT.jar
EXPOSE 10000
