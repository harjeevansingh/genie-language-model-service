package com.languagemodelservice.dto;

/**
 * Author: harjeevansingh
 */

import lombok.Data;

import java.util.List;

@Data
public class MessageDTO {
    private String messageId;
    private Long conversationId;
    private Long userId;
    private String content;
    private List<String> conversationHistory;
}
