package de.codecentric.psd.worblehat.domain;

import static com.github.npathai.hamcrestopt.OptionalMatchers.isEmpty;
import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

public class StandardBookServiceTest {

	private BorrowingRepository borrowingRepository;

	private BookRepository bookRepository;

	private BookService bookService;

	private static final String BORROWER_EMAIL = "someone@codecentric.de";

	private static final DateTime NOW = DateTime.now();

	private Book aBook, aCopyofBook, anotherBook;

	private Book aBorrowedBook, aCopyofBorrowedBook, anotherBorrowedBook;
	private Borrowing aBorrowing, aBorrowingOfCopy, anotherBorrowing;

	@Before
	public void setup() {
		this.aBook = new Book("title", "author", "edition", "isbn", 2016, "test");
		this.aCopyofBook = new Book("title", "author", "edition", "isbn", 2016, "test");
		this.anotherBook = new Book("title2", "author2", "edition2", "isbn2", 2016, "test");

		this.aBorrowedBook = new Book("title", "author", "edition", "isbn", 2016, "test");
		this.aBorrowing = new Borrowing(this.aBorrowedBook, BORROWER_EMAIL, NOW);
		this.aBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		this.aCopyofBorrowedBook = new Book("title", "author", "edition", "isbn", 2016, "test");
		this.aBorrowingOfCopy = new Borrowing(this.aCopyofBorrowedBook, BORROWER_EMAIL, NOW);
		this.aCopyofBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		this.anotherBorrowedBook = new Book("title2", "author2", "edition2", "isbn2", 2016, "test");
		this.anotherBorrowing = new Borrowing(this.anotherBorrowedBook, BORROWER_EMAIL, NOW);
		this.anotherBorrowedBook.borrowNowByBorrower(BORROWER_EMAIL);

		this.bookRepository = mock(BookRepository.class);

		this.borrowingRepository = mock(BorrowingRepository.class);
		when(this.borrowingRepository.findBorrowingsByBorrower(BORROWER_EMAIL))
				.thenReturn(Arrays.asList(this.aBorrowing, this.anotherBorrowing));

		when(this.borrowingRepository.findBorrowingForBook(this.aBook)).thenReturn(null);

		this.bookService = new StandardBookService(this.borrowingRepository, this.bookRepository);

	}

	private void givenALibraryWith(final Book... books) {
		Map<String, Set<Book>> bookCopies = new HashMap<>();
		for (Book book : books) {
			if (!bookCopies.containsKey(book.getIsbn())) {
				bookCopies.put(book.getIsbn(), new HashSet<>());
			}
			bookCopies.get(book.getIsbn()).add(book);
		}
		for (Map.Entry<String, Set<Book>> entry : bookCopies.entrySet()) {
			when(this.bookRepository.findByIsbn(entry.getKey())).thenReturn(entry.getValue());
			when(this.bookRepository.findTopByIsbn(entry.getKey()))
					.thenReturn(Optional.of(entry.getValue().iterator().next()));
		}
	}

	@Test
	public void shouldReturnAllBooksOfOnePerson() {
		this.bookService.returnAllBooksByBorrower(BORROWER_EMAIL);
		verify(this.borrowingRepository).delete(this.anotherBorrowing);
	}

	@Test
	public void shouldSaveBorrowingWithBorrowerEmail() {
		this.givenALibraryWith(this.aBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		this.bookService.borrowBook(this.aBook.getIsbn(), BORROWER_EMAIL);
		verify(this.borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), equalTo(BORROWER_EMAIL));
	}

	@Test()
	public void shouldNotBorrowWhenBookAlreadyBorrowed() {
		this.givenALibraryWith(this.aBorrowedBook);
		Optional<Borrowing> borrowing = this.bookService.borrowBook(this.aBorrowedBook.getIsbn(), BORROWER_EMAIL);
		assertTrue(!borrowing.isPresent());
	}

	@Test
	public void shouldSelectOneOfTwoBooksWhenBothAreNotBorrowed() {
		this.givenALibraryWith(this.aBook, this.aCopyofBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		this.bookService.borrowBook(this.aBook.getIsbn(), BORROWER_EMAIL);
		verify(this.borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(),
				either(is(this.aBook)).or(is(this.aCopyofBook)));
	}

	@Test
	public void shouldSelectUnborrowedOfTwoBooksWhenOneIsBorrowed() {
		this.givenALibraryWith(this.aBook, this.aBorrowedBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		this.bookService.borrowBook(this.aBook.getIsbn(), BORROWER_EMAIL);
		verify(this.borrowingRepository).save(borrowingArgumentCaptor.capture());
		assertThat(borrowingArgumentCaptor.getValue().getBorrowerEmailAddress(), is(BORROWER_EMAIL));
		assertThat(borrowingArgumentCaptor.getValue().getBorrowedBook(), is(this.aBook));
	}

	@Test
	public void shouldThrowExceptionWhenAllBooksAreBorrowedRightNow() {
		this.givenALibraryWith(this.aBorrowedBook, this.aCopyofBorrowedBook);
		ArgumentCaptor<Borrowing> borrowingArgumentCaptor = ArgumentCaptor.forClass(Borrowing.class);
		Optional<Borrowing> borrowing = this.bookService.borrowBook(this.aBorrowedBook.getIsbn(), BORROWER_EMAIL);
		assertThat(borrowing, isEmpty());
		verify(this.borrowingRepository, never()).save(any(Borrowing.class));
	}

	@Test
	public void shouldCreateBook() {
		when(this.bookRepository.save(any(Book.class))).thenReturn(this.aBook);
		this.bookService.createBook(this.aBook.getTitle(), this.aBook.getAuthor(), this.aBook.getEdition(),
				this.aBook.getIsbn(), this.aBook.getYearOfPublication(), this.aBook.getDescription());

		// assert that book was saved to repository
		ArgumentCaptor<Book> bookArgumentCaptor = ArgumentCaptor.forClass(Book.class);
		verify(this.bookRepository).save(bookArgumentCaptor.capture());

		// assert that the information was passed correctly to create the book
		assertThat(bookArgumentCaptor.getValue().getTitle(), is(this.aBook.getTitle()));
		assertThat(bookArgumentCaptor.getValue().getAuthor(), is(this.aBook.getAuthor()));
		assertThat(bookArgumentCaptor.getValue().getEdition(), is(this.aBook.getEdition()));
		assertThat(bookArgumentCaptor.getValue().getIsbn(), is(this.aBook.getIsbn()));
		assertThat(bookArgumentCaptor.getValue().getYearOfPublication(), is(this.aBook.getYearOfPublication()));
		assertThat(bookArgumentCaptor.getValue().getDescription(), is(this.aBook.getDescription()));
	}

	@Test
	public void shouldCreateAnotherCopyOfExistingBook() {
		when(this.bookRepository.save(any(Book.class))).thenReturn(this.aBook);
		this.bookService.createBook(this.aBook.getTitle(), this.aBook.getAuthor(), this.aBook.getEdition(),
				this.aBook.getIsbn(), this.aBook.getYearOfPublication(), this.aBook.getDescription());
		verify(this.bookRepository, times(1)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentTitle() {
		this.givenALibraryWith(this.aBook);
		this.bookService.createBook(this.aBook.getTitle() + "X", this.aBook.getAuthor(), this.aBook.getEdition(),
				this.aBook.getIsbn(), this.aBook.getYearOfPublication(), this.aBook.getDescription());
		verify(this.bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldNotCreateAnotherCopyOfExistingBookWithDifferentAuthor() {
		this.givenALibraryWith(this.aBook);
		this.bookService.createBook(this.aBook.getTitle(), this.aBook.getAuthor() + "X", this.aBook.getEdition(),
				this.aBook.getIsbn(), this.aBook.getYearOfPublication(), this.aBook.getDescription());
		verify(this.bookRepository, times(0)).save(any(Book.class));
	}

	@Test
	public void shouldFindAllBooks() {
		List<Book> expectedBooks = new ArrayList<>();
		expectedBooks.add(this.aBook);
		when(this.bookRepository.findAllByOrderByTitle()).thenReturn(expectedBooks);
		List<Book> actualBooks = this.bookService.findAllBooks();
		assertThat(actualBooks, is(expectedBooks));
	}

	@Test
	public void shouldVerifyExistingBooks() {
		when(this.bookRepository.findByIsbn(this.aBook.getIsbn())).thenReturn(Collections.singleton(this.aBook));
		Boolean bookExists = this.bookService.bookExists(this.aBook.getIsbn());
		assertTrue(bookExists);
	}

	@Test
	public void shouldVerifyNonexistingBooks() {
		when(this.bookRepository.findByIsbn(this.aBook.getIsbn())).thenReturn(Collections.emptySet());
		Boolean bookExists = this.bookService.bookExists(this.aBook.getIsbn());
		assertThat(bookExists, is(false));
	}

	@Test
	public void shouldDeleteAllBooksAndBorrowings() {
		this.bookService.deleteAllBooks();
		verify(this.bookRepository).deleteAll();
		verify(this.borrowingRepository).deleteAll();
	}
}
