package com.stason.testing.model.entity;

public enum Role {
    GUEST(1),
    STUDENT(2),
    ADMIN(3);
    Role(int id){
        this.id=id;
    }
    private int id;

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        if (this.equals(GUEST)) return "Role{Guest}";
        if (this.equals(STUDENT)) return "Role{Student}";
        if (this.equals(ADMIN)) return "Role{Admin}";
        return null;
    }
}
