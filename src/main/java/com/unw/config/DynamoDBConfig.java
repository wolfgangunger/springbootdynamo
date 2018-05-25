package com.unw.config;

import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

@Configuration
@ConfigurationProperties
@EnableDynamoDBRepositories(basePackages = "com.unw.service")
public class DynamoDBConfig {

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		AmazonDynamoDB amazonDynamoDB = new AmazonDynamoDBClient(amazonAWSCredentials());
		if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
			amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
		}
		return amazonDynamoDB;
	}
	/// to be removod - should work with application properties ---
	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}

	public String getAmazonDynamoDBEndpoint() {
		return amazonDynamoDBEndpoint;
	}

	public void setAmazonDynamoDBEndpoint(String amazonDynamoDBEndpoint) {
		this.amazonDynamoDBEndpoint = amazonDynamoDBEndpoint;
	}

	public String getAmazonAWSAccessKey() {
		return amazonAWSAccessKey;
	}

	public void setAmazonAWSAccessKey(String amazonAWSAccessKey) {
		this.amazonAWSAccessKey = amazonAWSAccessKey;
	}

	public String getAmazonAWSSecretKey() {
		return amazonAWSSecretKey;
	}

	public void setAmazonAWSSecretKey(String amazonAWSSecretKey) {
		this.amazonAWSSecretKey = amazonAWSSecretKey;
	}
}
