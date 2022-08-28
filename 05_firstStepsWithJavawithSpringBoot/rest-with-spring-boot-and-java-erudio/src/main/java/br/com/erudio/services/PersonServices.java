package br.com.erudio.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.stereotype.Service;

import br.com.erudio.PersonController;
import br.com.erudio.data.vo.v1.PersonVO;
import br.com.erudio.exceptions.RequiredObjectIsNullException;
import br.com.erudio.exceptions.ResourceNotFoundException;
import br.com.erudio.mapper.DozerMapper;
import br.com.erudio.model.Person;
import br.com.erudio.repositories.PersonRepository;

@Service
public class PersonServices {
	
	private final AtomicLong counter = new AtomicLong();
	private Logger logger = Logger.getLogger(PersonServices.class.getName());
	
	
	@Autowired
	PersonRepository repository;
	
	public List<PersonVO> findAll() {
		
		logger.info("Finding all persons");
	
		var persons = DozerMapper.parseListObjects(repository.findAll(), PersonVO.class);
		persons
			.stream()
			.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		return persons;
		
	}
	
	public PersonVO findById(Long id) {
		
		logger.info("Finding one Person");
		var entity = repository.findById(id)
			.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		PersonVO vo = DozerMapper.parseObject(entity, PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return vo;
				
	}
	
	public PersonVO create (PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("Creating one Person");
		var entity = DozerMapper.parseObject(person, Person.class);
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public PersonVO update (PersonVO person) {
		
		if(person == null) throw new RequiredObjectIsNullException();
		
		logger.info("update one Person");
		
		var entity = repository.findById(person.getKey())
		.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		entity.setFirstName(person.getFirstName());
		entity.setLastName(person.getLastName());
		entity.setAdress(person.getAdress());
		entity.setGender(person.getGender());
		
		var vo = DozerMapper.parseObject(repository.save(entity), PersonVO.class);
		vo.add(linkTo(methodOn(PersonController.class).findById(vo.getKey())).withSelfRel());
		return vo;
	}
	
	public void delete (Long id) {
		logger.info("delete one PersonVO");
		
		var entity = repository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("No records found for this ID"));
		
		repository.delete(entity);
		
	}
	
	private PersonVO mockPerson(int i) {
		
		logger.info("Finding one PersonVO");
		
		PersonVO person =  new PersonVO();
		person.setKey(counter.incrementAndGet());
		person.setFirstName("PersonVO Name " + i);
		person.setLastName("Last Name " + i);
		person.setAdress("Some address in Brasil " + i);
		person.setGender("Male");
		return person;
	}
}
