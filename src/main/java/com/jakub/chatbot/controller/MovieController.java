package com.jakub.chatbot.controller;

import com.jakub.chatbot.entity.Rating;
import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/movie")
public class MovieController {

	private MovieService movieService;

	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView listMovies(){
		return movieService.listMovies();
	}

	@RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
	public ModelAndView getMovie(@PathVariable("id") int idMovie) throws NotFoundException {
		return movieService.getMovie(idMovie);
	}

	@RequestMapping(value = "/list/{id}/marking", method = RequestMethod.GET)
	public ModelAndView markingMovie(@PathVariable("id") int idMovie) throws NotFoundException {
		return movieService.markingMovie(idMovie);
	}

	@RequestMapping(value = "/list/{id}/marking/send", method = RequestMethod.POST)
	public ModelAndView sendMarking(@PathVariable("id") int idMovie, @ModelAttribute(value = "rating") Rating rating){
		return movieService.sendMarking(rating,idMovie);
	}

}
