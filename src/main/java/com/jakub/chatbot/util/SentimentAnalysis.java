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
//	public void turneyAlgotithm() {

//			// Yoooo!! Time to calculate the sentiments
//			// The probabilities of phrases are done via Google
//			// Google access is done via Selenium via Python RPC server!
//			// Lets hit the road!
//			System.out.println("\nEnter the port number on which Python-Selenium server is running:\t");
//			the_port = terminalInput.nextLine();
//			try{
//				Integer.parseInt(the_port);
//			}
//			catch(Exception e){
//				System.out.println("\nInvalid port number. Exiting now!\n");
//				System.exit(0);
//			}
//			System.out.println("\nInteracting with Google...This may take several minutes...\n");
//			XmlRpcClient google_rpc=new XmlRpcClient("http://localhost:"+the_port+"/");
//			params.clear();
//			params.addElement(final_list);
//			// RPC initialization
//			Vector params2 = new Vector();
//			params2.addElement("test#");
//			result = google_rpc.execute("get_count", params2);
//			// Initialization ended
//			result = google_rpc.execute("get_count", params);
//			String hits = ((String) result);
//			//System.out.println(hits);
//			//hits for "Excellent" = 352000000, "poor" = 197000000
//			String[] nums = hits.split(",");
//			ArrayList PMI = new ArrayList();
//			int ind=0;
//			Double d = 0.0;
//			for(ind=0;ind<nums.length;ind=ind+2){
//				d = ((Double.parseDouble(hits.split(",")[ind])*197000000/(Double.parseDouble(hits.split(",")[ind+1])*352000000)));
//				PMI.add(d.toString());
//			}
//			Double res = 0.0;
//			for (ind=0;ind<PMI.size();++ind){
//				res +=Math.log(Double.parseDouble(((String) PMI.get(ind))))/Math.log(2);
//			}
//			res = res/PMI.size();
//			System.out.printf("Sentiment Value - SentimentAnalysis Algorithm -  is: %.2f\n\n", res);
//
//
//
//			// Now run sentiment analysis from database - on Sentinet
//			// DATABASE SENTINET ANALYSIS
//
//			final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//			System.out.println("Enter database name:\t");
//			the_port = terminalInput.nextLine();
//			final String DB_URL = "jdbc:mysql://localhost:3306/"+ the_port;
//			System.out.println("\nEnter database username:\t");
//			the_port = terminalInput.nextLine();
//			final String USER = the_port.trim();
//			System.out.println("\nEnter database password:\t");
//			the_port = terminalInput.nextLine();
//			final String PASS = the_port.trim();
//			Connection conn = null;
//
//			try{
//				conn = DriverManager.getConnection(DB_URL, USER, PASS);
//			}
//			catch(Exception e){
//				System.out.println("Sorry! Database connection could not be done!!");
//				System.exit(0);
//			}
//			Statement stmt = conn.createStatement();
//			String[] db_sent = final_list.split("#");
//			String[] word_list;
//			String my_word, ex_handler;
//			Integer tot_res=0;
//			Integer ii, jj, ii_stat, jj_stat, ii_pol, jj_pol;
//
//			for(ind=0;ind<db_sent.length;++ind){
//				word_list = db_sent[ind].split(" ");
//				ii=0;jj=0;ii_stat=0;jj_stat=0;ii_pol=0;jj_pol=0;
//				for(int inter=0;inter<2;++inter){
//					my_word = word_list[inter].trim();
//					// Check first database
//					try{
//						ResultSet rs = stmt.executeQuery("select * from sent_lexicon_1 where word='"+my_word+"'");
//						while(rs.next()){
//							if(inter==0 && "positive".equals(rs.getString("polarity"))){
//								ii_pol=1;
//								if("strongsubj".equals(rs.getString("type"))){
//									ii_pol=2;
//								}
//							}
//							if(inter==0 && "negative".equals(rs.getString("polarity"))){
//								ii_pol=-1;
//								if("strongsubj".equals(rs.getString("type"))){
//									ii_pol=-2;
//								}
//							}
//
//							if(inter==1 && "positive".equals(rs.getString("polarity"))){
//								jj_pol=1;
//								if("strongsubj".equals(rs.getString("type"))){
//									jj_pol=2;
//								}
//							}
//							if(inter==1 && "negative".equals(rs.getString("polarity"))){
//								jj_pol=-1;
//								if("strongsubj".equals(rs.getString("type"))){
//									jj_pol=-2;
//								}
//							}
//
//						}
//
//					}
//					catch(Exception e){
//						if(inter==0){
//							ii_stat=1;
//						}
//						else{
//							jj_stat=1;
//						}
//						ex_handler="";
//					}
//					// check second database
//				}
//				if(jj_pol!=0){
//					if(ii_pol*jj_pol>0){
//						tot_res+= ii_pol+ jj_pol;
//					}
//				}
//
//			}
//			System.out.println("\nSentiment analysis from database! High positive means higher positive sentiment.");
//			System.out.println("And lower negative number means larger negative sentiment.\n");
//			System.out.printf("Seniment score from sentinet: %d\n\n", tot_res);
//
//		}
//		catch (Exception e) {
//			System.err.println("JavaClient: " + e);
//		}
//
//	}


