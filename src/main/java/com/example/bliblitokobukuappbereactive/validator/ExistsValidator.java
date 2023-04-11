package com.example.bliblitokobukuappbereactive.validator;

import com.example.bliblitokobukuappbereactive.constraint.ExistsConstraint;
import com.example.bliblitokobukuappbereactive.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.concurrent.ExecutionException;

public class ExistsValidator implements ConstraintValidator<ExistsConstraint, String> {

    @Autowired
    BookRepository bookRepository;

    @Override
    public void initialize(ExistsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String bookId, ConstraintValidatorContext context) {
        try {
            return bookRepository
                    .existsById(bookId)
                    .toFuture().get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

}
