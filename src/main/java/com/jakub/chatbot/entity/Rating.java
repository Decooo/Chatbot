package com.jakub.chatbot.entity;

import javax.persistence.*;

@Entity
@Table(name = "rating")
public class Rating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idRating;

	private double rating;
	private String contentRating;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_movie",nullable = false)
	private Movie movie;

	public int getIdRating() {
		return idRating;
	}

	public void setIdRating(int idRating) {
		this.idRating = idRating;
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public String getContentRating() {
		return contentRating;
	}

	public void setContentRating(String contentRating) {
		this.contentRating = contentRating;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@Override
	public String toString() {
		return "Rating{" +
				"idRating=" + idRating +
				", rating=" + rating +
				", contentRating='" + contentRating + '\'' +
				", movie=" + movie +
				'}';
	}
}
