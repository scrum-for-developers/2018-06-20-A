package de.codecentric.worblehat.acceptancetests.step.business;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;

@Component("Library")
public class Library {

	private BookService bookService;

	@Autowired
	public Library(final ApplicationContext applicationContext) {
		this.bookService = applicationContext.getBean(BookService.class);
	}

	// *******************
	// *** G I V E N *****
	// *******************

	@Given("an empty library")
	public void emptyLibrary() {
		this.bookService.deleteAllBooks();
	}

	@Given("a library, containing a book with isbn $isbn")
	public void createLibraryWithSingleBookWithGivenIsbn(final String isbn) {
		Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
		this.bookService.createBook(book.getTitle(), book.getAuthor(), book.getEdition(), isbn,
				book.getYearOfPublication(), book.getDescription());
	}

	// just an example of how a step looks that is different from another one,
	// after the last parameter
	// see configuration in AllAcceptanceTestStories
	@Given("a library, containing a book with isbn $isbn and title $title")
	public void createLibraryWithSingleBookWithGivenIsbnAndTitle(final String isbn, final String title) {
		Book book = DemoBookFactory.createDemoBook().withISBN(isbn).withTitle(title).build();
		this.bookService.createBook(book.getTitle(), book.getAuthor(), book.getEdition(), isbn,
				book.getYearOfPublication(), book.getDescription());
	}

	@Given("borrower $borrower has borrowed books $isbns")
	public void borrower1HasBorrowerdBooks(final String borrower, final String isbns) {
		this.borrowerHasBorrowedBooks(borrower, isbns);
	}

	public void borrowerHasBorrowedBooks(final String borrower, final String isbns) {
		List<String> isbnList = this.getListOfItems(isbns);
		for (String isbn : isbnList) {
			Book book = DemoBookFactory.createDemoBook().withISBN(isbn).build();
			this.bookService.createBook(book.getTitle(), book.getAuthor(), book.getEdition(), isbn,
					book.getYearOfPublication(), book.getDescription()).orElseThrow(IllegalStateException::new);

			this.bookService.borrowBook(book.getIsbn(), borrower);
		}
	}

	private List<String> getListOfItems(final String isbns) {
		return isbns.isEmpty() ? Collections.emptyList() : Arrays.asList(isbns.split(" "));
	}
	// *****************
	// *** W H E N *****
	// *****************

	// *****************
	// *** T H E N *****
	// *****************

	@Then("the library contains only the book with $isbn")
	public void shouldContainOnlyOneBook(final String isbn) {
		this.waitForServerResponse();
		List<Book> books = this.bookService.findAllBooks();
		assertThat(books.size(), is(1));
		assertThat(books.get(0).getIsbn(), is(isbn));
	}

	@Then("the library contains $copies of the book with $isbn")
	public void shouldContainCopiesOfBook(final Integer copies, final String isbn) {
		this.waitForServerResponse();
		Set<Book> books = this.bookService.findBooksByIsbn(isbn);
		assertThat(books.size(), is(copies));
		assertThat(books, everyItem(hasProperty("isbn", is(isbn))));
	}

	private void waitForServerResponse() {
		// normally you would have much better mechanisms for waiting for a
		// server response. We are choosing a simple solution for the sake of
		// this
		// training
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// pass
		}
	}

}
