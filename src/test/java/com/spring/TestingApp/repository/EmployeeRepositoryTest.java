package com.spring.TestingApp.repository;

import com.spring.TestingApp.entity.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

//@Import(TestContainerConfiguration.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeRepositoryTest {

    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;

    @BeforeEach
    void setUp() {

        employee = Employee.builder()
//                .id(1L)
                .name("Aman")
                .email("aman@gmail.com")
                .salary(100L)
                .build();

    }

    @Test
    void testFindByEmail_whenEmailIsPresent_thenReturnEmployee() {

        // Arrange, Given
        employeeRepository.save(employee);

        // Act,  When
        List<Employee> employeeList = employeeRepository.findByEmail(employee.getEmail());

        // Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isNotEmpty();
        assertThat(employeeList.get(0).getEmail()).isEqualTo(employee.getEmail());

    }

    @Test
    void testFindByEmail_whenEmailIsNotFound_thenReturnEmptyEmployeeList() {

        // Arrange, Given
        String email = "notPresent.123@gmail.com";

        // Act,  When
        List<Employee> employeeList = employeeRepository.findByEmail(email);


        // Assert, Then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList).isEmpty();


    }
}