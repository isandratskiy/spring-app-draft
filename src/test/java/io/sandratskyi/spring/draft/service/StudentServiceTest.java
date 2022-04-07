package io.sandratskyi.spring.draft.service;

import io.sandratskyi.spring.draft.StudentResolver;
import io.sandratskyi.spring.draft.entity.Student;
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
class StudentServiceTest {
    @Autowired
    private StudentService service;

    @Test
    void getStudents(Student student) {
        service.addStudent(student);
        var students = service.getStudents();
        assertFalse(students.isEmpty());
    }

    @Test
    void addStudent(Student student) {
        service.addStudent(student);
        var addedStudent = service.getStudents()
                .stream()
                .filter(it -> it.getEmail().equals(student.getEmail()))
                .findAny();

        assertTrue(addedStudent.isPresent(), "have no student!");
    }

    @Test
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
