package com.knosworthy.offlineintegration.example;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class PersonDAO {
	
	@PersistenceContext
	private EntityManager entityManager;


	public List<Person> getAll() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Person> criteriaQuery = criteriaBuilder.createQuery(Person.class);

		Root<Person> root = criteriaQuery.from(Person.class);
		criteriaQuery.select(root);
		
		TypedQuery<Person> query = entityManager.createQuery(criteriaQuery);
		return query.getResultList();
	}

	public Person getPerson(Long id) {
		return entityManager.find(Person.class, id);
	}
}