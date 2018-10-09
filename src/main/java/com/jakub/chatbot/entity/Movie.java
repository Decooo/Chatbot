package com.jakub.chatbot.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "movie")
public class Movie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int idMovie;

	@NotNull
	@Size(min = 2, max = 255)
	private String title;

	@NotNull
	@Size(min = 5, max = 45)
	private String director;

	@NotNull
	private int productionYear;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<Rating> ratings = new HashSet<>();

	public int getIdMovie() {
		return idMovie;
	}

	public void setIdMovie(int idMovie) {
		this.idMovie = idMovie;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public int getProductionYear() {
		return productionYear;
	}

	public void setProductionYear(int productionYear) {
		this.productionYear = productionYear;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}

	@Override
	public String toString() {
		return "Movie{" +
				"idMovie=" + idMovie +
				", title='" + title + '\'' +
				", director='" + director + '\'' +
				", productionYear=" + productionYear +
				", ratings=" + ratings +
				'}';
	}
}
