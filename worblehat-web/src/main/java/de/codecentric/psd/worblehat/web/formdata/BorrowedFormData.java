package de.codecentric.psd.worblehat.web.formdata;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Form data object from the borrow view.
 */
public class BorrowedFormData {

	@NotEmpty(message = "{empty.borrowCmd.email}")
	@Email(message = "{notvalid.borrowCmd.email}")
	private String email;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

}
