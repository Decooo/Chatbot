package com.jakub.chatbot.util;

import com.jakub.chatbot.exceptions.NotFoundException;
import com.jakub.chatbot.util.enums.CategoryQuestions;
import com.jakub.chatbot.util.jsonEntity.Entities;
import com.jakub.chatbot.util.jsonEntity.Value;
import com.jakub.chatbot.util.jsonEntity.WitResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class AnalysisDialog {

	public static void analysis(String messaging, DialogProgress dialogProgress) throws JSONException, NotFoundException {
		dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.userCode(messaging));
		WitRequest request = new WitRequest();

		Map<String, Double> sumConfidence = new HashMap();

		if (messaging.length() >= 280) {
			ArrayList<WitResponse> responseList = createResponseList(messaging, request);
			for (WitResponse aResponseList : responseList) {
				calculateSumConfidence(sumConfidence, aResponseList);
			}
		} else if (messaging.trim().length() <= 0) {
			dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.botCodeEmptyMarking(randomQuestion(dialogProgress)));
		} else {
			WitResponse witResponse = doWitResponse(request.doRequest(messaging));
			calculateSumConfidence(sumConfidence, witResponse);
		}
		String maxKey = getMaxKey(sumConfidence);

		if (!maxKey.equalsIgnoreCase("") && sumConfidence.get(maxKey) > 0.5 && maxKey.equalsIgnoreCase(dialogProgress.getCurrentCategoryQuestions())) {
			if (dialogProgress.getLengthOpinionOnSubject() + messaging.length() >= 120) {
				setCompletedCategory(dialogProgress, maxKey);
				setVariablesToShowResult(dialogProgress, maxKey, messaging, sumConfidence);
				dialogProgress.setContent(dialogProgress.getContent() + " " + messaging.trim());
				orLastCharContentIsDot(dialogProgress);

				if (isEndConversation(dialogProgress)) {
					dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.endConversation());
					dialogProgress.setRatingReady(true);
				} else {
					randomCategoryQuestion(dialogProgress);
					dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.botCode(randomQuestion(dialogProgress)));
				}
				resetLengthOpinionOnSubject(dialogProgress);
			} else {
				dialogProgress.setLengthOpinionOnSubject(dialogProgress.getLengthOpinionOnSubject() + messaging.length());
				dialogProgress.setContent(dialogProgress.getContent() + " " + messaging.trim());
				orLastCharContentIsDot(dialogProgress);
				setVariablesToShowResult(dialogProgress, maxKey, messaging, sumConfidence);
				dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.botCode(randomQuestionAuxiliary(dialogProgress)));
			}

		} else if (messaging.trim().length() > 0)
			dialogProgress.setCodeHtml(dialogProgress.getCodeHtml() + HtmlCode.botCodeOffTopic(randomQuestion(dialogProgress)));
	}

	private static void setVariablesToShowResult(DialogProgress dialogProgress, String maxKey, String messaging, Map<String, Double> sumConfidence) {
		if (maxKey.equalsIgnoreCase("Gra_aktorska")) {
			dialogProgress.setOpinionActing(dialogProgress.getOpinionActing() + " " + messaging);
			dialogProgress.setConfidenceActing(generateStringConfidence(sumConfidence));
		} else if (maxKey.equalsIgnoreCase("fabula")) {
			dialogProgress.setOpinionStory(dialogProgress.getOpinionStory() + " " + messaging);
			dialogProgress.setConfidenceStory(generateStringConfidence(sumConfidence));
		} else if (maxKey.equalsIgnoreCase("efekty_specjalne")) {
			dialogProgress.setOpinionSpecialEffects(dialogProgress.getOpinionSpecialEffects() + " " + messaging);
			dialogProgress.setConfidenceSpecialEffects(generateStringConfidence(sumConfidence));
		}
	}

	private static String generateStringConfidence(Map<String, Double> sumConfidence) {
		double sum = sumConfidence.values().stream().mapToDouble(Double::doubleValue).sum();
		double acting = sumConfidence.getOrDefault("Gra_aktorska", 0.0) / sum;
		acting = Math.round(acting * 100.00) * 100.00 / 100.00;
		double story = sumConfidence.getOrDefault("fabula", 0.0) / sum;
		story = Math.round(story * 100.00) * 100.00 / 100.00;
		double specialEffects = sumConfidence.getOrDefault("efekty_specjalne", 0.0) / sum;
		specialEffects = Math.round(specialEffects * 100.00) * 100.00 / 100.00;

		StringBuilder confidence = new StringBuilder();
		confidence.append("Gra aktorska: ").append(acting).append("%").append(" <br/>")
				.append("Fabuła: ").append(story).append("%").append(" <br/>")
				.append("Efekty specjalne: ").append(specialEffects).append("%");
		return confidence.toString();
	}

	private static String randomQuestionAuxiliary(DialogProgress dialogProgress) throws NotFoundException {
		ArrayList<String> questionsList = dialogProgress.getQuestionsAuxiliary();

		if (questionsList.size() == 0) throw new NotFoundException("Nie znaleziono żadnych pytań");
		else if (dialogProgress.getNumberCurrentQuestionAuxiliary() >= questionsList.size())
			dialogProgress.setNumberCurrentQuestionAuxiliary(0);

		dialogProgress.setNumberCurrentQuestionAuxiliary(dialogProgress.getNumberCurrentQuestionAuxiliary() + 1);
		return questionsList.get(dialogProgress.getNumberCurrentQuestionAuxiliary() - 1);
	}

	private static void resetLengthOpinionOnSubject(DialogProgress dialogProgress) {
		dialogProgress.setLengthOpinionOnSubject(0);
		dialogProgress.setNumberCurrentQuestionAuxiliary(-1);
	}

	private static ArrayList<WitResponse> createResponseList(String messaging, WitRequest request) throws JSONException {
		ArrayList<String> sentenceList = doSentenceList(messaging);
		ArrayList<WitResponse> responseList = new ArrayList<>();
		for (String aSentenceList : sentenceList) {
			WitResponse witResponse = doWitResponse(request.doRequest(aSentenceList));
			responseList.add(witResponse);
		}
		return responseList;
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

	private static String getMaxKey(Map<String, Double> sumConfidence) {
		if (sumConfidence.size() > 1) {
			return Collections.max(sumConfidence.entrySet(), Comparator.comparingDouble(Map.Entry::getValue)).getKey();
		} else if (sumConfidence.size() == 1) return sumConfidence.entrySet().iterator().next().getKey();
		else return "";
	}

	private static void orLastCharContentIsDot(DialogProgress dialogProgress) {
		String content = dialogProgress.getContent().trim();
		if (!content.endsWith(".") || !content.endsWith("?") || !content.endsWith("!"))
			dialogProgress.setContent(content + ".");
	}

	private static void setCompletedCategory(DialogProgress dialogProgress, String maxKey) {
		if (maxKey.equalsIgnoreCase("Gra_aktorska")) dialogProgress.setActing(true);
		else if (maxKey.equalsIgnoreCase("fabula")) dialogProgress.setStory(true);
		else if (maxKey.equalsIgnoreCase("efekty_specjalne")) dialogProgress.setSpecialEffects(true);
	}

	private static void calculateSumConfidence(Map<String, Double> sumConfidence, WitResponse witResponse) {
		for (Entities entitie : witResponse.getEntitiesArrayList()) {
			double sum = 0;
			for (Value value : entitie.getValueArrayList()) {
				sum += value.getConfidence();
			}
			if (!sumConfidence.containsKey(entitie.getName())) {
				sumConfidence.put(entitie.getName(), sum);
			} else sumConfidence.replace(entitie.getName(), sumConfidence.get(entitie.getName()) + sum);
		}
	}

	private static boolean isEndConversation(DialogProgress dialogProgress) {
		return dialogProgress.getActing() && dialogProgress.getStory() && dialogProgress.getSpecialEffects();
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
		return questionsList.get(dialogProgress.getNumberCurrentQuestions() - 1);
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
		dialogProgress.setNumberCurrentQuestions(0);
	}

	public static void startDialog(DialogProgress dialogProgress) throws NotFoundException {
		randomCategoryQuestion(dialogProgress);
		String question = randomQuestion(dialogProgress);
		dialogProgress.setCodeHtml(HtmlCode.botCode(question));
	}
}
