package com.adamelmurzaev.springsecurirysuleymanov.model;

public enum Permission {
    DEVELOPERS_READ("developers:read"),
    DEVELOPERS_WRITE("developers:write");

    private final String PERMISSION;

    Permission(String permission){
        this.PERMISSION = permission;
    }

    public String getPermission() {
        return PERMISSION;
    }
}
