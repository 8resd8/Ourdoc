package com.ssafy.ourdoc.api.nationallibrary;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.ourdoc.domain.book.dto.BookRequest;
import com.ssafy.ourdoc.global.integration.nationallibrary.dto.NationalLibraryBookResponse;
import com.ssafy.ourdoc.global.integration.nationallibrary.service.NationalLibraryBookService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/nl")
public class NationalLibraryController {
	private final NationalLibraryBookService nationalLibraryService;

	@GetMapping("/book")
	public ResponseEntity<List<NationalLibraryBookResponse>> getNationalLibrary(
		@RequestBody BookRequest nationalLibraryBookRequest) {
		List<NationalLibraryBookResponse> books = nationalLibraryService.parseBook(nationalLibraryBookRequest);
		return ResponseEntity.ok(books);
	}
}
