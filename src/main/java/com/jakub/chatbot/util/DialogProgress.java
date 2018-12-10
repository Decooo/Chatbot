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
		questionsActing.add("Co sądzisz o grze aktorskiej?");
		questionsActing.add("Jak ocenisz grę głównego bohatera?");
		questionsActing.add("Która rola najbardziej Ci się podobała?");
		questionsActing.add("Jak oceniasz grę aktorów?");
		questionsActing.add("jak ocenisz grę postaci drugoplanowych?");
		Collections.shuffle(questionsActing);
	}

	private void doQuestionsStoryList() {
		questionsStory.add("Jak oceniasz fabułę filmu?");
		questionsStory.add("Jak oceniasz historię przedstawioną w filmie?");
		questionsStory.add("Czy podobało Ci się zakończenie historii?");
		questionsStory.add("Czy fabuła filmu Cię zainteresowała?");
		questionsStory.add("Jaka jest Twoja opinia o fabule przedstwionej w filmie?");
		Collections.shuffle(questionsStory);
	}

	private void doQuestionsSpecialEffectsList() {
		questionsSpecialEffects.add("Jak oceniasz efekty specjalne przedstawione w filmie?");
		questionsSpecialEffects.add("Czy podobała Ci się scenografia?");
		questionsSpecialEffects.add("Czy muzyka była dobrze dobrana?");
		questionsSpecialEffects.add("Czy montaż został dobrze zrobiony?");
		questionsSpecialEffects.add("Czy efekty specjalne zrobiły na Tobie wrażenie?");
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
