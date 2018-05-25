package com.unw.service;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.DynamoDBPagingAndSortingRepository;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;

import com.unw.model.Company;

@EnableScan
public interface CompanyRepo extends DynamoDBPagingAndSortingRepository<Company, String> {

	Optional<Company> findById(String id);
	
	Optional<Company> findByName(String name);

}
