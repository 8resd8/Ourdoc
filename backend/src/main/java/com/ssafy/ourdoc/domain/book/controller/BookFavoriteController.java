package com.ssafy.ourdoc.domain.book.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.favorite.BookFavoriteDetailPage;
import com.ssafy.ourdoc.domain.book.dto.favorite.BookFavoriteListResponse;
import com.ssafy.ourdoc.domain.book.dto.BookRequest;
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
	@ResponseStatus(HttpStatus.CREATED)
	public void addFavorite(@RequestBody BookRequest request, @Login User user) {
		bookFavoriteService.addBookFavorite(request, user);
	}

	@GetMapping
	public BookFavoriteListResponse getFavorite(@ModelAttribute BookSearchRequest request, @Login User user,
		@PageableDefault(page = 0, size = 10) Pageable pageable) {

		return bookFavoriteService.getBookFavorites(request, user, pageable);
	}

	@GetMapping("/detail")
	public BookFavoriteDetailPage getFavoriteDetail(@RequestParam(name = "bookFavoriteId") Long bookFavoriteId, @Login User user, @PageableDefault(page = 0, size = 10) Pageable pageable){
		return bookFavoriteService.getBookFavoriteDetailPage(bookFavoriteId, user, pageable);
	}

	@DeleteMapping
	public void deleteFavorite(@RequestBody BookRequest request, @Login User user) {
		bookFavoriteService.deleteBookFavorite(request, user);
	}
}
