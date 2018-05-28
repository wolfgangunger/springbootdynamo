package com.unw.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.unw.model.Company;
import com.unw.model.Person;

@Service
public class CompanyService {
	
private final CompanyRepo companyRepo;
	
	@Autowired
	private DynamoService dynamoService;
	
	@Autowired
	public CompanyService(CompanyRepo companyRepo) {
		this.companyRepo = companyRepo;
	}

	// repo methods --------------
	
	@PostConstruct
	public void init() {
		//dynamoService.checkTable(Company.class);
	}

	public long count() {
		return companyRepo.count();
	}
	
	public Iterable<Company> findAll(Sort arg0) {
		return companyRepo.findAll(arg0);
	}

	public Page<Company> findAll(Pageable arg0) {
		return companyRepo.findAll(arg0);
	}

	public Iterable<Company> findAll(){
		return 	companyRepo.findAll();
	}
	public List<Company> findAllAsList(){
		Iterable<Company> companies = companyRepo.findAll();
		List<Company> list = new ArrayList();
		companies.forEach(list::add);
		System.out.println(list.size());
		return list;
	}
	
	public Optional<Company> findCompanyById(String id) {
		return companyRepo.findById(id);
	}
	
	public Optional<Company> findByName(String name){
		return companyRepo.findByName(name);
	}
	
	public <S extends Company> S save(S arg0) {
		return companyRepo.save(arg0);
	}
	
	public void deleteCompany(Company company) {
		companyRepo.delete(company);
	}
	// repo methods --------------
}
