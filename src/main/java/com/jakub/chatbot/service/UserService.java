package com.jakub.chatbot.service;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

@Service
public class UserService {

	public ModelAndView mainPage() {
		return new ModelAndView("index.html");
	}

}
