package com.mynevermore.universityhelper.model;

public class Task {

    private int mId;
    private String mTitle;
    private String mDate;
    private String[] mText;
    private boolean[] mCompleted;

    public Task(){}

    public Task(int id, String title, String date, String[] text, boolean[] completed) {
        mId = id;
        mTitle = title;
        mDate = date;
        mText = text;
        mCompleted = completed;
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

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String[] getText() {
        return mText;
    }

    public void setText(String[] text) {
        mText = text;
    }

    public boolean[] getCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean[] completed) {
        mCompleted = completed;
    }

}
