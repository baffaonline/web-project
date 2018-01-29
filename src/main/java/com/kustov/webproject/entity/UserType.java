package com.kustov.webproject.entity;

public enum UserType {
    USER("user"), ADMIN("admin"), GUEST("guest");

    private String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
