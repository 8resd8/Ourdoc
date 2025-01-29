package com.ssafy.ourdoc.book.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.book.NationalLibraryBookService;
import com.ssafy.ourdoc.book.dto.NationalLibraryBookRequest;
import com.ssafy.ourdoc.book.dto.NationalLibraryBookResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/book")
public class BookController {
	private final NationalLibraryBookService nationalLibraryService;

	@GetMapping("/nl")
	public ResponseEntity<List<NationalLibraryBookResponse>> getNationalLibrary(
		@RequestBody NationalLibraryBookRequest nationalLibraryBookRequest) throws IOException {
		List<NationalLibraryBookResponse> books = nationalLibraryService.parseBook(nationalLibraryBookRequest);
		return ResponseEntity.ok(books);
	}
}
