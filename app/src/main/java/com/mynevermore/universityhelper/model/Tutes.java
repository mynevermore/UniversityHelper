package com.mynevermore.universityhelper.model;

public class Tutes {

    private int mId;
    private String mTitle;
    private String mDay;
    private String mTime;
    private String mType;
    private String mLocation;

    public Tutes(){}

    public Tutes(int id, String title, String day, String time, String type, String location) {
        this.mId = id;
        this.mTitle = title;
        this.mDay = day;
        this.mTime = time;
        this.mType = type;
        this.mLocation = location;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDay() {
        return mDay;
    }

    public void setDay(String day) {
        mDay = day;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getTuteType() {
        return mType;
    }

    public void setTuteType(String type) {
        mType = type;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        mLocation = location;
    }
}
