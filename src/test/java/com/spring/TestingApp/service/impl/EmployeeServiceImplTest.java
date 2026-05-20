package com.spring.TestingApp.service.impl;

import com.spring.TestingApp.dto.EmployeeDTO;
import com.spring.TestingApp.entity.Employee;
import com.spring.TestingApp.exceptions.ResourceNotFoundException;
import com.spring.TestingApp.repository.EmployeeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

//@Import(TestContainerConfiguration.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class EmployeeServiceImplTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Spy
    private ModelMapper  modelMapper;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee mockEmployee;

    private EmployeeDTO mockEmployeeDTO;

    @BeforeEach
    void setUp() {
        mockEmployee = Employee.builder()
                .id(1L)
                .email("aman@gmail.com")
                .name("Aman")
                .salary(100L)
                .build();

        mockEmployeeDTO = modelMapper.map(mockEmployee, EmployeeDTO.class);
    }

    @Test
    void testGetEmployeeById_WhenEmployeeIdIsPresent_ThenReturnEmployee() {

//        assign

        Long id = mockEmployee.getId();

        when(employeeRepository.findById(id)).thenReturn(Optional.of(mockEmployee)); // stubbing

//        act

        EmployeeDTO employeeDTO = employeeService.getEmployeeById(id);


//        assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getId()).isEqualTo(id);
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployee.getEmail());
        verify(employeeRepository, times(1)).findById(id);

    }

    @Test
    void testGetEmployeeById_whenEmployeeIsNotPresent_thenThrowException() {

//        arrange
        when(employeeRepository.findById(anyLong())).thenReturn(Optional.empty());

//        act and assert
        assertThatThrownBy(() -> employeeService.getEmployeeById(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository).findById(1L);


    }
    @Test
    void testCreateEmployee_WhenValidEmployee_ThenCreateNewEmployee() {

//        assign

        when(employeeRepository.findByEmail(anyString())).thenReturn(List.of());
        when(employeeRepository.save(any(Employee.class))).thenReturn(mockEmployee);

//        act

        EmployeeDTO employeeDTO = employeeService.createNewEmployee(mockEmployeeDTO);

//        assert
        assertThat(employeeDTO).isNotNull();
        assertThat(employeeDTO.getEmail()).isEqualTo(mockEmployeeDTO.getEmail());

        ArgumentCaptor<Employee> employeeArgumentCaptor = ArgumentCaptor.forClass(Employee.class);
        verify(employeeRepository).save(employeeArgumentCaptor.capture());

        Employee caputeredEmployee = employeeArgumentCaptor.getValue();
        assertThat(caputeredEmployee.getEmail()).isEqualTo(mockEmployee.getEmail());
    }

    @Test
    void testCreateNewEmployee_whenAttemptingToCreateEmployeeWithExistingEmail_thenThrowException() {
//        arrange
        when(employeeRepository.findByEmail(mockEmployeeDTO.getEmail())).thenReturn(List.of(mockEmployee));
//        act and assert

        Assertions.assertThatThrownBy(() -> employeeService.createNewEmployee(mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Employee already exists with email: "+mockEmployee.getEmail());

        verify(employeeRepository).findByEmail(mockEmployeeDTO.getEmail());
        verify(employeeRepository, never()).save(any());
    }

    @Test
    void updateEmployee_whenEmployeeDoesNotExists_thenThrowException() {

//        arrange
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        //        act and assert

        assertThatThrownBy(() -> employeeService.updateEmployee(1L, mockEmployeeDTO))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository).findById(1L);
        verify(employeeRepository, never()).save(any());


    }

    @Test
    void testUpdateEmployee_whenAttemptingToUpdateEmail_thenThrowException() {

//        arrange
        when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("Random");
        mockEmployeeDTO.setEmail("random@gmail.com");

//        assert and act
        assertThatThrownBy(() -> employeeService.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("The email of the employee cannot be updated");

        verify(employeeRepository).findById(mockEmployeeDTO.getId());
        verify(employeeRepository, never()).save(any());




    }

    @Test
    void testUpdateEmployee_whenValidEmployee_thenUpdateEmployee() {

        //        arrange
        when(employeeRepository.findById(mockEmployeeDTO.getId())).thenReturn(Optional.of(mockEmployee));
        mockEmployeeDTO.setName("Random name");
        mockEmployeeDTO.setSalary(199L);


        Employee newEmployee = modelMapper.map(mockEmployeeDTO, Employee.class);
        when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);
//        act
        EmployeeDTO updatedEmployeeDto = employeeService.updateEmployee(mockEmployeeDTO.getId(), mockEmployeeDTO);

        Assertions.assertThat(updatedEmployeeDto).isEqualTo(mockEmployeeDTO);

        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(any());
    }

    @Test
    void testDeleteEmployee_whenEmployeeDoesNotExists_thenThrowException() {

        when(employeeRepository.existsById(1L)).thenReturn(false);

//        act
        assertThatThrownBy(() -> employeeService.deleteEmployee(1L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Employee not found with id: 1");

        verify(employeeRepository, never()).deleteById(anyLong());



    }

    @Test
    void testDeleteEmployee_whenEmployeeIsValid_thenDeleteEmployee() {
//        arrange
        when(employeeRepository.existsById(1L)).thenReturn(true);

        assertThatCode(() -> employeeService.deleteEmployee(1L))
                .doesNotThrowAnyException();

        verify(employeeRepository).deleteById(1L);


    }

}