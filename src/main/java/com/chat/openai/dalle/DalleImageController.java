package com.chat.openai.dalle;

import com.chat.openai.dto.FormInputDTO;
import com.chat.openai.client.OpenAiApiClient;
import com.chat.openai.client.OpenAiApiClient.OpenAiService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class DalleImageController {
	
	public static final String IMAGE_PAGE = "image";
	
	@Autowired private ObjectMapper jsonMapper;
	@Autowired private OpenAiApiClient client;
	
	private String drawImageWithDallE(String prompt) throws Exception {
		var generation = GenerationRequest.defaultWith(prompt);
		var postBodyJson = jsonMapper.writeValueAsString(generation);
		var responseBody = client.postToOpenAiApi(postBodyJson, OpenAiService.DALL_E);
		var completionResponse = jsonMapper.readValue(responseBody, GenerationResponse.class);
		return completionResponse.firstImageUrl().orElseThrow();
	}
	
	@GetMapping(IMAGE_PAGE)
	public String paintImage() {
		return IMAGE_PAGE;
	}
	
	@PostMapping(IMAGE_PAGE)
	public String drawImage(Model model, FormInputDTO dto) throws Exception {
		model.addAttribute("request", dto.prompt());
		model.addAttribute("imageUri", drawImageWithDallE(dto.prompt()));
		return IMAGE_PAGE;
	}

}
