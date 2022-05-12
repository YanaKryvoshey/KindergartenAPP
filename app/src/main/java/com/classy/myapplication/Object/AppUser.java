package com.classy.myapplication.Object;

public class AppUser {

    private String name;
    private String id;
    private String email;
    private String password;
    private String phoneNumber;

    public AppUser(String name, String id, String email, String password, String phoneNumber) {
        this.name = name;
        this.id = id;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public AppUser() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
