package com.knosworthy.offlineintegration.example;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;
import java.util.logging.Logger;

public class PersonExampleApp {

    private static final Logger LOGGER = Logger.getLogger(PersonExampleApp.class.getName());


    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PersonExampleAppConfiguration.class)) {
            PersonService personService = context.getBean(PersonService.class);

            List<Person> allPeople = personService.getAll();
            for (Person person : allPeople) {
                LOGGER.info("Say hello to ....  "+person.toString());
            }
        }
    }
}
