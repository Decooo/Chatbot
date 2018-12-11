package com.jakub.chatbot.util.jsonEntity;

import java.util.ArrayList;

public class WitResponse {
	private String text;
	private ArrayList<Entities> entitiesArrayList;
	private String msg_id;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public ArrayList<Entities> getEntitiesArrayList() {
		return entitiesArrayList;
	}

	public void setEntitiesArrayList(ArrayList<Entities> entitiesArrayList) {
		this.entitiesArrayList = entitiesArrayList;
	}

	public String getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(String msg_id) {
		this.msg_id = msg_id;
	}
}
