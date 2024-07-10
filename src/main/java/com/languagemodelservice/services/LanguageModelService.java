package com.languagemodelservice.services;

/**
 * Author: harjeevansingh
 */

import com.languagemodelservice.utils.FaqUtil;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class LanguageModelService {

    private final OpenAiService openAiService;

    @Autowired
    public LanguageModelService(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    public String generateResponse(String prompt, List<String> conversationHistory) {
        List<ChatMessage> messages = new ArrayList<>();

        // Add system message to set the context
        messages.add(new ChatMessage("system", "You are a helpful AI assistant for an online lending app QuickLoan. Only answer the questions which can be answered using the attached FAQs, otherwise politely and professionally deny answering and mention to ask relevant questions only i.e. related to QuickLoan. Use the following FAQ to assist with answers when relevant:\n\n" + FaqUtil.loadFaq()));

        // Add conversation history
        if(Objects.nonNull(conversationHistory)){
            for (String message : conversationHistory) {
                messages.add(new ChatMessage("user", message));
            }
        }

        // Add the current prompt
        messages.add(new ChatMessage("user", prompt));

        ChatCompletionRequest completionRequest = ChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo")
                .build();

        try {
            return openAiService.createChatCompletion(completionRequest).getChoices().get(0).getMessage().getContent();
        } catch (Exception e) {
            log.error("Error generating response", e);
            return "I'm sorry, I'm having trouble generating a response right now.";
        }
    }
}