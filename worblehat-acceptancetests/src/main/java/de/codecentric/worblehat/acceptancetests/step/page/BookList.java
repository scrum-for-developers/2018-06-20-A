package de.codecentric.worblehat.acceptancetests.step.page;

import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBook;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.HtmlBookList;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.Page;
import de.codecentric.worblehat.acceptancetests.adapter.SeleniumAdapter;
import de.codecentric.worblehat.acceptancetests.adapter.wrapper.PageElement;
import de.codecentric.worblehat.acceptancetests.step.business.DemoBookFactory;
import de.codecentric.psd.worblehat.domain.Book;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@Component("BookList")
public class BookList {

    private SeleniumAdapter seleniumAdapter;

    @Autowired
    public BookList(SeleniumAdapter seleniumAdapter){
        this.seleniumAdapter = seleniumAdapter;
    }

    @Then("the booklist contains a book with values title $title, author $author, year $year, edition $edition, isbn $isbn")
    public void bookListContainsRowWithValues(final String title,
                                              final String author,
                                              final String year,
                                              final String edition,
                                              final String isbn){
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
        assertThat(title, is(htmlBook.getTitle()));
        assertThat(author, is(htmlBook.getAuthor()));
        assertThat(year, is(htmlBook.getYearOfPublication()));
        assertThat(edition, is(htmlBook.getEdition()));
        assertThat(isbn, is(htmlBook.getIsbn()));
    }

    @Then("The library contains no books")
    public void libraryIsEmpty(){
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        assertThat(htmlBookList.size(), is(0));
    }

    @Then("the booklist lists the user $borrower as borrower for the book with isbn $isbn")
    public void bookListHasBorrowerForBookWithIsbn(final String borrower,
                                                   final String isbn){
        Book book = DemoBookFactory.createDemoBook().build();
        Map<String, String> wantedRow = createRowMap(book.getTitle(), book.getAuthor(),
                String.valueOf(book.getYearOfPublication()), book.getEdition(), isbn, borrower);
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        HtmlBook htmlBook = htmlBookList.getBookByIsbn(isbn);
        assertThat(htmlBook.getBorrower(), is(borrower));
    }

    @Then("books $isbns are not borrowed anymore by borrower $borrower")
    public void booksAreNotBorrowedByBorrower1(String isbns,
                                               String borrower){
        List<String> isbnList = getListOfItems(isbns);
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        for (String isbn : isbnList){
            assertThat(htmlBookList.getBookByIsbn(isbn).getBorrower(), is(isEmptyOrNullString()));
        }
    }

    @Then("books $isbns are still borrowed by borrower $borrower")
    public void booksAreStillBorrowedByBorrower2(String isbns,
                                                 String borrower2){
        List<String> isbnList = getListOfItems(isbns);
        seleniumAdapter.gotoPage(Page.BOOKLIST);
        HtmlBookList htmlBookList = seleniumAdapter.getTableContent(PageElement.BOOKLIST);
        for (String isbn : isbnList){
            assertThat(htmlBookList.getBookByIsbn(isbn).getBorrower(), is(borrower2));
        }
    }

    private List<String> getListOfItems(String isbns) {
        return isbns.isEmpty() ? Collections.emptyList() : Arrays.asList(isbns.split(" "));
    }

    private HashMap<String, String> createRowMap(final String title, final String author, final String year,
                                                 final String edition, final String isbn, final String borrower) {
        return new HashMap<String, String>(){
            {
                put("Title", title);
                put("Author", author);
                put("Year", year);
                put("Edition", edition);
                put("ISBN", isbn);
                put("Borrower", borrower);
            }
        };
    }

}
