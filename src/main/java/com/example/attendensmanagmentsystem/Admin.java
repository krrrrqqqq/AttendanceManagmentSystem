package com.example.attendensmanagmentsystem;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private String adminId;
    private String adminName;

    private List<User> userList;

    public Admin(String adminId, String adminName) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.userList = new ArrayList<>();
    }


    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public void addUser(User user) {
        userList.add(user);
        System.out.println("User added: " + user.getUsername());
    }

    public void removeUser(User user) {
        userList.remove(user);
        System.out.println("User removed: " + user.getUsername());
    }

    public List<User> getAllUsers() {
        return userList;
    }

}