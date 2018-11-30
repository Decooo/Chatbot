package com.jakub.chatbot.util;

import morfologik.stemming.IStemmer;
import morfologik.stemming.WordData;
import morfologik.stemming.polish.PolishStemmer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SentimentAnalysis {

	private static ArrayList<String> phraseList = new ArrayList<>();

	public double analysis(String opinion) throws IOException {
		var wordList = separationWordInSentences(opinion);
		var phrase = doPhraseList(wordList);
		return countEmotionsInPhrase(phrase);
	}

	private double countEmotionsInPhrase(ArrayList<String> phrases) throws IOException {
		HashMap<String, String> emotions = CSVReader.getInstance().getEmotions();

		double positivePoints = 0;
		double negativePoints = 0;

		for (String phrase : phrases) {
			String[] pairs = phrase.split(" ");
			int valueWord1 = getValue(emotions.getOrDefault(pairs[0], ""));
			int valueWord2 = getValue(emotions.getOrDefault(pairs[1], ""));

			if ((valueWord1 >= 0 && valueWord2 >= 0) || (valueWord1 <= 0 && valueWord2 <= 0)) {
				if (valueWord1 + valueWord2 > 0) {
					positivePoints += (valueWord1 + valueWord2);
				} else negativePoints += (valueWord1 + valueWord2);
			}

			System.out.println(pairs[0] + " " + pairs[1] + " | " + valueWord1 + " : " + valueWord2);

		}

		System.out.println("negativePoints = " + negativePoints);
		System.out.println("positivePoints = " + positivePoints);

		double result  = (positivePoints / (negativePoints * -1 + positivePoints));
		return Math.round(result * 100D) / 100D * 10;
	}

	private int getValue(String s) {
		if (s.equalsIgnoreCase("+ m")) {
			return ValueEmotion.STRONGPOSITIVE.getValue();
		} else if (s.equalsIgnoreCase("+ s")) {
			return ValueEmotion.WEAKPOSITIVE.getValue();
		} else if (s.equalsIgnoreCase("- s")) {
			return ValueEmotion.WEAKNEGATIVE.getValue();
		} else if (s.equalsIgnoreCase("- m")) {
			return ValueEmotion.STRONGNEGATIVE.getValue();
		} else return ValueEmotion.NATURAL.getValue();
	}

	private ArrayList<String> doPhraseList(ArrayList<String[]> wordList) {
		phraseList.clear();
		for (String[] aWordList : wordList) {
			var stemList = stemming(new ArrayList<>(Arrays.asList(aWordList)));
			var tags = new ArrayList<String>();
			var words = new ArrayList<String>();

			for (var stem : stemList) {
				String[] pairs = stem.toString().substring(1, stem.toString().length() - 1).split(",");
				tags.add(pairs[0].split("=")[1]);
				words.add(pairs[1].split("=")[1]);
			}
			checkPhrase(tags, words);
		}
		return phraseList;
	}

	private ArrayList stemming(ArrayList<String> words) {
		ArrayList<HashMap<String, String>> result = new ArrayList<>();
		for (String word : words) {
			var stemmingWord = stem(word);
			if (stemmingWord.length >= 2) {
				HashMap<String, String> stem = new HashMap<>();
				stem.put("word", stemmingWord[0]);
				stem.put("tag", tagFormat(stemmingWord[1]));
				result.add(stem);
			}
		}
		return result;
	}

	private String[] stem(String word) {
		IStemmer s = new PolishStemmer();
		ArrayList<String> result = new ArrayList<String>();
		for (WordData wd : s.lookup(word)) {
			result.add(wd.getStem().toString());
			result.add(wd.getTag().toString());
		}
		return result.toArray(new String[result.size()]);
	}

	private String tagFormat(String tag) {
		if (tag.contains(":") && tag.contains("+")) {
			String tags = tag.split(":")[0];
			return checkCharPlus(tags);
		} else if (tag.contains(":")) {
			return tag.split(":")[0];
		} else return checkCharPlus(tag);
	}

	private String checkCharPlus(String tags) {
		if (tags.contains("+")) {
			return tags.split("\\+")[0];
		} else return tags;
	}

	private void checkPhrase(ArrayList<String> tags, ArrayList<String> words) {
		// First ADJ, SUBST/GER, anything
		for (int i = 0; i < tags.size() - 1; ++i) {
			if ("adj".equals(tags.get(i).substring(0, 3)) && "subst".equals(tags.get(i + 1))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			} else if ("adj".equals(tags.get(i).substring(0, 3)) && "ger".equals(tags.get(i + 1))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			}
		}
		// First ADV, ADJ, NOT SUBST/GER
		for (int i = 0; i < tags.size() - 2; ++i) {
			if ("adv".equals(tags.get(i)) && "adj".equals(tags.get(i + 1).substring(0, 3)) && (!("subst".equals(tags.get(i + 2))) && !("ger".equals(tags.get(i + 2))))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			}
		}
		// First ADJ, ADJ, NOT SUBST/GER
		for (int i = 0; i < tags.size() - 2; ++i) {
			if ("adj".equals(tags.get(i).substring(0, 3)) && "adj".equals(tags.get(i + 1).substring(0, 3)) && (!("subst".equals(tags.get(i + 2))) && !("ger".equals(tags.get(i + 2))))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			}
		}
		// First SUBST/GER, ADJ, NOT SUBST/GER
		for (int i = 0; i < tags.size() - 2; ++i) {
			if (("subst".equals(tags.get(i)) || "ger".equals(tags.get(i))) && "adj".equals(tags.get(i + 1).substring(0, 3)) && (!("subst".equals(tags.get(i + 2))) && !("ger".equals(tags.get(i + 2))))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			}
		}
		// First ADV, VERB
		for (int i = 0; i < tags.size() - 1; ++i) {
			if ("adv".equals(tags.get(i)) && "verb".equals(tags.get(i + 1))) {
				phraseList.add(words.get(i) + " " + words.get(i + 1));
			}
		}
	}

	private ArrayList<String[]> separationWordInSentences(String text) {
		text = separationSentences(text);
		var wordList = new ArrayList<String[]>();

		String[] sentences = text.split("#");
		for (String sentence : sentences) {
			String[] words = sentence.trim().split(" ");
			if (words.length > 3) {
				wordList.add(words);
			}
		}
		return wordList;
	}

	private String separationSentences(String text) {
		text = deleteSpecialCharsAndDigits(text);
		text = text.replaceAll("[\\.\\!\\?]", " #");
		text = text.trim().replaceAll(" +", " ");
		return text;
	}

	private String deleteSpecialCharsAndDigits(String text) {
		return text.replaceAll("[\\<\\(\\#\\[\\{\\\\\\^\\-\\=\\$\\|\\]\\}\\)\\*\\+\\>\\,\\d]", " ");
	}

}



