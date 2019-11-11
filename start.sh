docker-compose -f AMT-Project-One/docker/topology-project-one/docker-compose.yml down
cd AMT-Project-One
mvn clean install -DskipTests
cd ..
cp ./AMT-Project-One/target/AMT-project-one.war images/payara/
docker-compose -f topology-project-one/docker-compose.yml down
docker-compose -f topology-project-one/docker-compose.yml up --build
