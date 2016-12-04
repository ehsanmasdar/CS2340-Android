package com.ehsandev.cs2340.model;

public class User {
    private String username;
    private String password;
    private int failed;
    private boolean banned;
    private boolean blocked;
    private AccessLevel level;

    public User(String username, String password, AccessLevel level) {
        this.username = username;
        this.password = password;
        this.level = level;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, AccessLevel level) {
        this.username = username;
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getFailed() {
        return failed;
    }

    public void setFailed(int failed) {
        this.failed = failed;
    }

    public boolean isBanned() {
        return banned;
    }

    public void setBanned(boolean banned) {
        this.banned = banned;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public AccessLevel getLevel() {
        return level;
    }

    public void setLevel(AccessLevel level) {
        this.level = level;
    }
}
