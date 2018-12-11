package com.jakub.chatbot.util;

import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.util.enums.CategoryQuestions;
import com.jakub.chatbot.util.jsonEntity.Entities;
import com.jakub.chatbot.util.jsonEntity.Value;
import com.jakub.chatbot.util.jsonEntity.WitResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class AnalysisDialog {

	public static void analysis(String messaging, DialogProgress dialogProgress) throws JSONException {
		WitRequest request = new WitRequest();

		if (messaging.length() >= 280) {
			ArrayList<String> sentenceList = doSentenceList(messaging);
			ArrayList<WitResponse> responseList = new ArrayList<>();
			for (String aSentenceList : sentenceList) {
				WitResponse witResponse = doWitResponse(request.doRequest(aSentenceList));
				responseList.add(witResponse);
			}
			//ANALIZA RESPONSE LIST
			System.out.println("responseList.size() = " + responseList.size());
		} else {
			WitResponse witResponse = doWitResponse(request.doRequest(messaging));
			//ANALIZA WIT RESPONSE
		}


		dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.userCode("Testowa"));
	}

	private static ArrayList<String> doSentenceList(String messaging) {
		ArrayList<String> sentenceList = new ArrayList<>();
		while (messaging.length() >= 280) {
			String temp = messaging.substring(0, 279);
			int index = temp.lastIndexOf('.');

			if (index >= 10) {
				sentenceList.add(temp.substring(0, index));
			} else if (temp.lastIndexOf(',') > index) {
				index = temp.lastIndexOf(',');
				sentenceList.add(temp.substring(0, index));
			} else {
				index = temp.lastIndexOf(' ');
				sentenceList.add(temp.substring(0, index));
			}
			messaging = messaging.substring(index);
		}
		if (1 < messaging.length()) {
			sentenceList.add(messaging);
		}
		return sentenceList;
	}

	private static WitResponse doWitResponse(String response) throws JSONException {
		JSONObject jsonObject = new JSONObject(response);
		WitResponse witResponse = new WitResponse();
		witResponse.setText(jsonObject.optString("_text"));
		witResponse.setMsg_id(jsonObject.optString("msg_id"));
		JSONObject entitiesObject = jsonObject.getJSONObject("entities");
		Iterator<String> keys = entitiesObject.keys();
		ArrayList<Entities> entitiesArrayList = new ArrayList<>();

		while (keys.hasNext()) {
			String key = keys.next();
			Entities entities = new Entities();
			entities.setName(key);
			JSONArray jsonArrayValue = entitiesObject.getJSONArray(key);
			ArrayList<Value> valueArrayList = new ArrayList<>();
			for (int i = 0; i < jsonArrayValue.length(); i++) {
				JSONObject jsonValue = jsonArrayValue.getJSONObject(i);
				Value value = new Value();
				value.setConfidence(jsonValue.optDouble("confidence"));
				value.setType(jsonValue.optString("type"));
				value.setValue(jsonValue.optString("value"));
				value.setSuggested(jsonValue.optString("suggested"));
				valueArrayList.add(value);
			}
			entities.setValueArrayList(valueArrayList);
			entitiesArrayList.add(entities);
		}

		witResponse.setEntitiesArrayList(entitiesArrayList);
		return witResponse;
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
