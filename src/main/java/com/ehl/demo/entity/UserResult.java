package com.ehl.demo.entity;

public class UserResult {
    private String authority;
    private String namespace;

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Override
    public String toString() {
        return "UserResult{" +
                "authority='" + authority + '\'' +
                ", namespace='" + namespace + '\'' +
                '}';
    }
}
