package com.seafood.inventory.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SeafoodType {
    SALMON("Salmon"),
    TUNA("Tuna"),
    LOBSTER("Lobster"),
    CRAB("Crab"),
    SHRIMP("Shrimp"),
    HALIBUT("Halibut"),
    MACKEREL("Mackerel"),
    COD("Cod"),
    OYSTER("Oyster"),
    SCALLOP("Scallop"),
    HERRING("Herring");

    private final String description;
}
