package de.codecentric.psd.worblehat.web.formdata;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import de.codecentric.psd.worblehat.web.validation.ISBN;

/**
 * Form data object from the borrow view.
 */
public class BookBorrowFormData {

	@NotEmpty(message = "{empty.borrowCmd.isbn}")
	@ISBN(message = "{notvalid.borrowCmd.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.borrowCmd.email}")
	@Email(message = "{notvalid.borrowCmd.email}")
	private String email;

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(final String isbn) {
		this.isbn = isbn.trim();
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email.trim();
	}

}
