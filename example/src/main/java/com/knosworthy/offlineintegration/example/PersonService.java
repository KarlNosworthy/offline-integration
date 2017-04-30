package com.knosworthy.offlineintegration.example;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class PersonService {
	
	private PersonDAO personDAO;


	public PersonService(PersonDAO personDAO) {
		this.personDAO = personDAO;
	}

	@Transactional(readOnly = true)
	public List<Person> getAll() {
		return personDAO.getAll();
	}

	@Transactional(readOnly = true)
	public Person getPerson(Long id) {
		Person person = null;
		if (id != null && id > 0) {
			person = personDAO.getPerson(id);
		}
		return person;
	}
}