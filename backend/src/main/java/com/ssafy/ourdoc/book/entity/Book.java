package com.ssafy.ourdoc.book.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Book {
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

	@Column(name = "publisher")
	private String publisher;

	@Column(name = "publish_time")
	private LocalDate publishTime;

	@Column(name = "image_url")
	private String imageUrl;
}
