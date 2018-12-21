package com.jakub.chatbot.util;

public class PhraseEmotions {
	private String phrase;
	private int rate1Word;
	private int rate2Word;
	private String ratePhrase;

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public int getRate1Word() {
		return rate1Word;
	}

	public void setRate1Word(int rate1Word) {
		this.rate1Word = rate1Word;
	}

	public int getRate2Word() {
		return rate2Word;
	}

	public void setRate2Word(int rate2Word) {
		this.rate2Word = rate2Word;
	}

	public String getRatePhrase() {
		return ratePhrase;
	}

	public void setRatePhrase(String ratePhrase) {
		this.ratePhrase = ratePhrase;
	}
}
