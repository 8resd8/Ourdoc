package com.ssafy.ourdoc.domain.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.dto.favorite.BookFavoriteDetail;
import com.ssafy.ourdoc.domain.book.dto.favorite.BookFavoriteDetailPage;
import com.ssafy.ourdoc.domain.book.dto.favorite.BookFavoriteListResponse;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.entity.BookFavorite;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRepository;
import com.ssafy.ourdoc.domain.book.util.BookStatusMapper;
import com.ssafy.ourdoc.domain.bookreport.dto.BookReportHomeworkStudent;
import com.ssafy.ourdoc.domain.bookreport.repository.BookReportRepository;
import com.ssafy.ourdoc.domain.bookreport.service.BookReportStudentService;
import com.ssafy.ourdoc.domain.user.entity.User;

import groovy.util.logging.Slf4j;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class BookFavoriteService {
	private final BookRepository bookRepository;
	private final BookFavoriteRepository bookFavoriteRepository;
	private final BookService bookService;
	private final BookStatusMapper bookStatusMapper;

	private final BookReportRepository bookReportRepository;

	private final BookReportStudentService bookReportStudentService;

	public void addBookFavorite(BookRequest request, User user) {
		Book book = bookService.findBookById(request.bookId());
		if (bookFavoriteRepository.existsByBookAndUser(book, user)) {
			throw new IllegalArgumentException("이미 관심 도서로 등록했습니다.");
		}
		BookFavorite bookFavorite = BookFavorite.builder().book(book).user(user).build();
		bookFavoriteRepository.save(bookFavorite);
	}

	public void deleteBookFavorite(BookRequest request, User user) {
		Book book = bookService.findBookById(request.bookId());
		BookFavorite bookFavorite = bookFavoriteRepository.findByBookAndUser(book, user)
			.orElseThrow(() -> new IllegalArgumentException("관심 도서로 등록한 도서가 아닙니다."));
		bookFavoriteRepository.delete(bookFavorite);
	}

	public BookFavoriteListResponse getBookFavorites(BookSearchRequest request, User user, Pageable pageable) {
		List<Book> searchedBooks = getBookList(request);

		Page<BookFavorite> bookFavorites = bookFavoriteRepository.findByUserAndBookIn(user, searchedBooks, pageable);

		List<Book> books = bookFavorites.stream()
			.map(BookFavorite::getBook)
			.toList();

		List<BookFavoriteDetail> details = bookFavorites.stream()
			.map(bookFavorite -> getBookFavoriteDetail(bookFavorite, user))
			.toList();

		Page<BookFavoriteDetail> content = new PageImpl<>(details, pageable, bookFavorites.getTotalElements());
		return new BookFavoriteListResponse(content);
	}

	private List<Book> getBookList(BookSearchRequest request) {
		return bookRepository.findBookList(request.title(), request.author(), request.publisher());
	}

	private BookFavoriteDetail getBookFavoriteDetail(BookFavorite bookFavorite, User user) {
		Book book = bookFavorite.getBook();
		boolean submitStatus = bookReportRepository.countByUserIdAndBookId(user.getId(), book.getId()) > 0;
		List<BookReportHomeworkStudent> bookReports = bookReportStudentService.getReportStudentHomeworkResponses(
			book.getId(), user.getId());
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(bookFavorite.getBook(), user);
		return BookFavoriteDetail.of(bookFavorite, submitStatus, bookReports, bookStatus);
	}

	public BookFavoriteDetailPage getBookFavoriteDetailPage(Long bookFavoriteId, User user, Pageable pageable) {
		BookFavorite bookFavorite = bookFavoriteRepository.findById(bookFavoriteId)
			.orElseThrow(()->new IllegalArgumentException("해당하는 관심도서 ID가 없습니다."));
		Book book = bookFavorite.getBook();
		boolean submitStatus = bookReportRepository.countByUserIdAndBookId(user.getId(), book.getId()) > 0;
		Page<BookReportHomeworkStudent> bookReports = bookReportStudentService.getReportStudentHomeworkPageResponses(
			book.getId(), user.getId(), pageable);
		BookStatus bookStatus = bookStatusMapper.mapBookStatus(bookFavorite.getBook(), user);
		return BookFavoriteDetailPage.of(bookFavorite, submitStatus, bookReports, bookStatus);
	}
}
