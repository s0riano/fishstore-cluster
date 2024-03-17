package com.seafood.shop.dto.openingHours;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpeningHoursDTO {
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String notes;
}
