package de.codecentric.psd.worblehat.web.formdata;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class BookDataFormDataTest {

	private static final String BLANK = " ";
	private static final String TEST_STRING = "12345678";

	@Test
	public void testIsbnWithLeadingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setIsbn(BLANK + TEST_STRING);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testIsbnWithTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setIsbn(TEST_STRING + BLANK);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testIsbnWithLeadingAndTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setIsbn(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getIsbn(), is(TEST_STRING));
	}

	@Test
	public void testAuthorWithLeadingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setAuthor(BLANK + TEST_STRING);
		assertThat(formData.getAuthor(), is(TEST_STRING));
	}

	@Test
	public void testAuthorWithTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setAuthor(TEST_STRING + BLANK);
		assertThat(formData.getAuthor(), is(TEST_STRING));
	}

	@Test
	public void testAuthorWithLeadingAndTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setAuthor(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getAuthor(), is(TEST_STRING));
	}

	@Test
	public void testEditionWithLeadingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setEdition(BLANK + TEST_STRING);
		assertThat(formData.getEdition(), is(TEST_STRING));
	}

	@Test
	public void testEditionWithTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setEdition(TEST_STRING + BLANK);
		assertThat(formData.getEdition(), is(TEST_STRING));
	}

	@Test
	public void testEditionWithLeadingAndTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setEdition(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getEdition(), is(TEST_STRING));
	}

	@Test
	public void testTtileWithLeadingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setTitle(BLANK + TEST_STRING);
		assertThat(formData.getTitle(), is(TEST_STRING));
	}

	@Test
	public void testTitleWithTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setTitle(TEST_STRING + BLANK);
		assertThat(formData.getTitle(), is(TEST_STRING));
	}

	@Test
	public void testTitleWithLeadingAndTrailingBlanks() {
		final BookDataFormData formData = new BookDataFormData();

		formData.setTitle(BLANK + TEST_STRING + BLANK);
		assertThat(formData.getTitle(), is(TEST_STRING));
	}

}
