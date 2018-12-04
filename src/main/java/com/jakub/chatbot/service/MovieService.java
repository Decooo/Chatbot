package com.jakub.chatbot.service;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.repository.MovieRepository;
import com.jakub.chatbot.repository.RatingRepository;
import com.jakub.chatbot.util.AnalysisDialog;
import com.jakub.chatbot.util.DialogProgress;
import com.jakub.chatbot.util.WitRequest;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Service
public class MovieService {

	private MovieRepository movieRepository;
	private RatingRepository ratingRepository;

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

	public ModelAndView markingMovie(int idMovie, Model model, HttpServletRequest request) throws NotFoundException, IOException {
		var modelAndView = new ModelAndView("marking.html");
		var movie = movieRepository.findById(idMovie);
		if (!movie.isPresent()) throw new NotFoundException("Nie znaleziono filmu!");

		modelAndView.addObject("movie", movie.get());
		modelAndView.addObject("rating", new Rating());

		DialogProgress dialogProgress = (DialogProgress) model.asMap().getOrDefault("dialogProgress", null);
		dialogProgress = checkInitializeDialogProgress(dialogProgress);
		modelAndView.addObject("codeHtml", dialogProgress.getCodeHtml());

		HttpSession session = request.getSession(true);
		session.setAttribute("dialogProgress", dialogProgress);
		return modelAndView;
	}

	public ModelAndView sendMarking(Rating rating, int idMovie, RedirectAttributes redirectAttributes, HttpServletRequest request) throws NotFoundException {
		var modelAndView = new ModelAndView("redirect:/movie/list/" + idMovie + "/marking");
		HttpSession session = request.getSession(true);

		DialogProgress dialogProgress = (DialogProgress) session.getAttribute("dialogProgress");
		dialogProgress = checkInitializeDialogProgress(dialogProgress);

		var witRequest = new WitRequest();
		AnalysisDialog.analysis(witRequest.doRequest(rating.getContentRating()), dialogProgress);

		redirectAttributes.addFlashAttribute("codeHtml", dialogProgress.getCodeHtml());
		redirectAttributes.addFlashAttribute("dialogProgress", dialogProgress);
		rating.setContentRating("");
		return modelAndView;
	}

	private DialogProgress checkInitializeDialogProgress(DialogProgress dialogProgress) throws NotFoundException {
		if (dialogProgress == null) {
			dialogProgress = new DialogProgress();
			AnalysisDialog.startDialog(dialogProgress);
		}
		return dialogProgress;
	}
}
