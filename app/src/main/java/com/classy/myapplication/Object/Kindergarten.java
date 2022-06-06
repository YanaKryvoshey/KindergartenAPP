package com.classy.myapplication.Object;


import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Kindergarten extends RealmObject implements Serializable {

    @PrimaryKey
    public String name;
    public String photoRef;
    public String rat;
    public Double lat;
    public Double lng;
    public String address;
    //public ArrayList<TeacherUser> teachers;


    public Kindergarten(String name, String photoRef, String rat, Double lat, Double lng,String address) {
        this.name = name;
        this.photoRef = photoRef;
        this.rat = rat;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
    }

    public Kindergarten() {
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRat() {
        return rat;
    }

    public void setRat(String rat) {
        this.rat = rat;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPhotoRef() {
        return photoRef;
    }

    public void setPhotoRef(String photoRef) {
        this.photoRef = photoRef;
    }



}
