package com.elibrary.rshelfstore.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elibrary.rshelfstore.model.Book;
import com.elibrary.rshelfstore.pojos.FilterOptions;
import com.elibrary.rshelfstore.pojos.ResponseEntity;
import com.elibrary.rshelfstore.service.BookService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rshelfstore/book")
@CrossOrigin(origins = "*")
@Slf4j
public class BookController {

	@Autowired
	private BookService bookService;

	@PostMapping("/save")
	public ResponseEntity<List<Book>> saveBooks(@RequestBody List<Book> bookDetails) throws Exception {
		ResponseEntity<List<Book>> res = new ResponseEntity<List<Book>>();
		List<Book> list = new ArrayList<Book>();
		list = bookService.saveBooks(bookDetails);
		if (list.size() > 0) {
			res.setData(list);
			res.setMessage("Book(s) saved successfully in the system!");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("!! Somthing went wrong in the registration of book(s) fron the system !!");
			res.setStatus(-1);
		}

		log.info("/save response => " + res);
		return res;
	}

	@GetMapping("/getBookData")
	public ResponseEntity<List<Book>> getBooks() throws Exception {
		ResponseEntity<List<Book>> res = new ResponseEntity<List<Book>>();

		List<Book> list = bookService.getBooks();

		if (list.size() > 0) {
			res.setData(list);
			res.setMessage("Data has been retrieved successfully!");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("!! Somthing went wrong in the retrival of all books fron the system !!");
			res.setStatus(-1);
		}
		log.info("/getBookData response => " + res);
		return res;
	}

	@GetMapping("/getById")
	public ResponseEntity<Book> getBookById(@RequestParam("bookId") Integer bookId) throws Exception {
		ResponseEntity<Book> res = new ResponseEntity<Book>();

		Book book = bookService.getBookById(bookId);

		if (book != null) {
			res.setData(book);
			res.setMessage("Book has been retrieved successfully By Its Id!");
			res.setStatus(200);

		} else {
			res.setData(null);
			res.setMessage("!! Somthing went wrong in the retrival of the book from system !!");
			res.setStatus(-1);
		}
		log.info("/getById response => " + res);
		return res;
	}

	@PutMapping("/editBook")
	public ResponseEntity<Book> editBookDetails(@RequestBody Book book) throws Exception {
		ResponseEntity<Book> res = new ResponseEntity<Book>();

		Book bookObj = bookService.editBookDetails(book);

		if (bookObj != null) {
			res.setData(bookObj);
			res.setMessage("Book has been Updated successfully By Its Id!");
			res.setStatus(200);

		} else {
			res.setData(null);
			res.setMessage("!! Somthing went wrong in the updation of the book from system !!");
			res.setStatus(-1);
		}
		log.info("/editBook response => " + res);
		return res;
	}

	@DeleteMapping("/deleteBook")
	public ResponseEntity<List<Book>> deleteBookDetails(@RequestBody List<Integer> bookIds) throws Exception {

		ResponseEntity<List<Book>> res = new ResponseEntity<List<Book>>();
		List<Book> removedBookList = bookService.deleteBookDetails(bookIds);

		if (removedBookList.size() > 0) {
			res.setData(removedBookList);
			res.setMessage("Book(s) has been deleted successfully By Id(s)!");
			res.setStatus(200);

		} else {
			res.setData(null);
			res.setMessage("!! Somthing went wrong in the deletion of the book(s) from system !!");
			res.setStatus(-1);
		}
		log.info("/deleteBook response => " + res);
		return res;
	}

	@PostMapping("/filterBooks")
	public ResponseEntity<List<Book>> filterBooks(@RequestBody FilterOptions filterOptions) throws Exception {
		ResponseEntity<List<Book>> res = new ResponseEntity<List<Book>>();

		List<Book> searchList = bookService.filterBooks(filterOptions);

		if (searchList.size() > 0) {
			res.setMessage("Data Found.");
			res.setData(searchList);
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("No Records Found!");
			res.setStatus(-1);
		}

		log.info("/filterBooks response => " + res);
		return res;
	}

}
