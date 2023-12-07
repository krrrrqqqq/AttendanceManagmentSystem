package com.example.attendensmanagmentsystem;


public class User {
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String role;

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}

