package de.codecentric.psd.worblehat.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.psd.worblehat.domain.Book;
import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.web.formdata.BorrowedFormData;

/**
 * Controller for BorrowingBook
 */
@RequestMapping("/borrowedBooks")
@Controller
public class BorrowedBooksController {

	private BookService bookService;

	@Autowired
	public BorrowedBooksController(final BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String setupForm(final ModelMap model) {
		model.put("borrowedFormData", new BorrowedFormData());
		return "borrowed";
	}

	@Transactional
	@RequestMapping(method = RequestMethod.POST)
	public String processSubmit(@ModelAttribute("borrowedFormData") @Valid final BorrowedFormData borrowedFormData,
			final BindingResult result, final ModelMap modelMap) {
		if (result.hasErrors()) {
			return "borrowed";
		}
		List<Book> books = this.bookService.findAllBooks();
		if (books.isEmpty()) {
			result.rejectValue("email", "noBookExists");
			return "borrowed";
		}

		modelMap.addAttribute("books", books);

		return "borrowedBooks";

	}

	@ExceptionHandler(Exception.class)
	public String handleErrors(final Exception ex, final HttpServletRequest request) {
		return "home";
	}
}
