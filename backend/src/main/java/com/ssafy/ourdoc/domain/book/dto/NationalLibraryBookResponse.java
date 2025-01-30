package com.ssafy.ourdoc.domain.book.dto;

public record NationalLibraryBookResponse(
	String isbn,
	String title,
	String author,
	String genre,
	String publisher,
	String publishTime,
	String imageUrl
) {
}
