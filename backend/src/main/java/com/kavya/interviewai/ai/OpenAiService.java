package com.kavya.interviewai.ai;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class OpenAiService {

    @Value("${openai.api.key}")
    private String apiKey;

    private final RestClient restClient = RestClient.create();

    public String analyzeResume(String resumeText) {

        Map<String, Object> requestBody = Map.of(
                "model", "gpt-4.1-mini",
                "input", "Analyze this resume for a technical interview preparation platform. " +
                        "Return strengths, weaknesses, missing skills, and 5 interview questions:\n\n" +
                        resumeText
        );

        Map response = restClient.post()
                .uri("https://api.openai.com/v1/responses")
                .header("Authorization", "Bearer " + apiKey)
                .header("Content-Type", "application/json")
                .body(requestBody)
                .retrieve()
                .body(Map.class);

        List output = (List) response.get("output");
        Map firstOutput = (Map) output.get(0);
        List content = (List) firstOutput.get("content");
        Map firstContent = (Map) content.get(0);

        return (String) firstContent.get("text");
    }
}