package com.example.myapplication_tips;

import android.util.Log;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.bumptech.glide.annotation.GlideModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Shift extends BaseObservable {
    private int moneyCredit;
    private int moneyCash;
    private int moneySixINS;
    private int moneyMinusSixIns;
    private int moneyPerHour;
    private double totalHours;
    private int moneyTips;
    private String timeNow;
    private String imageUrl = "";
    private String userId;
    private String userName;
    ArrayList<Worker> workerArray;
    public Shift() {
        timeNow = timeNow();
        //Log.d("bbbbbbbbbbbbbmmmmmm",timeNow);
    }

    public Shift(int moneyCredit, int moneyCash, int moneySixINS, int moneyMinusSixIns, int moneyPerHour, double totalHours, int moneyTips) {
        this.moneyCredit = moneyCredit;
        this.moneyCash = moneyCash;
        this.moneySixINS = moneySixINS;
        this.moneyMinusSixIns = moneyMinusSixIns;
        this.moneyPerHour = moneyPerHour;
        this.totalHours = totalHours;
        this.moneyTips = moneyTips;
    }
    private String timeNow() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy:MM:dd:HH:mm");
        LocalDateTime now = LocalDateTime.now();
        String date_suffix=""+dtf.format(now);
        return date_suffix;
    }

    //@Bindable
    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
        //notifyPropertyChanged(BR.timeNow);
    }

    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }
    //@Bindable
    public int getMoneyTips() {
        return moneyTips;
    }

    public void setMoneyTips(int moneyTips) {
        this.moneyTips = moneyTips;
        //notifyPropertyChanged(BR.moneyTips);
    }
    //@Bindable
    public int getMoneyCredit() {
        return moneyCredit;
    }

    public void setMoneyCredit(int moneyCredit) {
        this.moneyCredit = moneyCredit;
        //notifyPropertyChanged(BR.moneyCredit);

    }

    public int getMoneyCash() {
        return moneyCash;
    }

    public void setMoneyCash(int moneyCash) {
        this.moneyCash = moneyCash;
    }

    public int getMoneySixINS() {
        return moneySixINS;
    }

    public void setMoneySixINS(int moneySixINS) {
        this.moneySixINS = moneySixINS;
    }

    public int getMoneyMinusSixIns() {
        return moneyMinusSixIns;
    }

    public void setMoneyMinusSixIns(int moneyMinusSixIns) {
        this.moneyMinusSixIns = moneyMinusSixIns;
    }

    public int getMoneyPerHour() {
        return moneyPerHour;
    }

    public void setMoneyPerHour(int moneyPerHour) {
        this.moneyPerHour = moneyPerHour;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public ArrayList<Worker> getWorkerArray() {
        return workerArray;
    }

    public void setWorkerArray(ArrayList<Worker> workerArray) {
        this.workerArray = workerArray;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "moneyCredit=" + moneyCredit +
                ", moneyCash=" + moneyCash +
                ", moneySixINS=" + moneySixINS +
                ", moneyMinusSixIns=" + moneyMinusSixIns +
                ", moneyPerHour=" + moneyPerHour +
                ", totalHours=" + totalHours +
                ", moneyTips=" + moneyTips +
                ", timeNow='" + timeNow + '\'' +
                '}';
    }
}
