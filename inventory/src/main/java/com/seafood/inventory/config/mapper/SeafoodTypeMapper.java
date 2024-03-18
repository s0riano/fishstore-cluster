package com.seafood.inventory.config.mapper;

import com.seafood.inventory.entities.enums.SeafoodType;
import com.fasterxml.jackson.databind.ObjectMapper; // If you're using Jackson for JSON parsing

import java.io.IOException;

public class SeafoodTypeMapper {

    public static SeafoodType mapToLocalSeafoodType(String seafoodTypeStr) {
        try {
            return SeafoodType.valueOf(seafoodTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown SeafoodType: " + seafoodTypeStr);
        }
    }

    // If you're receiving a JSON object
    public static SeafoodType mapFromJson(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String seafoodTypeStr = mapper.readTree(json).get("seafoodType").asText();
        return mapToLocalSeafoodType(seafoodTypeStr);
    }
}


