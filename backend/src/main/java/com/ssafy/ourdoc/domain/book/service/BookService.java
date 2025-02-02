package com.ssafy.ourdoc.domain.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
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
		bookRepository.save(book);
	}

	public List<Book> searchBook(BookRequest request) {
		String title = request.title().isEmpty() ? null : request.title();
		String author = request.author().isEmpty() ? null : request.author();
		String publisher = request.publisher().isEmpty() ? null : request.publisher();

		List<Book> books = bookRepository.findByTitleAndAuthorAndPublisher(title, author, publisher);
		if (books.isEmpty()) {
			List<NationalLibraryBookResponse> externalBooks = nationalLibraryBookService.parseBook(request);
			externalBooks.forEach(response -> registerBook(NationalLibraryBookResponse.toBookEntity(response)));

			books = bookRepository.findByTitleAndAuthorAndPublisher(title, author, publisher);
		}
		return books;
	}

	public Book getBookDetail(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
}
