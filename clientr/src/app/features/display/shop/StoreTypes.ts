import {UUID} from "crypto";

export interface DisplayStoreOld {
    id: string | number| UUID;
    name: string;
    location: string;
    description: string;
    nextDate: string;
}
export interface DisplayStore{
    id: string;
    name: string;
    items: SeafoodType[];
    location: string;
    description: string;
}

export enum SeafoodType {
    SALMON = "SALMON",
    TUNA = "TUNA",
    CRAB = "CRAB",
    SHRIMP = "SHRIMP",
    LOBSTER = "LOBSTER"
    // ... other types
}
