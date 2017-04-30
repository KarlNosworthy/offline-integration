package com.knosworthy.offlineintegration.example;

import com.knosworthy.offlineintegration.SpringContextJPAConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class PersonExampleAppConfiguration extends SpringContextJPAConfiguration {

    @Bean
    public PersonService personService() {
        return new PersonService(personDAO());
    }

    @Bean
    public PersonDAO personDAO() {
        return new PersonDAO();
    }

    @Override
    protected Properties jpaProperties() {
        Properties properties = super.jpaProperties();
        properties.setProperty(HibernateImportFilesPropertyName, "person_app_import_data.sql");
        return properties;
    }

    @Override
    public String getDataSourceDriverClassName() {
        return "com.mysql.jdbc.Driver";
    }

    @Override
    public String getDataSourceURL() {
        return "jdbc:mysql://localhost:3306/person_example";
    }

    @Override
    public String getDataSourceUsername() {
        return "<username>";
    }

    @Override
    public String getDataSourcePassword() {
        return "<password>";
    }

    @Override
    public String getDialectPropertyClassName() {
        return "org.hibernate.dialect.MySQL5Dialect";
    }

    @Override
    public String[] getEntityPackagesToScan() {
        return new String[] {"com.knosworthy.offlineintegration.example"};
    }
}