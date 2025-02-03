package com.ssafy.ourdoc.domain.book.repository;

import static com.ssafy.ourdoc.domain.book.entity.QBook.*;

import java.util.List;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.ourdoc.domain.book.entity.Book;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookQueryRepositoryImpl implements BookQueryRepository {
	private final JPAQueryFactory queryFactory;

	public List<Book> findBookList(String title, String author, String publisher) {
		BooleanBuilder builder = new BooleanBuilder();

		if (title != null && !title.isEmpty()) {
			builder.and(book.title.containsIgnoreCase(title));
		}
		if (author != null && !author.isEmpty()) {
			builder.and(book.author.containsIgnoreCase(author));
		}
		if (publisher != null && !publisher.isEmpty()) {
			builder.and(book.publisher.containsIgnoreCase(publisher));
		}

		return queryFactory
			.selectFrom(book)
			.where(builder)
			.fetch();
	}
}
