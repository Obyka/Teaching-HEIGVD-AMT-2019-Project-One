cd AMT-Project-One
mvn clean install -DskipTests
cd ..
cp ./AMT-Project-One/target/AMT-project-one.war images/payara/
cd topology-project-one
docker-compose down
docker-compose up --build
