package io.sandratskyi.spring.draft.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.time.LocalDate.of;
import static java.time.Month.APRIL;
import static java.util.List.of;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            var bob = new Student(
                    1L,
                    "Bob",
                    "bob@dummy.com",
                    of(1988, APRIL, 5));

            var sara = new Student(
                    2L,
                    "Sara",
                    "sara@dummy.com",
                    of(1996, APRIL, 5));

            repository.saveAll(of(bob, sara));
        };
    }
}
