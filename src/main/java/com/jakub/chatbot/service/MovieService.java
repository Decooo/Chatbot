package com.jakub.chatbot.service;

import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.repository.MovieRepository;
import com.jakub.chatbot.repository.RatingRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

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

	public ModelAndView markingMovie(int idMovie) {
		var model = new ModelAndView("marking.html");
		return model;
	}
}
