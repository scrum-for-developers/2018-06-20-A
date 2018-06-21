package de.codecentric.worblehat.acceptancetests.step.business;

import de.codecentric.psd.worblehat.domain.Book;

public class DemoBookFactory {

	private Book book;

	private DemoBookFactory() {
		this.book = new Book("A book title", "A book author", "1", "1234567890", 2013, "Description");
	}

	public static DemoBookFactory createDemoBook() {
		return new DemoBookFactory();
	}

	public DemoBookFactory withTitle(final String title) {
		this.book.setTitle(title);
		return this;
	}

	public DemoBookFactory withAuthor(final String author) {
		this.book.setAuthor(author);
		return this;
	}

	public DemoBookFactory withEdition(final String edition) {
		this.book.setEdition(edition);
		return this;
	}

	public DemoBookFactory withISBN(final String isbn) {
		this.book.setIsbn(isbn);
		return this;
	}

	public DemoBookFactory withYearOfPublication(final String year) {
		this.book.setYearOfPublication(Integer.parseInt(year));
		return this;
	}

	public DemoBookFactory withYearOfPublication(final int yearOfPublication) {
		this.book.setYearOfPublication(yearOfPublication);
		return this;
	}

	public Book build() {
		return this.book;
	}

}
