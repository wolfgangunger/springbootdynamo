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

import com.unw.model.Person;

@Service
public class PersonService {
	
	private final PersonRepo personRepo;
	
	@Autowired
	private DynamoService dynamoService;
	
	@Autowired
	public PersonService(PersonRepo personRepo) {
		this.personRepo = personRepo;
	}
	
	@PostConstruct
	public void init() {
		dynamoService.checkTable(Person.class);
	}
	
	public void checkDynamo() {
		//connect2AWS();
		dynamoService.createTable(Person.class);
	}
	
	public void createTablePerson() {
		dynamoService.createTable(Person.class);
		System.out.println("Person created");
	}
	
	public void insertPerson() {
		Person p = new Person();
		p.setId("001");
		p.setFirstName("Wolfgang");
		p.setLastName("Unger");
		save(p);
	}

	// repo methods --------------

	public long count() {
		return personRepo.count();
	}
	
	public Iterable<Person> findAll(Sort arg0) {
		return personRepo.findAll(arg0);
	}

	public Page<Person> findAll(Pageable arg0) {
		return personRepo.findAll(arg0);
	}

	public Iterable<Person> findAll(){
		return 	personRepo.findAll();
	}
	public List<Person> findAllAsList(){
		Iterable<Person> persons = personRepo.findAll();
		List<Person> list = new ArrayList();
		persons.forEach(list::add);
		System.out.println(list.size());
		return list;
	}
	
	public Optional<Person> findPersonById(String id) {
		return personRepo.findById(id);
	}
	
	public Optional<Person> findByLastName(String lastName){
		return personRepo.findByLastName(lastName);
	}
	
	public <S extends Person> S save(S arg0) {
		return personRepo.save(arg0);
	}
	
	public void deletePerson(Person person) {
		personRepo.delete(person);
	}
	// repo methods --------------
	
}
