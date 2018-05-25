package com.unw.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.DescribeTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.util.TableUtils;
import com.unw.config.DynamoDBConfig;

@Service
public class DynamoService {
	
	@Autowired
	private DynamoDBConfig dynamoDBConfig;

	private DynamoDBMapper dynamoDBMapper;

	private AmazonDynamoDB amazonDynamoDB;
	
	@PostConstruct
	public void init() {
		boolean result = connect2DynamoDB();
		if(result) {
			System.out.println("connected");
		}else {
			System.out.println("not connected, check stacktrace");			
		}
	}
	
	public boolean connect2DynamoDB() {
		//ProfileCredentialsProvider credentialsProvider = getProfileCredentialsProvider();
		//amazonDynamoDB = AmazonDynamoDBClientBuilder.standard().withCredentials(credentialsProvider)
		//		.withRegion("eu-central-1").build();
		//connecting based on config values
		amazonDynamoDB = dynamoDBConfig.amazonDynamoDB();
		listTables();
		return amazonDynamoDB != null;
	}
	private void listTables() {
		ListTablesResult result = amazonDynamoDB.listTables();
		result.getTableNames().forEach((name)->System.out.println(" - " + name));
	}
	
	public void checkTable(Class clazz) {
		try {
		DescribeTableResult result = amazonDynamoDB.describeTable(clazz.getSimpleName());
		System.out.println(result);
		System.out.println(result.toString());
		}catch(Exception e) {
			//not existing, create table 
			//e.printStackTrace();
			createTable(clazz);
		}
	}
	
	public boolean createTable(Class clazz) {
		try {
			CreateTableRequest tableRequest = createCreateTableRequestWithMapper(clazz);
			// Create table if it does not exist yet
			TableUtils.createTableIfNotExists(amazonDynamoDB, tableRequest);
			// wait for the table to move into ACTIVE state
			TableUtils.waitUntilActive(amazonDynamoDB, tableRequest.getTableName());
			return tableRequest != null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private CreateTableRequest createCreateTableRequestWithName(String tableName) {
		// Create a table with a primary hash key named 'name', which holds a string
		CreateTableRequest createTableRequest = new CreateTableRequest().withTableName(tableName)
				.withKeySchema(new KeySchemaElement().withAttributeName("id").withKeyType(KeyType.HASH))
				.withAttributeDefinitions(
						new AttributeDefinition().withAttributeName("id").withAttributeType(ScalarAttributeType.S))
				.withProvisionedThroughput(
						new ProvisionedThroughput().withReadCapacityUnits(1L).withWriteCapacityUnits(1L));
		return createTableRequest;
	}
	
   private CreateTableRequest createCreateTableRequestWithMapper(Class clazz) {
		dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);
        CreateTableRequest tableRequest = dynamoDBMapper
          .generateCreateTableRequest(clazz);
        tableRequest.setProvisionedThroughput(
          new ProvisionedThroughput(1L, 1L));
        return tableRequest;
	}
	
	private ProfileCredentialsProvider getProfileCredentialsProvider() {
		ProfileCredentialsProvider credentialsProvider = new ProfileCredentialsProvider();
		try {
			credentialsProvider.getCredentials();
		} catch (Exception e) {
			throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
					+ "Please make sure that your credentials file is at the correct "
					+ "location (C:\\Users\\ungerw\\.aws\\credentials), and is in valid format.", e);
		}
		return credentialsProvider;
	}
}
