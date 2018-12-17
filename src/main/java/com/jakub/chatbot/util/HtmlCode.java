package com.jakub.chatbot.util;

public class HtmlCode {

	static String userCode(String message) {
		return "<div class=\"messaging darker\">\n" +
				"    <img src=\"/img/human.png\" alt=\"Avatar\" class=\"right\" style=\"width:100%;\">\n" +
				"    <p>" + message + "</p>\n" +
				"	 </div>";
	}

	static String botCode(String message) {
		return "<div class=\"messaging\">\n" +
				"	 <img src=\"/img/bot.png\" alt=\"Avatar\" style=\"width:100%;\">\n" +
				"    <p>" + message + "</p>\n" +
				"</div>";
	}

	static String botCodeOffTopic(String message) {
		return "<div class=\"messaging\">\n" +
				"	 <img src=\"/img/bot.png\" alt=\"Avatar\" style=\"width:100%;\">\n" +
				"    <p>Twoja odpowiedź jest nie na temat. Postaraj się udzielać bardziej dokładnych odpowiedzi.</p>\n" +
				"    <p>" + message + "</p>\n" +
				"</div>";
	}

	static String endConversation() {
		return "<div class=\"messaging\">\n" +
				"	 <img src=\"/img/bot.png\" alt=\"Avatar\" style=\"width:100%;\">\n" +
				"    <p>Dziękujemy za rozmowe. Aby zobaczyć ocenę przejdź do podsumowania!</p>\n" +
				"</div>";
	}

}
