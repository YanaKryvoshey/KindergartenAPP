package com.classy.myapplication;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Kindergarten extends RealmObject {

    @PrimaryKey
    public String name;
    public String photoRef;
    public String rat;
    public Double lat;
    public Double lng;

    public Kindergarten(String name, String photoRef, String rat, Double lat, Double lng) {
        this.name = name;
        this.photoRef = photoRef;
        this.rat = rat;
        this.lat = lat;
        this.lng = lng;
    }

    public Kindergarten() {
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

    @Override
    public String toString() {
        return "Kindergarten{" +
                "name='" + name + '\'' +
                ", rat='" + rat + '\'' +
                '}';
    }

}
