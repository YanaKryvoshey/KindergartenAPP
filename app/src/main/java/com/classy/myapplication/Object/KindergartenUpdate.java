package com.classy.myapplication.Object;

public class KindergartenUpdate {

    private Kindergarten kindergarten;
    private int children_num;
    private int ageRange;
    private int teachers_num;
    private  String massege;


    public KindergartenUpdate() {
    }

    public KindergartenUpdate(Kindergarten kindergarten, int children_num, int ageRange, int teachers_num, String massege) {
        this.kindergarten = kindergarten;
        this.children_num = children_num;
        this.ageRange = ageRange;
        this.teachers_num = teachers_num;
        this.massege = massege;
    }

    public Kindergarten getKindergarten() {
        return kindergarten;
    }

    public void setKindergarten(Kindergarten kindergarten) {
        this.kindergarten = kindergarten;
    }

    public int getChildren_num() {
        return children_num;
    }

    public void setChildren_num(int children_num) {
        this.children_num = children_num;
    }

    public int getAgeRange() {
        return ageRange;
    }

    public void setAgeRange(int ageRange) {
        this.ageRange = ageRange;
    }

    public int getTeachers_num() {
        return teachers_num;
    }

    public void setTeachers_num(int teachers_num) {
        this.teachers_num = teachers_num;
    }

    public String getMassege() {
        return massege;
    }

    public void setMassege(String massege) {
        this.massege = massege;
    }
}
