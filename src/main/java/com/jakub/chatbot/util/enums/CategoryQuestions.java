package com.jakub.chatbot.util.enums;

public enum CategoryQuestions {
	ACTING {
		@Override
		public String getValue() {
			return "ACTING";
		}
	},
	STORY {
		@Override
		public String getValue() {
			return "STORY";
		}
	},
	SPECIALEFFECTS {
		@Override
		public String getValue() {
			return "SPECIALEFFECTS";
		}
	};

	public abstract String getValue();

}
