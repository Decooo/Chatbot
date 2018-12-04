package com.jakub.chatbot.util;

import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.util.enums.CategoryQuestions;

import java.util.ArrayList;
import java.util.Random;

public class AnalysisDialog {

	public static void analysis(String response, DialogProgress dialogProgress) {
		System.out.println("response = " + response);
		dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.userCode("Testowa"));
	}

	public static void startDialog(DialogProgress dialogProgress) throws NotFoundException {
		randomCategoryQuestion(dialogProgress);
		String question = randomQuestion(dialogProgress);
		dialogProgress.setCodeHtml(HtmlCode.botCode(question));
	}

	private static String randomQuestion(DialogProgress dialogProgress) throws NotFoundException {
		ArrayList<String> questionsList = new ArrayList<>();
		if (dialogProgress.getCurrentCategoryQuestions().equals(CategoryQuestions.ACTING.getValue()))
			questionsList = dialogProgress.getQuestionsActing();
		else if (dialogProgress.getCurrentCategoryQuestions().equals(CategoryQuestions.STORY.getValue()))
			questionsList = dialogProgress.getQuestionsStory();
		else if (dialogProgress.getCurrentCategoryQuestions().equals(CategoryQuestions.SPECIALEFFECTS.getValue()))
			questionsList = dialogProgress.getQuestionsSpecialEffects();

		if (questionsList.size() == 0) throw new NotFoundException("Nie znaleziono żadnych pytań");
		else if (dialogProgress.getNumberCurrentQuestions() >= questionsList.size())
			dialogProgress.setNumberCurrentQuestions(0);

		dialogProgress.setNumberCurrentQuestions(dialogProgress.getNumberCurrentQuestions() + 1);
		return questionsList.get(dialogProgress.getNumberCurrentQuestions());
	}

	private static void randomCategoryQuestion(DialogProgress dialogProgress) throws NotFoundException {
		ArrayList<String> categoryList = new ArrayList<>();
		if (!dialogProgress.getActing()) categoryList.add(CategoryQuestions.ACTING.getValue());
		if (!dialogProgress.getStory()) categoryList.add(CategoryQuestions.STORY.getValue());
		if (!dialogProgress.getSpecialEffects()) categoryList.add(CategoryQuestions.SPECIALEFFECTS.getValue());

		if (categoryList.isEmpty()) throw new NotFoundException("Nie znaleziono dostępnych kategorii");
		else if (categoryList.size() == 1) dialogProgress.setCurrentCategoryQuestions(categoryList.get(0));
		else {
			Random generator = new Random();
			int index = generator.nextInt(categoryList.size() - 1);
			dialogProgress.setCurrentCategoryQuestions(categoryList.get(index));
		}
	}
}
