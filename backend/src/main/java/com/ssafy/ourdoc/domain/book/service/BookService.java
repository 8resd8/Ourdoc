package com.ssafy.ourdoc.domain.book.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class BookService {
	private final BookRepository bookRepository;
	private final NationalLibraryBookService nationalLibraryBookService;

	public void registerBook(Book book) {
		bookRepository.save(book);
	}

	public List<Book> searchBook(BookRequest request) {
		List<Book> books = bookRepository.findByTitleAndAuthorAndPublisher(request.title(), request.author(),
			request.publisher());
		if (books.isEmpty()) {
			List<NationalLibraryBookResponse> externalBooks = nationalLibraryBookService.parseBook(request);
			externalBooks.forEach(response -> registerBook(NationalLibraryBookResponse.toBookEntity(response)));

			books = bookRepository.findByTitleAndAuthorAndPublisher(
				request.title(), request.author(), request.publisher()
			);
		}
		return books;
	}
}
