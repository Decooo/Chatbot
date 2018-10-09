package com.jakub.chatbot.controller;

import com.jakub.chatbot.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
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
}
