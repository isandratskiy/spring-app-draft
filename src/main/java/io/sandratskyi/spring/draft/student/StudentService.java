package io.sandratskyi.spring.draft.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        return this.studentRepository.findAll();
    }

    public void addStudent(Student student) {
        Optional<Student> takenStudent = this.studentRepository.findStudentByEmail(student.getEmail());
        if (takenStudent.isPresent()) throw new IllegalStateException("Email is used already");
        this.studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Optional<Student> takenStudent = this.studentRepository.findById(id);
        if (takenStudent.isEmpty()) throw new IllegalStateException("Unable to find student with id: " + id);
        this.studentRepository.delete(takenStudent.get());
    }

    @Transactional
    public void updateStudent(
            Long id,
            String name,
            String email) {
        Student student = this.studentRepository.findById(id)
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
