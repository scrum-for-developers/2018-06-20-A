package de.codecentric.psd.worblehat.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookTest {

    Book BOOK;

    @Before
    public void setup() {
        BOOK = new Book("Titel", "Author", "2", "1", 1234, "test");
    }

    @Test
    public void shouldReturnFalseWhenAuthorisDifferent() {
        Book anotherCopy = new Book(BOOK.getTitle(), BOOK.getAuthor(), BOOK.getEdition(), BOOK.getIsbn(), BOOK.getYearOfPublication(), "test");
        anotherCopy.setAuthor("Bene");
        assertThat(BOOK.isSameCopy(anotherCopy), is(false));
    }

    @Test
    public void shouldReturnFalseWhenTitleisDifferent() {
        Book anotherCopy = new Book(BOOK.getTitle(), BOOK.getAuthor(), BOOK.getEdition(), BOOK.getIsbn(), BOOK.getYearOfPublication(), "test");
        anotherCopy.setTitle("Lord of the Rings");
        assertThat(BOOK.isSameCopy(anotherCopy), is(false));
    }
    @Test
    public void shouldReturnFalseWhenEditionisDifferent() {
        Book anotherCopy = new Book(BOOK.getTitle(), BOOK.getAuthor(), BOOK.getEdition(), BOOK.getIsbn(), BOOK.getYearOfPublication(), "test");
        anotherCopy.setEdition("3");
        assertThat(BOOK.isSameCopy(anotherCopy), is(false));
    }

    @Test
    public void shouldReturnTrueWhenAllButTitleEditionAndAuthorAreDifferent() {
        Book anotherCopy = new Book(BOOK.getTitle(), BOOK.getAuthor(), BOOK.getEdition(), BOOK.getIsbn(), BOOK.getYearOfPublication(), "test");
        anotherCopy.setIsbn("123456789X");
        anotherCopy.setYearOfPublication(2010);
        assertThat(BOOK.isSameCopy(anotherCopy), is(true));
    }

    @Test
    public void shouldBeBorrowable() {
        BOOK.borrowNowByBorrower("a@bc.de");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }

    @Test
    public void shouldIgnoreNewBorrowWhenBorrowed() {
        BOOK.borrowNowByBorrower("a@bc.de");
        BOOK.borrowNowByBorrower("a@bc.ru");
        assertThat(BOOK.getBorrowing().getBorrowerEmailAddress(), is("a@bc.de"));
    }
}