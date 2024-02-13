package com.fishstore.authentication.enums;

public enum ShopRole {
    OWNER("Owner"),
    STAFF("Staff"),
    DEACTIVATED("This user does not have the required permissions anymore"),
    ASSISTANT("Assistant");

    private final String role;

    ShopRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
