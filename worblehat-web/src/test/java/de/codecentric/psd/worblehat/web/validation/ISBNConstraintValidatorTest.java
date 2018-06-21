package de.codecentric.psd.worblehat.web.validation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import javax.validation.ConstraintValidatorContext;

import org.junit.Before;
import org.junit.Test;

public class ISBNConstraintValidatorTest {

    private ISBNConstraintValidator isbnConstraintValidator;

    private ConstraintValidatorContext constraintValidatorContext;


    @Before
    public void setUp() throws Exception {
        this.isbnConstraintValidator = new ISBNConstraintValidator();
        this.constraintValidatorContext = mock(ConstraintValidatorContext.class);
    }

    @Test
    public void initializeShouldTakeIsbn() throws Exception {
        ISBN isbn= mock(ISBN.class);
        this.isbnConstraintValidator.initialize(isbn);
    }

    @Test
    public void shouldReturnTrueIfBlank() throws Exception {
        boolean actual = this.isbnConstraintValidator.isValid("", this.constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnTrueIfValidISBN10() throws Exception {
        boolean actual = this.isbnConstraintValidator.isValid("0132350882", this.constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalseIfInvalidISBN10() throws Exception {
        boolean actual = this.isbnConstraintValidator.isValid("0123459789", this.constraintValidatorContext);
        assertFalse(actual);
    }
    
    @Test
    public void shouldReturnTrueIfValidISBN13() throws Exception {
        boolean actual = this.isbnConstraintValidator.isValid("978-3-86680-192-9", this.constraintValidatorContext);
        assertTrue(actual);
    }

    @Test
    public void shouldReturnFalseIfInvalidISBN13() throws Exception {
        boolean actual = this.isbnConstraintValidator.isValid("978-3-86680-192-8", this.constraintValidatorContext);
        assertFalse(actual);
    }

}