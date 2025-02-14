package com.ssafy.ourdoc.domain.book.entity;

import java.time.Year;

import com.ssafy.ourdoc.global.common.entity.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "book_id", unique = true, nullable = false)
	private Long id;

	@Column(name = "isbn", unique = true)
	private String isbn;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "author")
	private String author;

	@Column(name = "genre")
	private String genre;

	@Column(name = "description")
	private String description;

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publish_year")
	private Year publishYear;

	@Column(name = "image_url")
	private String imageUrl;

	@Builder
	public Book(String isbn, String title, String author, String genre, String description, String publisher,
		Year publishYear, String imageUrl) {
		this.isbn = isbn;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.description = description;
		this.publisher = publisher;
		this.publishYear = publishYear;
		this.imageUrl = imageUrl;
	}
}
