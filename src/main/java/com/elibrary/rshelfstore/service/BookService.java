package com.elibrary.rshelfstore.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Book;
import com.elibrary.rshelfstore.pojos.FilterOptions;

@Service
public interface BookService {
	
	public List<Book> saveBooks(List<Book> bookDetails) throws Exception;
	public List<Book> getBooks() throws Exception;
	public Book getBookById(Integer bookId) throws Exception;
	public Book editBookDetails(Book book) throws Exception;
	public List<Book> deleteBookDetails(List<Integer> bookIds) throws Exception;
	public List<Book> filterBooks(FilterOptions filterOptions) throws Exception;

}
