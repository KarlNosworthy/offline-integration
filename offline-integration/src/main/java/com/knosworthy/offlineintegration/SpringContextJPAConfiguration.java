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
package com.knosworthy.offlineintegration;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public abstract class SpringContextJPAConfiguration {
	
	// Hibernate (JPA) Property names
	public static final String HibernateDialectPropertyName = "hibernate.dialect";
	public static final String HibernateDDLAutoPropertyName = "hibernate.hbm2ddl.auto";
	public static final String HibernateJDBCBatchSizePropertyName = "hibernate.jdbc.batch_size";
	public static final String HibernateShowSQLPropertyName = "hibernate.show_sql";
	public static final String HibernateFormatSQLPropertyName = "hibernate.format_sql";
	public static final String HibernateUseSQLCommentsPropertyName = "hibernate.use_sql_comments";
	public static final String HibernateImportFilesPropertyName = "hibernate.hbm2ddl.import_files";

	// Spring JPA Property Values
	public static final String JPAVendorAdapterClassName = "org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter";


	public abstract String getDataSourceDriverClassName();

	public abstract String getDataSourceURL();

	public abstract String getDataSourceUsername();

	public abstract String getDataSourcePassword();

	public abstract String getDialectPropertyClassName();

	public abstract String[] getEntityPackagesToScan();

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPackagesToScan(getEntityPackagesToScan());
		entityManagerFactory.setJpaProperties(jpaProperties());
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaVendorAdapter(vendorAdapter());
		return entityManagerFactory;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory){
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}

	protected DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(getDataSourceDriverClassName());
		dataSource.setUrl(getDataSourceURL());
		dataSource.setUsername(getDataSourceUsername());
		dataSource.setPassword(getDataSourcePassword());
		return dataSource;
	}

	protected JpaVendorAdapter vendorAdapter() {
		JpaVendorAdapter vendorAdapter = null;

		try {
			vendorAdapter = (JpaVendorAdapter) Class.forName(JPAVendorAdapterClassName).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
		}

		return vendorAdapter;
	}

	protected Properties jpaProperties() {
		Properties properties = new Properties();
		properties.setProperty(HibernateDialectPropertyName, getDialectPropertyClassName());
		properties.setProperty(HibernateDDLAutoPropertyName, "create");
		properties.setProperty(HibernateJDBCBatchSizePropertyName, "20");
		properties.setProperty(HibernateShowSQLPropertyName, "false");
		properties.setProperty(HibernateFormatSQLPropertyName, "false");
		properties.setProperty(HibernateUseSQLCommentsPropertyName, "false");
		return properties;
	}
}