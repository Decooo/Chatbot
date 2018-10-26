package com.jakub.chatbot.util;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;

public class CSVReader {

	private static CSVReader instance;
	private HashMap<String, String> emotions = new HashMap<>();

	private CSVReader() throws IOException {
		doEmotionsMap();
	}

	private void doEmotionsMap() throws IOException {
		Reader in = new FileReader("src/main/resources/slownik_anotacji_emocjonlanej.csv");
		Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(in);

		for (CSVRecord record : records) {
			emotions.put(record.get("lemat"), record.get("stopien_nacechowania"));
		}
	}

	public static synchronized CSVReader getInstance() throws IOException {
		if (instance == null) {
			instance = new CSVReader();
		}
		return instance;
	}

	public HashMap<String, String> getEmotions() {
		return emotions;
	}
}
