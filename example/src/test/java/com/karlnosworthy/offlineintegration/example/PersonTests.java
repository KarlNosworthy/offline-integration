/*
 * MIT License
 *
 * Copyright (c) 2017 Karl Nosworthy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.karlnosworthy.offlineintegration.example;

import com.karlnosworthy.offlineintegration.OfflineIntegrationSupport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class PersonTests implements OfflineIntegrationSupport {

	@Configuration
	public static class PersonTestsConfiguration extends OfflineIntegrationConfiguration {

		@Override
		protected Properties getJPAProperties() {
			Properties properties = super.getJPAProperties();
			properties.setProperty(HibernateImportFilesPropertyName, "person_import_data.sql");
			return properties;
		}

		@Override
		public String[] getEntityPackagesToScan() {
			return new String[] {"com.karlnosworthy.offlineintegration.example"};
		}

		@Bean
		public PersonService personService() {
			return new PersonService(personDAO());
		}

		@Bean
		public PersonDAO personDAO() {
			return new PersonDAO();
		}
	}

	@Autowired
	private PersonService personService;


	@Test
	public void testGetAllPeople() {
		assertThat(personService).isNotNull();

		List<Person> people = personService.getAll();
		assertThat(people).isNotNull();
		assertThat(people.size()).isEqualTo(5);

		Person firstPerson = people.get(0);
		assertThat(firstPerson).isNotNull();
		assertThat(firstPerson.getId()).isEqualTo(1);
		assertThat(firstPerson.getFirstName()).isNotNull();
		assertThat(firstPerson.getFirstName().isEmpty()).isFalse();
		assertThat(firstPerson.getFirstName()).isEqualTo("Shaun");
		assertThat(firstPerson.getMiddleName()).isNotNull();
		assertThat(firstPerson.getMiddleName().isEmpty()).isFalse();
		assertThat(firstPerson.getMiddleName()).isEqualTo("the");
		assertThat(firstPerson.getLastName()).isNotNull();
		assertThat(firstPerson.getLastName().isEmpty()).isFalse();
		assertThat(firstPerson.getLastName()).isEqualTo("Sheep");

		Person lastPerson = people.get(4);
		assertThat(lastPerson).isNotNull();
		assertThat(lastPerson.getId()).isEqualTo(5);
		assertThat(lastPerson.getFirstName()).isNotNull();
		assertThat(lastPerson.getFirstName().isEmpty()).isFalse();
		assertThat(lastPerson.getFirstName()).isEqualTo("Arthur");
		assertThat(lastPerson.getMiddleName()).isNull();
		assertThat(lastPerson.getLastName()).isNotNull();
		assertThat(lastPerson.getLastName().isEmpty()).isFalse();
		assertThat(lastPerson.getLastName()).isEqualTo("Christmas");
	}

	@Test
	public void testGetPersonWithId() {
		assertThat(personService).isNotNull();
		assertThat(personService.getPerson(null)).isNull();

		Person person = personService.getPerson(2L);
		assertThat(person).isNotNull();
		assertThat(person.getId()).isEqualTo(2);
		assertThat(person.getFirstName()).isNotNull();
		assertThat(person.getMiddleName()).isNull();
		assertThat(person.getLastName()).isNotNull();
		assertThat(person.getFirstName()).isEqualTo("Snow");
		assertThat(person.getLastName()).isEqualTo("White");
	}
}