package com.elibrary.rshelfstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Book;
import com.elibrary.rshelfstore.pojos.FilterOptions;
import com.elibrary.rshelfstore.repository.BookRepository;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepo;

	@Override
	public List<Book> saveBooks(List<Book> bookDetails) throws Exception {

		List<Book> regList = new ArrayList<Book>();

		for (Book element : bookDetails) {
			Book obj = bookRepo.save(element);
			regList.add(obj);
		}

		return regList;
	}

	@Override
	public List<Book> getBooks() throws Exception {

		return bookRepo.findAll();
	}

	@Override
	public Book getBookById(Integer bookId) throws Exception {

		Book book = null;

		if (bookId != null) {
			book = bookRepo.findBookByBookId(bookId);
		}

		return book;
	}

	@Override
	public Book editBookDetails(Book book) throws Exception {
		Book myBook = null;
		if (book.getBookId() != null) {
			Book b = bookRepo.findBookByBookId(book.getBookId());
			book.setLastModifiedOn(new Date());
			book.setCreatedOn(b.getCreatedOn());
			myBook = bookRepo.save(book);
		}
		return myBook;
	}

	@Override
	public List<Book> deleteBookDetails(List<Integer> bookIds) throws Exception {
		Book book = null;
		List<Book> removedEntities = new ArrayList<Book>();
		for (Integer id : bookIds) {
			book = bookRepo.findBookByBookId(id);
			if (book != null) {
				book.setIsDelete(1);
				bookRepo.save(book);
				removedEntities.add(book);
			}
		}

		return removedEntities;
	}

	@Override
	public List<Book> filterBooks(FilterOptions filterOptions) throws Exception {

		String filterColumn = filterOptions.getFilterColumn();

		switch (filterColumn) {
		case "Author":
			return bookRepo.findByAuthor(filterOptions.getFilterValue());

		case "Publication":
			return bookRepo.findByPublication(filterOptions.getFilterValue());

		case "Name":
			return bookRepo.findByName(filterOptions.getFilterValue());

		case "Pages":
			return bookRepo.findByPageRange(filterOptions.getList().get(0), filterOptions.getList().get(1));

		default:
			return null;
		}
	}

}
