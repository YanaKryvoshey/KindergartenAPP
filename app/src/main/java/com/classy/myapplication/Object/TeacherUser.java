package com.classy.myapplication.Object;

public class TeacherUser extends AppUser{
    private String gardenName;

    public TeacherUser(){

    }

    public TeacherUser(String name, String id, String email, String password, String phoneNumber, String gardenName) {
        super(name, id, email, password, phoneNumber);
        this.gardenName = gardenName;
    }

    public TeacherUser(String gardenName) {
        this.gardenName = gardenName;
    }

    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
