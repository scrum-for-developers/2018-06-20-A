package de.codecentric.psd.worblehat.domain;

import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateTime;

/**
 * Borrowing Entity
 */
@Entity
public class Borrowing implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id; // NOSONAR

	private String borrowerEmailAddress;

	@Temporal(TemporalType.DATE)
	private Date borrowDate;

	@OneToOne()
	private Book borrowedBook;

	/**
	 * @param book
	 *            The borrowed book
	 * @param borrowerEmailAddress
	 *            The borrowers e-mail Address
	 * @param borrowDate
	 *            The borrow date
	 */
	public Borrowing(final Book book, final String borrowerEmailAddress, final DateTime borrowDate) {
		super();
		this.borrowedBook = book;
		this.borrowerEmailAddress = borrowerEmailAddress;
		this.borrowDate = borrowDate.toDate();
	}

	public Borrowing(final Book book, final String borrowerEmailAddress) {
		this(book, borrowerEmailAddress, DateTime.now());
	}

	private Borrowing() {
		// for JPA
	}

	public Book getBorrowedBook() {
		return this.borrowedBook;
	}

	public String getBorrowerEmailAddress() {
		return this.borrowerEmailAddress;
	}

	public Date getBorrowDate() {
		return this.borrowDate;
	}

	public Date getReturnDate() {
		Instant tempDate = this.borrowDate.toInstant();
		tempDate.plus(3, ChronoUnit.WEEKS);
		return Date.from(tempDate);
	}
}
