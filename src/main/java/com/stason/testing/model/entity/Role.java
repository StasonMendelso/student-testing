package com.stason.testing.model.entity;

public enum Role {
    GUEST,
    STUDENT,
    ADMIN;

    @Override
    public String toString() {
        if (this.equals(GUEST)) return "Role{Guest}";
        if (this.equals(STUDENT)) return "Role{Student}";
        if (this.equals(ADMIN)) return "Role{Admin}";
        return null;
    }
}
