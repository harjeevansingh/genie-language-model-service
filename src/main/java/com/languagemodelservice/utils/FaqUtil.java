package com.languagemodelservice.utils;

/**
 * Author: harjeevansingh
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
public class FaqUtil {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static String cachedFaq = null;

    public static String loadFaq() {
        if (cachedFaq == null) {
            synchronized (FaqUtil.class) {
                if (cachedFaq == null) {
                    cachedFaq = loadFaqFromFile();
                }
            }
        }
        return cachedFaq;
    }

    private static String loadFaqFromFile() {
        try {
            Map<String, List<Map<String, String>>> faqMap = MAPPER.readValue(
                    new ClassPathResource("faq.json").getInputStream(),
                    Map.class
            );
            List<Map<String, String>> faqs = faqMap.get("faqs");
            StringBuilder faqString = new StringBuilder("FAQ:\n");
            for (Map<String, String> faq : faqs) {
                faqString.append("Q: ").append(faq.get("question")).append("\n");
                faqString.append("A: ").append(faq.get("answer")).append("\n\n");
            }
            return faqString.toString();
        } catch (IOException e) {
            log.error("Error loading FAQ", e);
            return "I apologize, but I'm currently experiencing difficulties. Please try again later. If the problem persists, contact support.";
        }
    }
}