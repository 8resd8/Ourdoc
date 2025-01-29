package com.ssafy.ourdoc.book.dto;

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
