package com.ssafy.ourdoc.domain.book.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookDetailResponse;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookService {
	private final BookRepository bookRepository;
	private final NationalLibraryBookService nationalLibraryBookService;

	public void registerBook(Book book) {
		if (bookRepository.findByIsbn(book.getIsbn()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 ISBN입니다.");
		}
		bookRepository.save(book);
	}

	public List<BookResponse> searchBook(BookRequest request) {
		List<Book> books = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		if (books.isEmpty()) {
			updateBookListFromNationalLibrary(request);
			books = bookRepository.findBookList(request.title(), request.author(), request.publisher());
		}

		return books.stream()
			.map(BookResponse::of)
			.collect(Collectors.toList());
	}

	public void updateBookListFromNationalLibrary(BookRequest request) {
		List<NationalLibraryBookResponse> externalBooks = nationalLibraryBookService.parseBook(request);
		List<NationalLibraryBookResponse> uniqueBooks = externalBooks.stream()
			.filter(distinctByKey(NationalLibraryBookResponse::isbn))
			.toList();
		List<Book> newBooks = uniqueBooks.stream()
			.filter(response -> bookRepository.findByIsbn(response.isbn()).isEmpty())
			.map(NationalLibraryBookResponse::toBookEntity)
			.collect(Collectors.toList());

		if (!newBooks.isEmpty()) {
			bookRepository.saveAll(newBooks);
		}
	}

	public BookDetailResponse getBookDetail(Long id) {
		Book book = bookRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당하는 ID의 도서가 없습니다."));
		return BookDetailResponse.of(book, book.getDescription());
	}

	private static <T> Predicate<T> distinctByKey(Function<T, Object> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}
