# Lab 4 

## 1/

## a) Identify a couple of examples on the use of AssertJ expressive methods chaining.

In class EmployeeRestControllerTemplateIT:
        
    assertThat(response.getBody()).extracting(Employee::getName).containsExactly("bob", "alex");

In class EmployeeService_UnitTest:
        
    assertThat(allEmployees).hasSize(3).extracting(Employee::getName).contains(alex.getName(), john.getName(), bob.getName());


## b) Identify an example in which you mock the behavior of the repository (and avoid involving a database).

In class EmployeeService_UnitTest:

    Mockito.when(employeeRepository.findByName(john.getName())).thenReturn(john);
    Mockito.when(employeeRepository.findByName(alex.getName())).thenReturn(alex);
    Mockito.when(employeeRepository.findByName("wrong_name")).thenReturn(null);
    Mockito.when(employeeRepository.findById(john.getId())).thenReturn(Optional.of(john));
    Mockito.when(employeeRepository.findAll()).thenReturn(allEmployees);
    Mockito.when(employeeRepository.findById(-99L)).thenReturn(Optional.empty());


## c) What is the difference between standard @Mock and @MockBean?

@Mock is used for unit testing business logic (only using JUnit and Mockito). 
@MockBean is for writing a test that is backed by a Spring Test Context and you want to add or replace a bean with a mocked version of it.

## d) What is the role of the file “application-integrationtest.properties”? In which conditions will it be used?

It contains various configuration options for Spring-Boot, including some relating to the connection to the database.
It will be used by loading it into the application context with the help of the annotation @TestPropertySource.



