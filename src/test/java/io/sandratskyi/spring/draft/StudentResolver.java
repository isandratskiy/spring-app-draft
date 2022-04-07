package io.sandratskyi.spring.draft;

import io.sandratskyi.spring.draft.entity.Student;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;

import java.util.Random;

import static java.time.LocalDate.of;
import static java.time.Month.APRIL;

public class StudentResolver implements ParameterResolver {
    @Override
    public boolean supportsParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return parameterContext.getParameter().getType() == Student.class;
    }

    @Override
    public Object resolveParameter(ParameterContext parameterContext, ExtensionContext extensionContext) {
        return new Student(
                new Random().nextLong(),
                "Bob",
                "dummy_" + new Random().nextInt() + "@gmail.com",
                of(1988, APRIL, 5));
    }
}
