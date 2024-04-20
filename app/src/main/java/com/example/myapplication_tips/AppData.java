package com.example.myapplication_tips;

import android.view.View;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.ArrayList;

public class AppData {

    private double sum;
    private ArrayList<Worker> workers;
    private Shift shift;

    public AppData() {
        this.sum = 0.0;
        this.workers = new ArrayList<>();
        this.shift = new Shift();
    }
    public void newShift(double totalHours, int moneyTips, int moneyCreditt, int checkSum, ArrayList<Worker> workers, View view){
        shift.setMoneyCredit(moneyCreditt);
        int moneyCredit = moneyCreditt;
        shift.setMoneyCash(moneyTips - moneyCredit);
        shift.setMoneySixINS((int)(totalHours*6));
        int moneySixINS = (int)(totalHours*6);
        shift.setMoneyMinusSixIns(moneyTips - moneySixINS);
        int moneyMinusSixIns = moneyTips - moneySixINS;
        shift.setMoneyPerHour((int) (moneyMinusSixIns/totalHours));
        shift.setTotalHours(totalHours);
        shift.setMoneyTips(moneyTips);
    }
    public int getMoneyCredit() {return shift.getMoneyCredit();}
    public int getMoneySixINS() {
        return shift.getMoneySixINS();
    }
    public int getMoneyCash() {
        return shift.getMoneyCash();
    }
    public int getMoneyMinusSixIns() {
        return shift.getMoneyMinusSixIns();
    }
    public int getMoneyPerHour() {
        return shift.getMoneyPerHour();
    }
    public double getTotalHours() {
        return shift.getTotalHours();
    }
    public int getMoneyTips() {
        return shift.getMoneyTips();
    }


    public void removeTheLast(){
        workers.remove(workers.size()-1);
    }
    public void addToList(String name,String time,String type, String Stime, String Ftime){
        workers.add(new Worker(name,time,AppManager.ExChange(time),type,0,Stime,Ftime));
    }
    public void removeFromList(int num){
        workers.remove(num);
    }
    public void sumHours(Double num){
        int index = workers.size() - 1;
        if (workers.get(index).getWorkerType().equals("מלצר") || workers.get(index).getWorkerType().equals("ברמן")) {
            sum += num;
        }
    }
    public void reduceSum(Double d) {
        sum -= d;
    }
    public double
    getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ArrayList<Worker> getWorkers() {
        return workers;
    }
    public String getStringWorkers() { return workers.toString();}
    public String getShiftToString() { return shift.toString();}
    public String getTimeNow() { return shift.getTimeNow();}
    public void setWorkers(ArrayList<Worker> workers) {

        this.workers = workers;
    }

    public void setShift(Shift shift) {
        this.shift = shift;
    }
}
