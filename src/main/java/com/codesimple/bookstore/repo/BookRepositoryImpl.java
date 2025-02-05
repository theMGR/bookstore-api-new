package com.codesimple.bookstore.repo;

import com.codesimple.bookstore.dto.BookQueryDslDTO;
import com.codesimple.bookstore.entity.Book;
import com.codesimple.bookstore.entity.QBook;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.jpa.impl.JPAQuery;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BookRepositoryImpl implements BookRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public static QBook qBook = QBook.book;

	@Override
	public List<Book> getAllBooksByQuerDsl(Integer year) {
		JPAQuery<Book> jpaQuery = new JPAQuery<>(entityManager);

		QBean<Book> bookQBean = Projections.bean(Book.class, qBook.id, qBook.bookType);

		return jpaQuery.from(qBook).where(qBook.yearOfPublication.eq(year)).select(bookQBean).fetch();
	}

	@Override
	public List<BookQueryDslDTO> getAllBooksByQuerDslDto(Integer year) {
		JPAQuery<BookQueryDslDTO> jpaQuery = new JPAQuery<>(entityManager);

		QBean<BookQueryDslDTO> dslDTOQBean = Projections.bean(BookQueryDslDTO.class, qBook.id,
				qBook.bookType.as("type"));

		return jpaQuery.select(dslDTOQBean).from(qBook).where(qBook.yearOfPublication.eq(year)).fetch();
	}

	@Override
	public List<Book> getBooksByYearAndType(Integer year, String bookType) {
		JPAQuery<Book> query = new JPAQuery<>(entityManager);

		return query.select(qBook).from(qBook).where(qBook.yearOfPublication.eq(year).and(qBook.bookType.eq(bookType)))
				.fetch();
	}
}