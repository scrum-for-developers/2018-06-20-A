package de.codecentric.psd.worblehat.web.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BookDataFormData;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/insertBooks")
public class InsertBookController {

	private static final Logger LOG = LoggerFactory.getLogger(InsertBookController.class);

	private BookService bookService;

	@Autowired
	public InsertBookController(final BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void setupForm(final ModelMap modelMap) {
		modelMap.put("bookDataFormData", new BookDataFormData());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("bookDataFormData") @Valid final BookDataFormData bookDataFormData,
			final BindingResult result) {

		if (result.hasErrors()) {
			return "insertBooks";
		} else {
			Optional<Book> book = this.bookService.createBook(bookDataFormData.getTitle(), bookDataFormData.getAuthor(),
					bookDataFormData.getEdition(), bookDataFormData.getIsbn(),
					Integer.parseInt(bookDataFormData.getYearOfPublication()), bookDataFormData.getDescription());
			if (book.isPresent()) {
				LOG.info("new book instance is created: " + book.get());
			} else {
				LOG.debug("failed to create new book with: " + bookDataFormData.toString());
			}
			return "redirect:bookList";
		}
	}

}
