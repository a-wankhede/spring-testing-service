package com.spring.TestingApp.controller;

import com.spring.TestingApp.dto.EmployeeDTO;
import com.spring.TestingApp.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient(timeout = "100000")
//@Import(TestContainerConfiguration.class)
public class AbstractIntegrationTest {


    @Autowired
     WebTestClient webTestClient;

    Employee testEmployee = Employee.builder()
//                .id(1L)
            .email("aman@gmail.com")
                .name("Aman")
                .salary(100L)
                .build();

    EmployeeDTO testEmployeeDTO = EmployeeDTO.builder()
//                .id(1L)
            .email("aman@gmail.com")
                .name("Aman")
                .salary(100L)
                .build();
}
