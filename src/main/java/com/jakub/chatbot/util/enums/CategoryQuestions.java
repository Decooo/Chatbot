package com.jakub.chatbot.util.enums;

public enum CategoryQuestions {
	ACTING {
		@Override
		public String getValue() {
			return "Gra_aktorska";
		}
	},
	STORY {
		@Override
		public String getValue() {
			return "fabula";
		}
	},
	SPECIALEFFECTS {
		@Override
		public String getValue() {
			return "efekty_specjalne";
		}
	};

	public abstract String getValue();

}
