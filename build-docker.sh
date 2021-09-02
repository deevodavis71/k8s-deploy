cd service1
mvn clean package -DskipTests
docker build --tag=microservice-service1:1 .
cd -

cd service2
mvn clean package -DskipTests
docker build --tag=microservice-service2:1 .
cd -

