package com.example.myapplication_tips;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.time.LocalTime;

public class Worker implements Parcelable {
    private String workerName;
    private String timeShiftWorker;
    private double ExchangeTimeShiftWorker;
    private String workerType;
    private int totalMoneyWorker;
    private String StartTimeWorker;
    private String finishTimeWorker;

    public Worker() {
    }

    public Worker(String workerName, String timeShiftWorker, double exchangeTimeShiftWorker,
                  String workerType, int totalMoneyWorker, String startTimeWorker, String finishTimeWorker) {
        this.workerName = workerName;
        this.timeShiftWorker = timeShiftWorker;
        ExchangeTimeShiftWorker = exchangeTimeShiftWorker;
        this.workerType = workerType;
        this.totalMoneyWorker = totalMoneyWorker;
        StartTimeWorker = startTimeWorker;
        this.finishTimeWorker = finishTimeWorker;
    }

    protected Worker(Parcel in) {
        workerName = in.readString();
        timeShiftWorker = in.readString();
        ExchangeTimeShiftWorker = in.readDouble();
        workerType = in.readString();
        totalMoneyWorker = in.readInt();
        StartTimeWorker = in.readString();
        finishTimeWorker = in.readString();
    }

    public static final Creator<Worker> CREATOR = new Creator<Worker>() {
        @Override
        public Worker createFromParcel(Parcel in) {
            return new Worker(in);
        }

        @Override
        public Worker[] newArray(int size) {
            return new Worker[size];
        }
    };

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getTimeShiftWorker() {
        return timeShiftWorker;
    }

    public void setTimeShiftWorker(String timeShiftWorker) {
        this.timeShiftWorker = timeShiftWorker;
    }

    public double getExchangeTimeShiftWorker() {
        return ExchangeTimeShiftWorker;
    }

    public void setExchangeTimeShiftWorker(double exchangeTimeShiftWorker) {
        ExchangeTimeShiftWorker = exchangeTimeShiftWorker;
    }

    public String getWorkerType() {
        return workerType;
    }

    public void setWorkerType(String workerType) {
        this.workerType = workerType;
    }

    public int getTotalMoneyWorker() {
        return totalMoneyWorker;
    }

    public void setTotalMoneyWorker(int totalMoneyWorker) {
        this.totalMoneyWorker = totalMoneyWorker;
    }

    public String getStartTimeWorker() {
        return StartTimeWorker;
    }

    public void setStartTimeWorker(String startTimeWorker) {
        StartTimeWorker = startTimeWorker;
    }

    public String getFinishTimeWorker() {
        return finishTimeWorker;
    }

    public void setFinishTimeWorker(String finishTimeWorker) {
        this.finishTimeWorker = finishTimeWorker;
    }

    @Override
    public String toString() {
        return
                workerName +
                ", " + timeShiftWorker +
                ", " + ExchangeTimeShiftWorker +
                ", " + workerType + ", " + StartTimeWorker + ", " + finishTimeWorker + ", " + totalMoneyWorker;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(workerName);
        parcel.writeString(timeShiftWorker);
        parcel.writeDouble(ExchangeTimeShiftWorker);
        parcel.writeString(workerType);
        parcel.writeInt(totalMoneyWorker);
        parcel.writeString(StartTimeWorker);
        parcel.writeString(finishTimeWorker);
    }
}
