package com.jakub.chatbot.util.jsonEntity;

public class Value {
	private String suggested;
	private double confidence;
	private String value;
	private String type;

	public String getSuggested() {
		return suggested;
	}

	public void setSuggested(String suggested) {
		this.suggested = suggested;
	}

	public double getConfidence() {
		return confidence;
	}

	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
