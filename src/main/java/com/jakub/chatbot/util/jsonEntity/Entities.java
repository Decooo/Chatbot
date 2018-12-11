package com.jakub.chatbot.util.jsonEntity;

import java.util.ArrayList;

public class Entities {
	String name;
	ArrayList<Value> valueArrayList;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<Value> getValueArrayList() {
		return valueArrayList;
	}

	public void setValueArrayList(ArrayList<Value> valueArrayList) {
		this.valueArrayList = valueArrayList;
	}
}
