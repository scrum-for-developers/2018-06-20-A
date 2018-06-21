package de.codecentric.psd.worblehat.web.formdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ReturnAllBooksFormDataTest {

	private static final String BLANK = " ";
	private static final String TEST_STRING = "12345678";

	@Test
	public void testEmailAddressWithLeadingBlanks() {
		final ReturnAllBooksFormData formData = new ReturnAllBooksFormData();

		formData.setEmailAddress(BLANK + TEST_STRING);
		assertThat(formData.getEmailAddress(), is(TEST_STRING));
	}

	@Test
	public void testEmailAddressWithTrailingBlanks() {
		final ReturnAllBooksFormData formData = new ReturnAllBooksFormData();

		formData.setEmailAddress(TEST_STRING + BLANK);
		assertThat(formData.getEmailAddress(), is(TEST_STRING));
	}

	@Test
	public void testEmaiAddresslWithLeadingAndTrailingBlanks() {
		final ReturnAllBooksFormData formData = new ReturnAllBooksFormData();

		formData.setEmailAddress(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getEmailAddress(), is(TEST_STRING));
	}

}
