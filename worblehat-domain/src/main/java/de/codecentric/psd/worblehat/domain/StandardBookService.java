package de.codecentric.psd.worblehat.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The domain service class for book operations.
 */
@Service
@Transactional
public class StandardBookService implements BookService {

	public StandardBookService() {
		// constructor is intentionally left blank
	}

	@Autowired
	public StandardBookService(final BorrowingRepository borrowingRepository, final BookRepository bookRepository) {
		this.borrowingRepository = borrowingRepository;
		this.bookRepository = bookRepository;
	}

	private BorrowingRepository borrowingRepository;

	private BookRepository bookRepository;

	@Override
	public List<Borrowing> getBorrowingsByBorrower(final String borrowerEmailAddress) {
		return this.borrowingRepository.findBorrowingsByBorrower(borrowerEmailAddress);
	}

	@Override
	public void returnAllBooksByBorrower(final String borrowerEmailAddress) {
		List<Borrowing> borrowingsByUser = this.borrowingRepository.findBorrowingsByBorrower(borrowerEmailAddress);
		for (Borrowing borrowing : borrowingsByUser) {
			this.borrowingRepository.delete(borrowing);
		}
	}

	@Override
	public void returnOneBookByBorrower(final String borrowerEmailAddress, final String isbn) {
		List<Borrowing> borrowingsByUser = this.borrowingRepository.findBorrowingsByBorrower(borrowerEmailAddress);
		for (Borrowing borrowing : borrowingsByUser) {
			if (StringUtils.equals(borrowing.getBorrowedBook().getIsbn(), isbn)) {
				this.borrowingRepository.delete(borrowing);
			}
		}
	}

	@Override
	public Optional<Borrowing> borrowBook(final String isbn, final String borrower) {
		Set<Book> books = this.bookRepository.findByIsbn(isbn);

		Optional<Book> unborrowedBook = books.stream().filter(book -> book.getBorrowing() == null).findFirst();

		return unborrowedBook.map(book -> {
			book.borrowNowByBorrower(borrower);
			this.borrowingRepository.save(book.getBorrowing());
			return book.getBorrowing();
		});
	}

	@Override
	public Set<Book> findBooksByIsbn(final String isbn) {
		return this.bookRepository.findByIsbn(isbn); // null if not found
	}

	@Override
	public List<Book> findAllBooks() {
		return this.bookRepository.findAllByOrderByTitle();
	}

	@Override
	public Optional<Book> createBook(@Nonnull final String title, @Nonnull final String author,
			@Nonnull final String edition, @Nonnull final String isbn, final int yearOfPublication,
			@Nonnull final String description) {
		Book book = new Book(title, author, edition, isbn, yearOfPublication, description);

		Optional<Book> bookFromRepo = this.bookRepository.findTopByIsbn(isbn);

		if (!bookFromRepo.isPresent() || book.isSameCopy(bookFromRepo.get())) {
			return Optional.of(this.bookRepository.save(book));
		} else {
			return Optional.empty();
		}
	}

	@Override
	public boolean bookExists(final String isbn) {
		Set<Book> books = this.bookRepository.findByIsbn(isbn);
		return !books.isEmpty();
	}

	@Override
	public void deleteAllBooks() {
		this.borrowingRepository.deleteAll();
		this.bookRepository.deleteAll();
	}

}
