package com.seafood.shop.dto.openingHours;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RemoveOpeningHourDTO {
    UUID id; //id for the date that should be removed
    //TODO: might adjust note's functionality for when removing multiple dates as you dont want to add a note for each but one note as a whole
    String notes; //Can add a note to why the date was removed.
}
