localhost:8080/dynamo/v1/index
localhost:8080/dynamo/v1/test
localhost:8080/dynamo/v1/persons
...

https://springbootdynamo.aws.de.altemista.cloud/dynamo/v1/index


install Dynamo local:
https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/DynamoDBLocal.html
switch to local dynamo folder
C:\prg\dynamodb_local
and run dynamo
java -jar DynamoDBLocal.jar
Open the DynamoDB-WebInterface:
   http://127.0.0.1:8000/shell/
   
Configure the DynamoDB-Webinterface as shown here:
![DynamoDBConfiguration](./doc/img/dynamodb_local_setup.png "configuring local dynamodb")
put into the configuration->"Access Key id:" the value: "localaccess"
  
016973021151.dkr.ecr.eu-west-3.amazonaws.com/ecs-repository:springbootdynamo

docker build -f src/main/docker/Dockerfile -t $TAG_NAME .
docker build -f src/main/docker/Dockerfile -t spring-boot .
docker build -f src/main/docker/Dockerfile -t 016973021151.dkr.ecr.eu-west-3.amazonaws.com/ecs-repository:springbootdynamo .
  
docker run -d -p 8080:8080 springbootdynamo 
docker run -d -p 8080:8080 016973021151.dkr.ecr.eu-west-3.amazonaws.com/ecs-repository:springbootdynamo

aws configure

 $(aws ecr get-login --region eu-west-3 --no-include-email) 
 docker push 016973021151.dkr.ecr.eu-west-3.amazonaws.com/ecs-repository:springbootdynamo 
  
