package com.jakub.chatbot.util;

public class HtmlCode {

	static String userCode(String message) {
		return "<div class=\"container darker\">\n" +
				"    <img src=\"/img/human.png\" alt=\"Avatar\" class=\"right\" style=\"width:100%;\">\n" +
				"    <p>" + message + "</p>\n" +
				"	 </div>";
	}

	static String botCode(String message) {
		return "<div class=\"container\">\n" +
				"	 <img src=\"/img/bot.png\" alt=\"Avatar\" style=\"width:100%;\">\n" +
				"    <p>" + message + "</p>\n" +
				"</div>";
	}

}