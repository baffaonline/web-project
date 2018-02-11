package com.kustov.webproject.entity;


/**
 * The Enum UserType.
 */
public enum UserType {

    /**
     * The user.
     */
    USER("user"), /**
     * The admin.
     */
    ADMIN("admin"), /**
     * The guest.
     */
    GUEST("guest");

    /**
     * The type name.
     */
    private String typeName;

    /**
     * Instantiates a new user type.
     *
     * @param typeName the type name
     */
    UserType(String typeName) {
        this.typeName = typeName;
    }

    /**
     * Gets the type name.
     *
     * @return the type name
     */
    public String getTypeName() {
        return typeName;
    }
}
