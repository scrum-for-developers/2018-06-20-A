package de.codecentric.psd.worblehat.domain;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BookTest {

    private static final String TITLE = "myBook";

    private static final String ISBN = "0123456789";

    private static final String AUTHOR = "Some guy";

    private static final String EDITION = "1";

    private static final int YEAR_OF_PUBLICATION = 2016;

    private static final Book book = new Book(TITLE, AUTHOR, EDITION, ISBN, YEAR_OF_PUBLICATION);

    @Test
    public void testBookHasTitle() throws Exception {
        assertThat(book.getTitle(), is(TITLE));
    }

    @Test
    public void testBookHasAuthor() throws Exception {
        assertThat(book.getAuthor(), is(AUTHOR));
    }

    @Test
    public void testBookHasISBN() throws Exception {
        assertThat(book.getIsbn(), is(ISBN));
    }

    @Test
    public void testBookHasEdition() throws Exception {
        assertThat(book.getEdition(), is(EDITION));
    }

    @Test
    public void testBookHasYearOfPublication() throws Exception {
        assertThat(book.getYearOfPublication(), is(YEAR_OF_PUBLICATION));
    }
}
