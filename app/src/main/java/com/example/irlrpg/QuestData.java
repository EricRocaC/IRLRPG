package com.example.irlrpg;

import android.os.Parcel;
import android.os.Parcelable;

public class QuestData implements Parcelable {
    private String descQuest;
    private String importance;
    private String expQuest;
    private String calendarQuest;
    private String userUID;

    public QuestData(){

    }

    public QuestData(String descQuest, String importance, String expQuest, String calendarQuest, String userUID){
        this.descQuest = descQuest;
        this.importance = importance;
        this.expQuest = expQuest;
        this.calendarQuest = calendarQuest;
        this.userUID = userUID;
    }

    protected QuestData(Parcel in) {
        descQuest = in.readString();
        importance = in.readString();
        expQuest = in.readString();
        calendarQuest = in.readString();
    }

    public static final Creator<QuestData> CREATOR = new Creator<QuestData>() {
        @Override
        public QuestData createFromParcel(Parcel in) {
            return new QuestData(in);
        }

        @Override
        public QuestData[] newArray(int size) {
            return new QuestData[size];
        }
    };

    public String getCalendarQuest() {
        return calendarQuest;
    }

    public void setCalendarQuest(String calendarQuest) {
        this.calendarQuest = calendarQuest;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDescQuest() {
        return descQuest;
    }

    public void setDescQuest(String descQuest) {
        this.descQuest = descQuest;
    }

    public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }

    public String getExpQuest() {
        return expQuest;
    }

    public void setExpQuest(String expQuest) {
        this.expQuest = expQuest;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descQuest);
        dest.writeString(importance);
        dest.writeString(expQuest);
        dest.writeString(calendarQuest);
    }
}
