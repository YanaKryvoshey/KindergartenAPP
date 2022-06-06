package com.classy.myapplication.Object;

public class ParentSearchGardenUser extends AppUser {
    String usertype = "ParentSearchGardenUser";

    public ParentSearchGardenUser(String name, String id, String email, String password, String phoneNumber, String gardenName) {
        super(name, id, email, password, phoneNumber,gardenName);

    }

    public ParentSearchGardenUser() {
    }

    public String getUsertype() {
        return usertype;
    }


}
