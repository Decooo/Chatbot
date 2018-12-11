package com.jakub.chatbot.controller;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.service.MovieService;
import org.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Controller
@RequestMapping("/movie")
public class MovieController {

	private MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMovies() {
		return movieService.listMovies();
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView getMovie(@PathVariable("id") int idMovie) throws NotFoundException {
		return movieService.getMovie(idMovie);
	}

	@RequestMapping(value = "/list/{id}/marking", method = RequestMethod.GET)
	public ModelAndView markingMovie(@PathVariable("id") int idMovie, Model model , HttpServletRequest request) throws NotFoundException, IOException {
		return movieService.markingMovie(idMovie, model, request);
	}

	@RequestMapping(value = "/list/{id}/marking/send", method = RequestMethod.POST)
	public ModelAndView sendMarking(@PathVariable("id") int idMovie, @ModelAttribute(value = "rating") Rating rating, RedirectAttributes redirectAttributes,
									HttpServletRequest request) throws NotFoundException, JSONException {
		return movieService.sendMarking(rating, idMovie, redirectAttributes, request);
	}

}
