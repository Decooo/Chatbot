package com.jakub.chatbot.service;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.repository.MovieRepository;
import com.jakub.chatbot.repository.RatingRepository;
import com.jakub.chatbot.util.SentimentAnalysis;
import com.jakub.chatbot.util.WitRequest;
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

	public ModelAndView markingMovie(int idMovie) throws NotFoundException {
		var model = new ModelAndView("marking.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");

		SentimentAnalysis turney = new SentimentAnalysis();
		turney.analysis("to film na 8-9*, bo i świetne zdjęcia (wielka inwencja operatora), i {dobry} montaż, czyli znakomite wyczucie kiedy ciąć, sceny prawdziwie filmowe, niewymagające dużo dialogów, bo tak są mocne - np. scena z trumną, kapitalne przeplatanie wizji z realnością na końcu, wątek walki duchowej z odbiciem w lustrze (raz to anioł, raz diabeł) - reżyser świetnie wymyslił mnóstwo scen, sa treściwe, plastyczne, pełne znaczenia, zaciekawiające (popychające akcję do przodu i wzmacniające emocje)! Naprawdę, w bardzo realny świat nas wprowadził, przekonał do niego że wreszcie nie patrzyłem zezem by wyłuskać słabości warsztatowe czy artystyczne...");
//		turney.analysis("zdjęcia były bardzo dobre");


		model.addObject("movie", movie.get());
		model.addObject("rating", new Rating());
		return model;
	}

	public ModelAndView sendMarking(Rating rating, int idMovie) {
		var model = new ModelAndView("redirect:/movie/list/" + idMovie + "/marking");

		var witRequest = new WitRequest();
		witRequest.doRequest(rating.getContentRating());

		System.out.println("Treść wiadomości: " + rating.getContentRating());
		rating.setContentRating("");
		return model;
	}
}
