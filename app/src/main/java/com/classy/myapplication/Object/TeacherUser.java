package com.classy.myapplication.Object;

public class TeacherUser extends AppUser{

    String usertype = "TeacherUser";

    public TeacherUser(){

    }

    public TeacherUser(String name, String id, String email, String password, String phoneNumber, String gardenName) {
        super(name, id, email, password, phoneNumber,gardenName);
    }

    public String getUsertype() {
        return usertype;
    }


}
