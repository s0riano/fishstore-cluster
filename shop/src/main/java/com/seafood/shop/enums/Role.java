package com.seafood.shop.enums;

public enum Roles {
    OWNER("Owner"),
    //MANAGER("Manager"),
    STAFF("Staff"),
    DEACTIVATED("This user does not have the required permissions anymore"),
    ASSISTANT("Assistant");

    private final String role;

    Roles(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
