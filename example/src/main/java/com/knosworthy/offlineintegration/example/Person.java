package com.knosworthy.offlineintegration.example;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	private Long id;
	private String firstName;
	private String middleName;
	private String lastName;
	

	public Person() {
	}

	public Person(String firstName, String lastName) {
		this(firstName, null, lastName);
	}

	public Person(String firstName, String middleName, String lastName) {
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public Long getId() {
		return id;
	}


	public String toString() {
		StringBuilder buffer = new StringBuilder();
		buffer.append(firstName);

		if (middleName != null && !middleName.isEmpty()) {
			buffer.append(" " +middleName);
		}
		buffer.append(" " + lastName);
		return buffer.toString();
	}
}