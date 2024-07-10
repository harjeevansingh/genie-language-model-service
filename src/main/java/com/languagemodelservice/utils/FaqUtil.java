package com.languagemodelservice.utils;

/**
 * Author: harjeevansingh
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FaqUtil {
    public static String loadFaq() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, List<Map<String, String>>> faqMap = mapper.readValue(
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
            e.printStackTrace();
            return "";
        }
    }
}
