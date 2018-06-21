package de.codecentric.psd.worblehat.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.ISBNValidator;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

	@Override
	public void initialize(ISBN constraintAnnotation) {
		// There is nothing to initialize
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// Don't validate null, empty and blank strings, since these are validated by @NotNull, @NotEmpty and @NotBlank
		if(StringUtils.isNotBlank(value)) {
			ISBNValidator validator = ISBNValidator.getInstance();
			final boolean isValidISBN10 = validator.isValidISBN10(value);
			final boolean isValidISBN13 = validator.isValidISBN13(value);
			return isValidISBN10 || isValidISBN13 ;
		}
		return true;
	}

}
