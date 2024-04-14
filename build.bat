mvn clean install package
docker build -t userrecord .
docker run -p 8080:8080 userrecord
