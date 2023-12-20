package com.fishtore.price.config;

import com.fishtore.price.enums.SeafoodType;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

import java.util.Optional;

@ReadingConverter
public class StringToEnumConverter implements Converter<String, SeafoodType> {
    @Override
    public SeafoodType convert(@NotNull String source) {
        return Optional.of(source)
                .map(String::toUpperCase)
                .map(this::getEnumValue)
                .orElse(SeafoodType.ERROR); // Replace SeafoodType.DEFAULT with your default enum value
    }

    private SeafoodType getEnumValue(String source) {
        try {
            return SeafoodType.valueOf(source);
        } catch (IllegalArgumentException e) {
            // Log the error or handle it as needed
            return SeafoodType.ERROR; // Replace SeafoodType.DEFAULT with your default enum value
        }
    }
}