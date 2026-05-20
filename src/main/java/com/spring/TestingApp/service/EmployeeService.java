package com.spring.TestingApp.service;

import com.spring.TestingApp.dto.EmployeeDTO;

public interface EmployeeService {

    EmployeeDTO getEmployeeById(Long id);
    EmployeeDTO createNewEmployee(EmployeeDTO employeeDto);
    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDto);
    void deleteEmployee(Long id);
}
