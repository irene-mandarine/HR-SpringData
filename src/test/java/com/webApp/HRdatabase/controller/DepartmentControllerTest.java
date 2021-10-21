package com.webApp.HRdatabase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.webApp.HRdatabase.data.Department;
import com.webApp.HRdatabase.repository.DepartmentRepository;
import com.webApp.HRdatabase.service.DepartmentService;
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
class DepartmentControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @LocalServerPort
    private String port;

    @Test
    void testNullNameDepartmentInsertion() throws URISyntaxException {
        Department department = new Department(null, "London");
        RequestEntity<Department> requestEntity = new RequestEntity<>(department,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/departments/"));
        ResponseEntity<Department> responseEntity = restTemplate
                .exchange(requestEntity, Department.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testEmptyNameDepartmentInsertion() throws URISyntaxException {
        Department department = new Department("", "London");
        RequestEntity<Department> requestEntity = new RequestEntity<>(department,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/departments/"));
        ResponseEntity<Department> responseEntity = restTemplate
                .exchange(requestEntity, Department.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void testBlankNameDepartmentInsertion() throws URISyntaxException {
        Department department = new Department("      ", "London");
        RequestEntity<Department> requestEntity = new RequestEntity<>(department,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/departments/"));
        ResponseEntity<Department> responseEntity = restTemplate
                .exchange(requestEntity, Department.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Rollback
    void testDepartmentInsertion() throws URISyntaxException {
        String name = "Devs";
        String location = "London";
        Department department = new Department(name, location);
        RequestEntity<Department> requestEntity = new RequestEntity<>(department,
                HttpMethod.POST,
                new URI("http://localhost:" + port + "/api/hr/departments/"));
        ResponseEntity<Department> responseEntity = restTemplate
                .exchange(requestEntity, Department.class);
        assertThat(responseEntity.getBody().getName()).isEqualTo(name);
        assertThat(responseEntity.getBody().getLocation()).isEqualTo(location);
        assertThat(responseEntity.getBody().getDepartmentId()).isNotNull();
            }

    @Test
    @Rollback
    void testGetAllDepartments() throws URISyntaxException, JsonProcessingException, JSONException {
        departmentRepository.deleteAll();
        Department d1 = departmentRepository
                .save(new Department("It", "Italy"));
        Department d2 = departmentRepository
                .save(new Department("It", "Ireland"));
        List<Department> expected = asList(d1, d2);
        ResponseEntity<String> responseEntity = restTemplate
                .getForEntity(new URI("http://localhost:" + port + "/api/hr/departments/"),
                        String.class);
        JSONAssert.assertEquals(objectMapper.writeValueAsString(expected),
                responseEntity.getBody(),
                JSONCompareMode.LENIENT);
    }

    @Test
    @Rollback
    void testGetOneDepartment() throws URISyntaxException {
        Department d1 = departmentRepository
                .save(new Department("It", "Italy"));
        ResponseEntity<Department> responseEntity = restTemplate
                .getForEntity(new URI("http://localhost:" + port + "/api/hr/departments/" + d1.getDepartmentId()),
                        Department.class);
        assertThat(responseEntity.getBody()).isEqualTo(d1);
    }

    @Test
    @Rollback
    void testUpdateDepartment() throws URISyntaxException {
        Department d1 = departmentRepository
                .save(new Department("Finance", "Italy"));
        RequestEntity<Department> requestEntity = new RequestEntity<>(d1,
                HttpMethod.PUT,
                new URI("http://localhost:" + port + "/api/hr/departments/" + d1.getDepartmentId()));
        ResponseEntity<Department> responseEntity = restTemplate
                .exchange(requestEntity, Department.class);
        assertThat(responseEntity.getBody()).isEqualTo(d1);
    }
}