package com.ssafy.ourdoc.domain.book.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.domain.book.dto.BookResponse;
import com.ssafy.ourdoc.domain.book.dto.BookSearchRequest;
import com.ssafy.ourdoc.domain.book.service.BookFavoriteService;
import com.ssafy.ourdoc.domain.user.entity.User;
import com.ssafy.ourdoc.global.annotation.Login;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/books/favorite")
public class BookFavoriteController {

	private final BookFavoriteService bookFavoriteService;

	@PostMapping
	public void addFavorite(@RequestBody BookRequest request, @Login User user) {
		bookFavoriteService.addBookFavorite(request, user);
	}

	@GetMapping
	public ResponseEntity<List<BookResponse>> getFavorite(@ModelAttribute BookSearchRequest request, @Login User user) {
		List<BookResponse> books = bookFavoriteService.getBookFavorites(request, user);
		return ResponseEntity.ok(books);
	}

	@DeleteMapping
	public void deleteFavorite(@RequestBody BookRequest request, @Login User user) {
		bookFavoriteService.deleteBookFavorite(request, user);
	}
}
