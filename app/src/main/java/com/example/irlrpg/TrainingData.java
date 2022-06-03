package com.example.irlrpg;

import android.os.Parcel;
import android.os.Parcelable;

public class TrainingData implements Parcelable {
    private String descTraining;
    private String difficult;
    private String expTrain;
    private String userUID;

    public TrainingData(){

    }

    public TrainingData(String descTraining, String difficult, String expTrain, String userUID){
        this.descTraining = descTraining;
        this.difficult = difficult;
        this.expTrain = expTrain;
        this.userUID = userUID;
    }

    protected TrainingData(Parcel in) {
        descTraining = in.readString();
        difficult = in.readString();
        expTrain = in.readString();
    }

    public static final Creator<TrainingData> CREATOR = new Creator<TrainingData>() {
        @Override
        public TrainingData createFromParcel(Parcel in) {
            return new TrainingData(in);
        }

        @Override
        public TrainingData[] newArray(int size) {
            return new TrainingData[size];
        }
    };

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }

    public String getDescTraining() {
        return descTraining;
    }

    public void setDescTraining(String descTraining) {
        this.descTraining = descTraining;
    }

    public String getDifficult() {
        return difficult;
    }

    public void setDifficult(String difficult) {
        this.difficult = difficult;
    }

    public String getExpTrain() {
        return expTrain;
    }

    public void setExpTrain(String expTrain) {
        this.expTrain = expTrain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(descTraining);
        dest.writeString(difficult);
        dest.writeString(expTrain);
    }
}
