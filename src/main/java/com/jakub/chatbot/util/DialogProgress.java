package com.jakub.chatbot.util;

import java.util.ArrayList;
import java.util.Collections;

public class DialogProgress {

	private String content = "";
	private String codeHtml = "";
	private Boolean isActing = false;
	private Boolean isStory = false;
	private Boolean isSpecialEffects = false;
	private Boolean isRatingReady = false;
	private ArrayList<String> questionsActing = new ArrayList<>();
	private ArrayList<String> questionsStory = new ArrayList<>();
	private ArrayList<String> questionsSpecialEffects = new ArrayList<>();
	private String currentCategoryQuestions;
	private int numberCurrentQuestions = -1;

	public DialogProgress() {
		doQuestionsActingList();
		doQuestionsStoryList();
		doQuestionsSpecialEffectsList();
	}

	private void doQuestionsActingList() {
		questionsActing.add("Pytanie gra aktorska 1");
		questionsActing.add("Pytanie gra aktorska 2");
		questionsActing.add("Pytanie gra aktorska 3");
		questionsActing.add("Pytanie gra aktorska 4");
		questionsActing.add("Pytanie gra aktorska 5");
		Collections.shuffle(questionsActing);
	}

	private void doQuestionsStoryList() {
		questionsStory.add("Pytanie fabuła 1");
		questionsStory.add("Pytanie fabuła 2");
		questionsStory.add("Pytanie fabuła 3");
		questionsStory.add("Pytanie fabuła 4");
		questionsStory.add("Pytanie fabuła 5");
		Collections.shuffle(questionsStory);
	}

	private void doQuestionsSpecialEffectsList() {
		questionsSpecialEffects.add("Pytanie efekty specjalne 1");
		questionsSpecialEffects.add("Pytanie efekty specjalne 2");
		questionsSpecialEffects.add("Pytanie efekty specjalne 3");
		questionsSpecialEffects.add("Pytanie efekty specjalne 4");
		questionsSpecialEffects.add("Pytanie efekty specjalne 5");
		Collections.shuffle(questionsSpecialEffects);
	}

	public String getCurrentCategoryQuestions() {
		return currentCategoryQuestions;
	}

	public void setCurrentCategoryQuestions(String currentCategoryQuestions) {
		this.currentCategoryQuestions = currentCategoryQuestions;
	}

	public int getNumberCurrentQuestions() {
		return numberCurrentQuestions;
	}

	public void setNumberCurrentQuestions(int numberCurrentQuestions) {
		this.numberCurrentQuestions = numberCurrentQuestions;
	}

	public ArrayList<String> getQuestionsActing() {
		return questionsActing;
	}

	public void setQuestionsActing(ArrayList<String> questionsActing) {
		this.questionsActing = questionsActing;
	}

	public ArrayList<String> getQuestionsStory() {
		return questionsStory;
	}

	public void setQuestionsStory(ArrayList<String> questionsStory) {
		this.questionsStory = questionsStory;
	}

	public ArrayList<String> getQuestionsSpecialEffects() {
		return questionsSpecialEffects;
	}

	public void setQuestionsSpecialEffects(ArrayList<String> questionsSpecialEffects) {
		this.questionsSpecialEffects = questionsSpecialEffects;
	}

	public Boolean getRatingReady() {
		return isRatingReady;
	}

	public void setRatingReady(Boolean ratingReady) {
		isRatingReady = ratingReady;
	}

	public String getCodeHtml() {
		return codeHtml;
	}

	public void setCodeHtml(String codeHtml) {
		this.codeHtml = codeHtml;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Boolean getActing() {
		return isActing;
	}

	public void setActing(Boolean acting) {
		isActing = acting;
	}

	public Boolean getStory() {
		return isStory;
	}

	public void setStory(Boolean story) {
		isStory = story;
	}

	public Boolean getSpecialEffects() {
		return isSpecialEffects;
	}

	public void setSpecialEffects(Boolean specialEffects) {
		isSpecialEffects = specialEffects;
	}
}
