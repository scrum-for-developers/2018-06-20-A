package de.codecentric.psd.worblehat.web.formdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BookBorrowFormDataTest {

	private static final String BLANK = " ";
	private static final String TEST_STRING = "12345678";

	@Test
	public void testIsbnWithLeadingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setIsbn(BLANK + TEST_STRING);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testIsbnWithTrailingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setIsbn(TEST_STRING + BLANK);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testIsbnWithLeadingAndTrailingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setIsbn(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testEmailWithLeadingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setEmail(BLANK + TEST_STRING);
		assertThat(formData.getEmail(), is(TEST_STRING));
	}

	@Test
	public void testEmailWithTrailingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setEmail(TEST_STRING + BLANK);
		assertThat(formData.getEmail(), is(TEST_STRING));
	}

	@Test
	public void testEmailWithLeadingAndTrailingBlanks() {
		final BookBorrowFormData formData = new BookBorrowFormData();

		formData.setEmail(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getEmail(), is(TEST_STRING));
	}

}
