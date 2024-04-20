package com.example.myapplication_tips;

import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class AppManager {
    private CalcTipsActivity calcTipsActivity;
    private CalcTipsSecondActivity calcTipsSecondActivity;
    private PdfTableActivity pdfTableActivity;
    AppData appData;
    private static MyCustomAdapter adapter; //========


    public AppManager(CalcTipsActivity calcTipsActivity) {
        this.calcTipsActivity = calcTipsActivity;
        appData = new AppData();
    }
    public AppManager(CalcTipsSecondActivity calcTipsSecondActivity) {
        this.calcTipsSecondActivity = calcTipsSecondActivity;
        appData = new AppData();
    }
    public AppManager(PdfTableActivity pdfTableActivity) {
        this.pdfTableActivity = pdfTableActivity;
        appData = new AppData();
    }
    private static String[] teamListTips = {
            "בחר סוג עובד","ברמן","מלצר"
    };
    private static String[] teamListReport = {
            "בחר סוג עובד","ברמן","מלצר","אחמש","מארחת","שוטף","טבח","מנהל"
    };


    public void pickTime(View view, int timeNum){
        TimePickerDialog picker;
        final Calendar cldr = Calendar.getInstance();
        int hour = cldr.get(Calendar.HOUR_OF_DAY);
        int minutes = cldr.get(Calendar.MINUTE);
        // time picker dialog
        picker = new TimePickerDialog(view.getContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                        if (timeNum == 2)
                            calcTipsActivity.getWorkerInputTime().setText(sHour + ":" + sMinute);
                        else if (timeNum == 3) {
                            calcTipsActivity.getWorkerInputStartTime().setText(sHour + ":" + sMinute);
                        }
                        else if (timeNum == 4) {
                            calcTipsActivity.getWorkerInputFinishTime().setText(sHour + ":" + sMinute);
                        }
                    }
                }, hour, minutes, true);
        picker.show();
    }
    public void addNewWorker(View view, int checkNum) {
        String getInput=calcTipsActivity.getWorkerInputName().getText().toString();
        String getInputOne=calcTipsActivity.getWorkerInputTime().getText().toString();

        if(getInput == null || getInput.trim().equals("") || getInputOne == null || getInputOne.trim().equals("") ||
                calcTipsActivity.getSpinnerTeam().getSelectedItem().toString().equals("בחר סוג עובד")) {   // if the field is empty
            Toast.makeText(view.getContext(), "Check if you enter Name/Job", Toast.LENGTH_LONG).show();
        }
        else {

            calcTipsActivity.getWorkerInputName().setText(" ");
            calcTipsActivity.getWorkerInputTime().setText(" ");
            if(ExChange(getInputOne) == 0) {
                Toast.makeText(view.getContext(), "The time shift isnt corect", Toast.LENGTH_LONG).show();
                appData.removeTheLast();
            }
            else {
                if (checkNum == 1) {
                    String getInputTwo = calcTipsActivity.getWorkerInputStartTime().getText().toString();
                    String getInputThree = calcTipsActivity.getWorkerInputFinishTime().getText().toString();
                    appData.addToList(getInput,getInputOne,calcTipsActivity.getSpinnerTeam().getSelectedItem().toString(),getInputTwo,getInputThree);
                }
                else {
                    appData.addToList(getInput,getInputOne,calcTipsActivity.getSpinnerTeam().getSelectedItem().toString(),"X","X");
                }
                appData.sumHours(ExChange(getInputOne));
                calcTipsActivity.getTotalHoursTextView().setText(appData.getSum()+"");
                calcTipsActivity.getSpinnerTeam().setSelection(0);
                adapter = new MyCustomAdapter(appData.getWorkers(), view.getContext()); //==========
                calcTipsActivity.getListViewWorkers().setAdapter(adapter);
            }
            // calcTipsActivity.getTotalHoursTextView().setText( "סהכ שעות במשמרת: " );                                  // updating the total hours of all the worker
            calcTipsActivity.getWorkerInputName().setText(null);
            calcTipsActivity.getWorkerInputTime().setText(null);
            if (checkNum == 1) {
                calcTipsActivity.getWorkerInputStartTime().setText(null);
                calcTipsActivity.getWorkerInputFinishTime().setText(null);
            }
        }
    }
    public static Double ExChange(String time) {                   // the formula to calc the time shift
        String Hour=time.split(":")[0];
        String Minute=time.split(":")[1];
        int hour=Integer.parseInt(Hour.trim());
        double minute=Integer.parseInt(Minute.trim());
        if(minute < 2.5) {
            minute = 0.0;
        }
        else if(minute <= 7.5 && minute >= 2.5) {
            minute = 0.08;
        }
        else if(minute <= 12.5 && minute > 7.5) {
            minute = 0.17;
        }
        else if(minute <= 17.5 && minute > 12.5) {
            minute = 0.25;
        }
        else if(minute <= 22.5 && minute > 17.5) {
            minute = 0.33;
        }
        else if(minute <= 27.5 && minute > 22.5) {
            minute = 0.41;
        }
        else if(minute <= 32.5 && minute > 27.5) {
            minute = 0.50;
        }
        else if(minute <= 37.5 && minute > 32.5) {
            minute = 0.58;
        }
        else if(minute <= 42.5 && minute > 37.5) {
            minute = 0.67;
        }
        else if(minute <= 47.5 && minute > 42.5) {
            minute = 0.75;
        }
        else if(minute <= 52.5 && minute > 47.5) {
            minute = 0.83;
        }
        else if(minute <= 59 && minute >= 52.5) {
            minute = 0.91;
        }
        else if(minute > 59) {
            hour = 0;
            minute = 0;
        }
        return Double.valueOf(new DecimalFormat("##.##").format(hour + minute));
    }
    public void calcParms(double totalHours, int moneyTips, int moneyCreditt, int checkSum, ArrayList<Worker> workers, View view){
        appData.newShift(totalHours, moneyTips, moneyCreditt, checkSum, workers, view);
        int moneyCredit = appData.getMoneyCredit();
        int moneyCash = appData.getMoneyCash();
        int moneySixINS = appData.getMoneySixINS();
        int moneyMinusSixIns = appData.getMoneyMinusSixIns();
        int moneyPerHour = appData.getMoneyPerHour();
        Double a;
        for(Worker d : workers) {
            a = d.getExchangeTimeShiftWorker();
            if(d.getWorkerType().equals("מלצר") || d.getWorkerType().equals("ברמן")) {
                d.setTotalMoneyWorker((int) (a * moneyPerHour));
            }
        }
        if (checkSum == 0) {
            calcTipsSecondActivity.getFinalText().setText("סהכ טיפ: " + moneyTips + " סהכ שעות:" + totalHours + " מזומן:" + moneyCash
                    + " הפרשה:" + moneySixINS + " טיפ ללא הפרשה:" + moneyMinusSixIns + " טיפ לשעה:" + moneyPerHour);
        }
        else {
            calcTipsSecondActivity.getFinalText().setText("סהכ טיפ: " + moneyTips + " סהכ שעות:" + totalHours + " מזומן:" + moneyCash
                    + " אשראי:" + moneyCredit + " הפרשה:" + moneySixINS + " טיפ ללא הפרשה:" + moneyMinusSixIns + " טיפ לשעה:" + moneyPerHour);
        }
        adapter = new MyCustomAdapter(workers, view.getContext()); //==========
        calcTipsSecondActivity.getFinalListViewWorkers().setAdapter(adapter);
    }
    public void removeWorker(Worker worker, int i, View view) {
        double d = worker.getExchangeTimeShiftWorker();
        appData.reduceSum(d);
        appData.removeFromList(i);
        adapter = new MyCustomAdapter(appData.getWorkers(), view.getContext()); //==========
        calcTipsActivity.getListViewWorkers().setAdapter(adapter);
        calcTipsActivity.getTotalHoursTextView().setText(""+appData.getSum());
    }
    public String[] getTeamListTips() {
        return teamListTips;
    }

    public String[] getTeamListReport() {
        return teamListReport;
    }
    public ArrayList<Worker> getWorkersArray() {
        return appData.getWorkers();
    }
    public int getMoneyCredit() {return appData.getMoneyCredit();}
    public int getMoneyCash() {return appData.getMoneyCash();}
    public int getMoneySixINS() {return appData.getMoneySixINS();}
    public int getMoneyMinusSixINS() {return appData.getMoneyMinusSixIns();}
    public int getMoneyPerHour() {return appData.getMoneyPerHour();}
    public double getTotalHours() {return appData.getTotalHours();}
    public ArrayList<Worker> getWorkerList() {return appData.getWorkers();}
    public int getMoneyTips() {return appData.getMoneyTips();}

    public MyCustomAdapter getAdapter() {
        return adapter;
    }
}
