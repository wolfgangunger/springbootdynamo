package com.unw.api;

import java.util.List;
import java.util.Optional;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.unw.model.Company;
import com.unw.model.Person;
import com.unw.service.CompanyService;
import com.unw.service.PersonService;

/**
 * @author UNGERW
 */
@Component
@Path("/dynamo/v1/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DynamoResource {

	@Autowired
	private PersonService personService;

	@Autowired
	private CompanyService companyService;
	
    @GET
    @Path("/index")
    public String test() {
        return "Index Page  20.05.2018";
    }
    

    @GET
    @Path("/test")
    public String dynamo() {
    	// boolean result = personService.connect2LocalDynamo();
    	//boolean result = personService.connect2AWS();
    	personService.createTablePerson();
    	personService.insertPerson();
    	long count = personService.count();
    	Iterable<Person> persons =  personService.findAll();
    	for(Person p : persons) {
    		System.out.println(p.getFirstName());
    	}
        return "table created " + count ;
    } 
    
    /// crud for person
    @GET
    @Path("/persons")
    public Response findAllPersons() {
        return Response.status(200).entity(personService.findAll()).build();
    }
    @GET
    @Path("/persons2")
    public List<Person> findAllPersonsList() {
        return personService.findAllAsList();
    } 
   
    @GET
    @Path("/persons/{id}")
    public Response getPersonById(@PathParam ("id") String id) {
        return Response.status(200).entity(personService.findPersonById(id)).build();
    }

    @GET
    @Path("/personsByLastName/{lastName}")
    public Optional<Person> findPersonByLastName(@PathParam ("lastName") String lastName) {
    	return personService.findByLastName(lastName);
    }

    @POST
    @Path("/persons/")
    public Response savePerson(Person person) {
        return Response.status(200).entity(personService.save(person)).build();    	
    }
    
    @DELETE
    @Path("/persons/")    
    public Response deletePerson(Person person) {
        personService.deletePerson(person);
        return Response.status(200).build();
    }
    
    ///
    /// crud for company
    @GET
    @Path("/companies")
    public Response findAllCompanies() {
        return Response.status(200).entity(companyService.findAll()).build();
    }
    @GET
    @Path("/companies2")
    public List<Company> findAllCompaniesList() {
        return companyService.findAllAsList();
    } 
   
    @GET
    @Path("/companies/{id}")
    public Response getCompanyById(@PathParam ("id") String id) {
        return Response.status(200).entity(companyService.findCompanyById(id)).build();
    }

    @GET
    @Path("/companiesByName/{name}")
    public Optional<Company> findCompanByName(@PathParam ("name") String name) {
    	return companyService.findByName(name);
    }

    @POST
    @Path("/companies/")
    public Response saveCompany(Company company) {
        return Response.status(200).entity(companyService.save(company)).build();    	
    }
    
    @DELETE
    @Path("/companies/")    
    public Response deleteCompany(Company company) {
    	companyService.deleteCompany(company);
        return Response.status(200).build();
    }    
}
