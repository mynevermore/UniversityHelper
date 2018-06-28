package com.mynevermore.universityhelper.model;

public class Grade {

    private int mId;
    private String mUnit;
    private String mGrade;

    public Grade(){}

    public Grade(int id, String unit, String grade) {
        mId = id;
        mUnit = unit;
        mGrade = grade;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getUnit() {
        return mUnit;
    }

    public void setUnit(String unit) {
        mUnit = unit;
    }

    public String getGrade() {
        return mGrade;
    }

    public void setGrade(String grade) {
        mGrade = grade;
    }
}
