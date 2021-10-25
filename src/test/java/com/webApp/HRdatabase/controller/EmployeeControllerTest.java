package com.webApp.HRdatabase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webApp.HRdatabase.data.Department;
import com.webApp.HRdatabase.data.Employee;
import com.webApp.HRdatabase.repository.DepartmentRepository;
import com.webApp.HRdatabase.repository.EmployeeRepository;
import com.webApp.HRdatabase.service.DepartmentService;
import com.webApp.HRdatabase.service.EmployeeService;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private DepartmentService departmentService;

    @LocalServerPort
    private String port;

    @Test
    void testNullValuesEmployeeInsertion() throws URISyntaxException {
        Employee employee = new Employee("first",
                null,
                "nata@il.com",
                "5646",
                1,
                new Department("LPS", "NYC"));
        RequestEntity<Employee> requestEntity = new RequestEntity<>(employee,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testEmptyNameEmployeeInsertion() throws URISyntaxException {
        Employee employee = new Employee("first",
                "",
                "nata@il.com",
                "5646",
                56.0,
                new Department("LPS", "NYC"));
        RequestEntity<Employee> requestEntity = new RequestEntity<>(employee,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testBlankNameEmployeeInsertion() throws URISyntaxException {
        Employee employee = new Employee("first",
                "        ",
                "nata@il.com",
                "5646",
                56.0,
                new Department("LPS", "NYC"));
        RequestEntity<Employee> requestEntity = new RequestEntity<>(employee,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testNotValidSalaryEmployeeInsertion() throws URISyntaxException {
        Employee employee = new Employee("first",
                "last",
                "nata@il.com",
                "5646",
                -90,
                new Department("LPS", "NYC"));
        RequestEntity<Employee> requestEntity = new RequestEntity<>(employee,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Rollback
    void testEmployeeInsertion() throws URISyntaxException {
        String firstName = "Lana";
        String lastName = "Ion";
        Department department = new Department("LPS", "NYC");
        String email = "lana.ion@f.com";
        String phoneNumber = "569595";
        double salary = 79.0;
        Employee employee = new Employee(firstName, lastName, email,phoneNumber,salary,department);
        RequestEntity<Employee> requestEntity = new RequestEntity<>(employee,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getBody().getFirstName()).isEqualTo(firstName);
        assertThat(responseEntity.getBody().getLastName()).isEqualTo(lastName);
        assertThat(responseEntity.getBody().getDepartment()).isEqualTo(department);
        assertThat(responseEntity.getBody().getEmail()).isEqualTo(email);
        assertThat(responseEntity.getBody().getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(responseEntity.getBody().getSalary()).isEqualTo(salary);
        assertThat(responseEntity.getBody().getId()).isNotNull();
    }

    @Test
    @Rollback
    void testGetAllEmployees() throws URISyntaxException, JsonProcessingException, JSONException {
        employeeRepository.deleteAll();
        Employee d1 = employeeRepository
                .save(new Employee("first",
                        "last",
                        "fl@mail.com",
                        "565656",
                        56.0,
                        new Department("LPS", "NYC")));
        Employee d2 = employeeRepository
                .save(new Employee("first1",
                        "last1",
                        "fl1@mail.com",
                        "565657",
                        56.0,
                        new Department("LPS", "NYC")));
        List<Employee> expected = asList(d1, d2);
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(new URI("http://localhost:" + port + "/api/hr/employees/"),
                        String.class);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected),
                responseEntity.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    @Rollback
    void testGetOneEmployee() throws URISyntaxException {
        employeeRepository.deleteAll();
        Employee e1 = employeeRepository
                .save(new Employee("first",
                        "last",
                        "fl@mail.com",
                        "565656",
                        56.0,
                        new Department("LPS", "NYC")));
        System.out.println(employeeRepository.findById(e1.getId()));
        ResponseEntity<Employee> responseEntity = restTemplate
                .getForEntity(new URI("http://localhost:" + port + "/api/hr/employees/" + e1.getId()),
                        Employee.class);
        assertThat(responseEntity.getBody()).isEqualTo(e1);
    }

    @Test
    @Rollback
    void testUpdateEmployee() throws URISyntaxException {
        employeeRepository.deleteAll();
        Employee e1 = employeeRepository
                .save(new Employee("first",
                        "last",
                        "fl@mail.com",
                        "565656",
                        56.0,
                        new Department("LPS", "NYC")));
        e1.setPhoneNumber("569785");
        RequestEntity<Employee> requestEntity = new RequestEntity<>(e1,
                HttpMethod.PUT,
                new URI("http://localhost:" + port + "/api/hr/employees/" + e1.getId()));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getBody()).isEqualTo(e1);
    }

    @Test
    @Rollback
    void testAddEmployeeSameEmail() throws URISyntaxException {
        employeeRepository.deleteAll();
        Employee e1 = employeeRepository
                .save(new Employee("first",
                        "last",
                        "fl@mail.com",
                        "565656",
                        56.0,
                        new Department("LPS", "NYC")));
        Employee e2 =new Employee("first",
                "last",
                "fl@mail.com",
                "565656",
                56.0,
                new Department("LPS", "NYC"));
        RequestEntity<Employee> requestEntity = new RequestEntity<>(e2,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/employees/"));
        ResponseEntity<Employee> responseEntity = restTemplate
                .exchange(requestEntity, Employee.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }
}