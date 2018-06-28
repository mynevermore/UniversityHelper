package com.mynevermore.universityhelper.model;

public class Note {

    private int mId;
    private String mTitle;
    private String mDate;
    private String mText;

    public Note() {}

    public Note(int id, String title, String date, String text) {
        mId = id;
        mTitle = title;
        mDate = date;
        mText = text;
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

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }
}
