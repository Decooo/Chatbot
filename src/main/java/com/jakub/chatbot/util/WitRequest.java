package com.jakub.chatbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WitRequest {

	public void doRequest(String message){
		try {
			message = URLEncoder.encode(message, "UTF-8").replace("+", "%20");
			URL url = new URL("https://api.wit.ai/message?v=20170218&q=" + message);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", "Bearer AUM4D36AW3YRXDCUDETHPT7LC4Y4SOXM");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
					(conn.getInputStream())));

			String response;
			System.out.println("Output from Server .... \n");
			while ((response = br.readLine()) != null) {
				System.out.println(response);
			}

			conn.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
