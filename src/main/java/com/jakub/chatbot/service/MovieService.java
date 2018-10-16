package com.jakub.chatbot.service;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.repository.MovieRepository;
import com.jakub.chatbot.repository.RatingRepository;
import com.jakub.chatbot.util.WitRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

@Service
public class MovieService {

	MovieRepository movieRepository;
	RatingRepository ratingRepository;

	public MovieService(MovieRepository movieRepository, RatingRepository ratingRepository) {
		this.movieRepository = movieRepository;
		this.ratingRepository = ratingRepository;
	}

	public ModelAndView listMovies() {
		var model = new ModelAndView("listMovies.html");
		model.addObject("movies", movieRepository.findAll());
		return model;
	}

	public ModelAndView getMovie(int idMovie) throws NotFoundException {
		var model = new ModelAndView("movie.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");
		var ratings = ratingRepository.findByMovie(movie.get());

		model.addObject("movie", movie.get());
		model.addObject("ratings", ratings);
		return model;
	}

	public ModelAndView markingMovie(int idMovie) throws NotFoundException {
		var model = new ModelAndView("marking.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");

		model.addObject("movie", movie.get());
		model.addObject("rating", new Rating());
		return model;
	}

	public ModelAndView sendMarking(Rating rating, int idMovie) {
		var model = new ModelAndView("redirect:/movie/list/" + idMovie + "/marking");

		var witRequest = new WitRequest();
		System.out.println("Response: " + witRequest.doRequest(rating.getContentRating()));

		System.out.println("Treść wiadomości: " + rating.getContentRating());
		rating.setContentRating("");
		return model;
	}
}
