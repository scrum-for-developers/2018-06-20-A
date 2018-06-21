package de.codecentric.psd.worblehat.web.formdata;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import de.codecentric.psd.worblehat.web.validation.ISBN;
import de.codecentric.psd.worblehat.web.validation.Numeric;

/**
 * This class represent the form data of the add book form.
 */
public class BookDataFormData {

	@NotEmpty(message = "{empty.bookDataFormData.title}")
	private String title;

	@NotEmpty(message = "{empty.bookDataFormData.edition}")
	@Numeric(message = "{notvalid.bookDataFormData.edition}")
	private String edition;

	@NotEmpty(message = "{empty.bookDataFormData.yearOfPublication}")
	@Numeric(message = "{notvalid.bookDataFormData.yearOfPublication}")
	@Length(message = "{invalid.length.bookDataFormData.yearOfPublication}", min = 4, max = 4)
	private String yearOfPublication;

	@NotEmpty(message = "{empty.bookDataFormData.isbn}")
	@ISBN(message = "{notvalid.bookDataFormData.isbn}")
	private String isbn;

	@NotEmpty(message = "{empty.bookDataFormData.author}")
	private String author;

	public String getYearOfPublication() {
		return this.yearOfPublication;
	}

	public void setYearOfPublication(final String yearOfPublication) {
		this.yearOfPublication = yearOfPublication.trim();
	}

	public String getIsbn() {
		return this.isbn;
	}

	public void setIsbn(final String isbn) {
		this.isbn = isbn.trim();
	}

	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(final String author) {
		this.author = author.trim();
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title.trim();
	}

	public String getEdition() {
		return this.edition;
	}

	public void setEdition(final String edition) {
		this.edition = edition.trim();
	}

	@Override
	public String toString() {
		return "BookDataFormData [title=" + this.title + ", edition=" + this.edition + ", yearOfPublication="
				+ this.yearOfPublication + ", isbn=" + this.isbn + ", author=" + this.author + "]";
	}

}
