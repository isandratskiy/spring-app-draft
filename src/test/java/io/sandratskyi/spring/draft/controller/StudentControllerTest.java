package io.sandratskyi.spring.draft.controller;

import io.sandratskyi.spring.draft.StudentResolver;
import io.sandratskyi.spring.draft.entity.Student;
import io.sandratskyi.spring.draft.service.StudentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static java.time.LocalDate.of;
import static java.time.Month.APRIL;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@Testcontainers
@ActiveProfiles("integration")
@ExtendWith(StudentResolver.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@DisplayName("Student Controller --")
class StudentControllerTest {
    @Autowired
    public TestRestTemplate restTemplate;
    @Autowired
    private StudentService service;

    @Test
    @DisplayName("must get all student")
    void getStudents(Student student) {
        service.addStudent(student);
        var all = restTemplate.getForEntity("/api/v1/student", Student[].class);
        assertFalse(stream(all.getBody()).findAny().isEmpty());
    }

    @Test
    @DisplayName("must register student")
    void registerStudent(Student student) {
        var response = restTemplate.postForEntity("/api/v1/student", student, null);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    @DisplayName("must delete student")
    void deleteStudent(Student student) {
        service.addStudent(student);
        var beforeDelete = restTemplate.getForEntity("/api/v1/student", Student[].class).getBody().length;

        restTemplate.delete("/api/v1/student/1");
        var afterDelete = restTemplate.getForEntity("/api/v1/student", Student[].class).getBody().length;
        assertNotEquals(beforeDelete, afterDelete);
    }

    @Test
    @DisplayName("must update student")
    void updateStudent(Student student) {
        service.addStudent(student);
        var studentId = stream(restTemplate.getForEntity("/api/v1/student", Student[].class).getBody())
                .findFirst()
                .get()
                .getId();

        var updatePayload = new Student(
                1L,
                "Hank",
                "hank@gmail.com",
                of(1988, APRIL, 5));

        restTemplate.put("/api/v1/student/" + studentId, updatePayload);
        var updatedStudent = stream(
                restTemplate.getForEntity("/api/v1/student", Student[].class).getBody())
                .filter(it -> it.getId() == studentId)
                .findAny();

        assertTrue(updatedStudent.isPresent());
    }
}
