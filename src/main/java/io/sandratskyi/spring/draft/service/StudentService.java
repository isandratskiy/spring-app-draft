package io.sandratskyi.spring.draft.service;

import io.sandratskyi.spring.draft.entity.Student;
import io.sandratskyi.spring.draft.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository repository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.repository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.repository.findAll();
    }

    public void addStudent(Student student) {
        var takenStudent = this.repository.findStudentByEmail(student.getEmail());
        if (takenStudent.isPresent())
            throw new IllegalStateException("Email is used already");
        this.repository.save(student);
    }

    public void deleteStudent(Long id) {
        var takenStudent = this.repository.findById(id);
        if (takenStudent.isEmpty())
            throw new IllegalStateException("Unable to find student with id: " + id);
        this.repository.delete(takenStudent.get());
    }

    @Transactional
    public void updateStudent(
            Long id,
            String name,
            String email) {
        var student = this.repository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "Unable to find student with id: " + id));

        if (name != null
                && name.length() > 0
                && !student.getName().equals(name))
            student.setName(name);

        if (email != null
                && email.length() > 0
                && !student.getEmail().equals(email))
            student.setEmail(email);
    }
}
