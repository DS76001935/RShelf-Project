package com.elibrary.rshelfstore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.elibrary.rshelfstore.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

	public Book findBookByBookId(Integer bookId);

	@Query(value = "SELECT b FROM Book b WHERE b.authorName like :author", nativeQuery = false)
	public List<Book> findByAuthor(@Param("author") String author);

	@Query(value = "SELECT b FROM Book b WHERE b.publicationName like :publication", nativeQuery = false)
	public List<Book> findByPublication(@Param("publication") String publication);

	@Query(value = "SELECT b FROM Book b WHERE b.bookName like :name", nativeQuery = false)
	public List<Book> findByName(@Param("name") String name);

	@Query(value = "SELECT b FROM Book b WHERE b.pages IN (:minPageNo, :maxPageNo)", nativeQuery = false)
	public List<Book> findByPageRange(@Param("minPageNo") Integer minPageNo, @Param("maxPageNo") Integer maxPageNo);

}
