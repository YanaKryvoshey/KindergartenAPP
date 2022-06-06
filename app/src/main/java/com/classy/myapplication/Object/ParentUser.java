package com.classy.myapplication.Object;

public class ParentUser extends AppUser {
    String usertype = "ParentUser";



    public ParentUser(String name, String id, String email, String password, String phoneNumber,String gardenName) {
        super(name,id,email, password, phoneNumber,gardenName);

    }

    public ParentUser() {
    }

    public String getUsertype() {
        return usertype;
    }


}
