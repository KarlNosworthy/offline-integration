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