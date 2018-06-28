package com.mynevermore.universityhelper.model;

public class Assignment {

    private int mId;
    private String mTitle;
    private String mDueDate;
    private String mDueTime;
    private int mTaskId;
    private boolean mCompleted;
    private String mText;

    public Assignment(){}

    public Assignment(int id){ mId = id; }

    public Assignment(int id, String title, String dueDate, String dueTime, int taskId, boolean completed, String text) {
        mId = id;
        mTitle = title;
        mDueDate = dueDate;
        mDueTime = dueTime;
        mTaskId = taskId;
        mCompleted = completed;
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

    public String getDueDate() {
        return mDueDate;
    }

    public void setDueDate(String dueDate) {
        mDueDate = dueDate;
    }

    public int getTaskId() {
        return mTaskId;
    }

    public void setTaskId(int taskId) {
        mTaskId = taskId;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getDueTime() {
        return mDueTime;
    }

    public void setDueTime(String dueTime) {
        mDueTime = dueTime;
    }
}
