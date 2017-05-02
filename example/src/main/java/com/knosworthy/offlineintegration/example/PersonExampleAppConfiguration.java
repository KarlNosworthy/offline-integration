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
    protected Properties getJPAProperties() {
        Properties properties = super.getJPAProperties();
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