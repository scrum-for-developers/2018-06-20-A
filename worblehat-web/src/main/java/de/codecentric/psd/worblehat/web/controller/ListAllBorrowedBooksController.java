package de.codecentric.psd.worblehat.web.controller;

import de.codecentric.psd.worblehat.domain.BookService;
import de.codecentric.psd.worblehat.domain.Borrowing;
import de.codecentric.psd.worblehat.web.formdata.ReturnAllBooksFormData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

import javax.validation.Valid;

/**
 * Controller class for the
 */
@Controller
@RequestMapping("/listBorrowedBooks")
public class ListAllBorrowedBooksController {

	private BookService bookService;
	private List<Borrowing> borrowingsByBorrower;

	@Autowired
	public ListAllBorrowedBooksController(BookService bookService) {
		this.bookService = bookService;
	}

	@RequestMapping(method = RequestMethod.GET)
	public void prepareView(ModelMap modelMap) {
		modelMap.put("returnAllBookFormData", new ReturnAllBooksFormData());
	}

	@RequestMapping(method = RequestMethod.POST)
	public String returnAllBooks(
			@ModelAttribute("returnAllBookFormData") @Valid ReturnAllBooksFormData formData,
			BindingResult result,
			ModelMap modelMap) {
		
		if (result.hasErrors()) {
			return "listBorrowedBooks";
		} else {
			borrowingsByBorrower = bookService.getBorrowingsByBorrower(formData.getEmailAddress());
			modelMap.put("borrowings", borrowingsByBorrower);
			return "listBorrowedBooks";
		}
	}

}
