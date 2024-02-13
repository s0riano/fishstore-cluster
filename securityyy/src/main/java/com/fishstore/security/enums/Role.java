package com.fishstore.security.enums;

public enum Role {
    OWNER("Owner"),
    STAFF("Staff"),
    DEACTIVATED("This user does not have the required permissions anymore"),
    ASSISTANT("Assistant");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
