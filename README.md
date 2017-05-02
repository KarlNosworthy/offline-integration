# Offline Integration Testing
A micro framework to make it easier to perform offline / local integration testing when using Spring and Hibernate within a Java backend.

[![Build Status](https://travis-ci.org/KarlNosworthy/offline-integration-testing.svg?branch=master)](https://travis-ci.org/KarlNosworthy/offline-integration-testing)

## Introduction
By providing the ability to simplify swapping out of the JPA based persistence configuration, a Data Access Layer and its objects can be tested quickly and easily to ensure that criteria and any other SQL/JPQL/HQL logic is performing as expected.
This is achieved by utilising an in-memory database in order to run integration tests through a lightweight, low footprint, local database.

By default an in-memory H2 database instance is used but the configuration can easily be adjusted to fit requirements.


## Getting Started

Implement the _OfflineIntegrationSupport_ interface, extend the provided instance of _OfflineIntegrationConfiguration_ and provide any persistence adjustments (including overriding _getEntityPackagesToScan_) or bean references you need.

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class)
public class MyIntegrationTest implements OfflineIntegrationSupport {

  @Configuration
  public static MyIntegrationTestConfiguration extends OfflineIntegrationConfiguration {
  
    @Override
    public String[] getEntityPackagesToScan() {
        return new String[] {"com.mypackage.model"};
    }
      ....
  }
}
```

## Modifying OfflineIntegrationConfiguration

#### Specifying where to find model entities
Override `getEntityPackagesToScan` to provide an array of package names to be scanned for annotated Entity subclasses.

```java
  @Override
  public String[] getEntityPackagesToScan() {
      return new String[] {"com.mypackage.model", "com.thirdpartylib.models"};
  }  
```

#### Adjusting JPA Properties
Override `getJPAProperties` if you want to adjust any JPA EntityManagerFactory / Hibernate SessionFactory properties.

```java
   // To make sure you can see the SQL being created and used (this is off by default).
   @Override
   protected Properties getJPAProperties() {
      Properties properties = super.getJPAProperties();
      properties.setProperty(HibernateShowSQLPropertyName, "true");
      return properties;
   }
```

#### Overriding the data source configuration

Override `getDataSourceUsername` to specifiy your own username. (This is *blank* by default).

```java
    @Override
    public String getDataSourceUsername() {
        return "myusername";
    }
```

Override `getDataSourcePassword` to specifiy your own password. (This is *blank* by default).

```java
   @Override
   public String getDataSourcePassword() {
      return "mypassword";
   }
```

Override `getDataSourceURL()` to change the location and/or type of database used. (This is _'jdbc:h2:mem:test;DB_CLOSE_DELAY=-1'_ by default).

```java
   @Override
   public String getDataSourceURL() {
      // Create the test database on the filesystem in the users home directory
      return "jdbc:h2:~/test";
   }
```

##### Switching to a different database provider

Override `getDataSourceDriverClassName`, `getDialectPropertyClassName` and `getDataSourceURL` to configure the connection to the database.

```java
  // switch to local MySQL database for integration testing
  
  @Override
  public String getDataSourceDriverClassName() {
      return "com.mysql.jdbc.Driver";
  }
  
  @Override
  public String getDialectPropertyClassName() {
      return "org.hibernate.dialect.MySQL5Dialect";
  }
  
  @Override
  public String getDataSourceURL() {
      return "jdbc:mysql://localhost:3306/integration_testing";
  }
```

## Sample code

A full example of utilising this framework both for testing and as a basis to configure a spring-based app is provided in the this repository.

