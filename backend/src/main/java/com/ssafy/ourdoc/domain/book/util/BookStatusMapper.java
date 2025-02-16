package com.ssafy.ourdoc.domain.book.util;

import org.springframework.stereotype.Component;

import com.ssafy.ourdoc.domain.book.dto.BookStatus;
import com.ssafy.ourdoc.domain.book.entity.Book;
import com.ssafy.ourdoc.domain.book.repository.BookFavoriteRepository;
import com.ssafy.ourdoc.domain.book.repository.BookRecommendRepository;
import com.ssafy.ourdoc.domain.book.repository.HomeworkRepository;
import com.ssafy.ourdoc.domain.classroom.entity.ClassRoom;
import com.ssafy.ourdoc.domain.classroom.service.ClassService;
import com.ssafy.ourdoc.domain.user.entity.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookStatusMapper {
	private final BookFavoriteRepository bookFavoriteRepository;
	private final BookRecommendRepository bookRecommendRepository;
	private final HomeworkRepository homeworkRepository;

	private final ClassService classService;

	public BookStatus mapBookStatus(Book book, User user) {
		ClassRoom classRoom = classService.getUserClassRoom(user);
		boolean isFavorite = bookFavoriteRepository.existsByBookAndUser(book, user);
		boolean isRecommend = bookRecommendRepository.existsByBookAndUserAndClassRoom(book, user, classRoom);
		boolean isHomework = homeworkRepository.existsByBookAndClassRoom(book, classRoom);

		return new BookStatus(isFavorite, isRecommend, isHomework);
	}
}
