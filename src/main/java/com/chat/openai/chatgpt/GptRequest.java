package com.chat.openai.chatgpt;

public record GptRequest(String model, String prompt,
						 double temperature, int max_tokens) {
	
	public static GptRequest defaultWith(String prompt) {
		return new GptRequest("text-davinci-003", prompt, 0.7, 100);
	}
	
}
