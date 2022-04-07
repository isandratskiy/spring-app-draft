package io.sandratskyi.spring.draft.service;

import io.sandratskyi.spring.draft.StudentResolver;
import io.sandratskyi.spring.draft.entity.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Testcontainers
@ActiveProfiles("integration")
@ExtendWith(StudentResolver.class)
@DisplayName("Student Service Test -- ")
class StudentServiceTest {
    @Autowired
    private StudentService service;

    @Test
    @DisplayName("must get all students")
    void getStudents(Student student) {
        service.addStudent(student);
        var students = service.getStudents();
        assertFalse(students.isEmpty());
    }

    @Test
    @DisplayName("must add student")
    void addStudent(Student student) {
        service.addStudent(student);
        var addedStudent = service.getStudents()
                .stream()
                .filter(it -> it.getEmail().equals(student.getEmail()))
                .findAny();

        assertTrue(addedStudent.isPresent(), "have no student!");
    }

    @Test
    @DisplayName("must delete student")
    void deleteStudent(Student student) {
        service.addStudent(student);
        service.deleteStudent(1L);
        var addedStudent = service.getStudents()
                .stream()
                .filter(it -> it.getEmail().equals(student.getEmail()))
                .findAny();

        assertFalse(addedStudent.isPresent());
    }

    @Test
    @DisplayName("must update student")
    void updateStudent(Student student) {
        service.addStudent(student);
        var studentId = service.getStudents().stream().findAny().get().getId();

        service.updateStudent(studentId, "Hank", "hank@gmail.com");

        var addedStudent = service.getStudents()
                .stream()
                .filter(it -> it.getId() == studentId)
                .findAny()
                .get();

        assertEquals(addedStudent.getName(), "Hank", "have no student!");
    }
}
