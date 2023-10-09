package com.test.prophius.dtos.requestbodies;

public class ActivateUserRequest {
    private String username;
    private boolean active;

    public ActivateUserRequest() {
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
