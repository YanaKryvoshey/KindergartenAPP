package com.classy.myapplication.Object;

public class ParentUser extends AppUser {

    private String gardenName;


    public ParentUser(String name, String id, String email, String password, String phoneNumber,String gardenName) {
        super(name,id,email, password, phoneNumber);
        this.gardenName = gardenName;
    }

    public ParentUser(String gardenName) {
        this.gardenName = gardenName;
    }

    public ParentUser() {
    }


    public String getGardenName() {
        return gardenName;
    }

    public void setGardenName(String gardenName) {
        this.gardenName = gardenName;
    }
}
