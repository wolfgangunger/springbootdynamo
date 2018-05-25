package com.unw.service;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

//import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
//import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.unw.model.Person;

@EnableScan
public interface PersonRepo extends DynamoDBPagingAndSortingRepository<Person, String> {

	Optional<Person> findById(String id);
	
	Optional<Person> findByLastName(String lastName);

}
