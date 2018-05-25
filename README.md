localhost:8080/dynamo/v1/index
localhost:8080/dynamo/v1/test

install Dynamo local:
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html
switch to local dynamo folder
C:\prg\dynamodb_local
and run dynamo
java -jar DynamoDBLocal.jar


docker build -f src/main/docker/Dockerfile -t $TAG_NAME .
docker build -f src/main/docker/Dockerfile -t spring-boot .
  
  
