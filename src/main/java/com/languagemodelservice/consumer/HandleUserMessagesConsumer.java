package com.languagemodelservice.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.languagemodelservice.dto.MessageDTO;
import com.languagemodelservice.services.LanguageModelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * Author: harjeevansingh
 */

@Component
@Slf4j
public class HandleUserMessagesConsumer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    private LanguageModelService languageModelService;

    @Autowired
    public HandleUserMessagesConsumer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "user_messages", groupId = "language-model-group")
    public void handleUserMessage(String messageJson) {
        try {
            MessageDTO message = objectMapper.readValue(messageJson, MessageDTO.class);
            String aiResponse = languageModelService.generateResponse(message.getContent(), message.getConversationHistory());

            MessageDTO responseMessage = new MessageDTO();
            responseMessage.setMessageId(message.getMessageId());
            responseMessage.setConversationId(message.getConversationId());
            responseMessage.setUserId(message.getUserId());
            responseMessage.setContent(aiResponse);

            String responseJson = objectMapper.writeValueAsString(responseMessage);
            kafkaTemplate.send("ai-responses", responseJson);
        } catch (Exception e) {
            log.error("Error handling user message", e);
            // TODO: Handle error, possibly by sending an error message to another Kafka topic
        }
    }
}
