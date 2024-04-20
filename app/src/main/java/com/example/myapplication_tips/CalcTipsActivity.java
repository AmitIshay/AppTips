package com.example.myapplication_tips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.lifecycle.ViewModel;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class CalcTipsActivity extends AppCompatActivity {

    private TextInputEditText workerInputTime;
    private TextInputEditText workerInputName;
    private AppCompatSpinner spinnerTeam;
    private MaterialButton nextButton;
    private MaterialTextView totalHoursTextView;
    private MaterialButton addWorkerButton;
    private ListView listViewWorkers;
    private TextInputEditText workerInputStartTime;
    private TextInputEditText workerInputFinishTime;
    AppManager appManager;
    int checkNum;
    int timeNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc_tips);
        findViews();
        Intent i = getIntent();
        checkNum = Integer.parseInt(i.getStringExtra("message"));
        //Log.d("bbbbbbbbbbbbb",str);
        if (checkNum == 0){
            workerInputStartTime.setVisibility(View.INVISIBLE);
            workerInputFinishTime.setVisibility(View.INVISIBLE);
        }
        appManager = new AppManager(this);
        workerInputTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {timeNum = 2; appManager.pickTime(view, timeNum);}
        });
        if (checkNum == 0) {
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)findViewById(R.id.spinnerTeam);
            ArrayAdapter arrayAdapter = new ArrayAdapter(CalcTipsActivity.this, android.R.layout.simple_spinner_item,appManager.getTeamListTips());
            spinnerTeam.setAdapter(arrayAdapter);
        }
        else {
            AppCompatSpinner spinnerTeam = (AppCompatSpinner)findViewById(R.id.spinnerTeam);
            ArrayAdapter arrayAdapter = new ArrayAdapter(CalcTipsActivity.this, android.R.layout.simple_spinner_item,appManager.getTeamListReport());
            spinnerTeam.setAdapter(arrayAdapter);
        }
        workerInputStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {timeNum = 3; appManager.pickTime(view, timeNum);}
        });
        workerInputFinishTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {timeNum = 4; appManager.pickTime(view, timeNum);}
        });
        addWorkerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                appManager.addNewWorker(view, checkNum);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String totalHours = totalHoursTextView.getText().toString();
                Intent i = new Intent(getApplicationContext(),CalcTipsSecondActivity.class);
                i.putExtra("message", totalHours);
                ArrayList<Worker> currentList = appManager.getWorkersArray();
                i.putExtra("Array",currentList);
                if (checkNum == 0)
                    i.putExtra("checknum","0");
                else
                    i.putExtra("checknum", "1");
                startActivity(i);
            }
        });
        listViewWorkers.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int listItem, long l) {
                new AlertDialog.Builder(CalcTipsActivity.this).setTitle("Do you want to remove " + appManager.getAdapter().getItem(listItem) + " from list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Worker w = appManager.getAdapter().getItem(listItem);
                                appManager.removeWorker(w,listItem, view);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                return false;
            }
        });
    }

    private void findViews() {
        workerInputTime = findViewById(R.id.workerInputTime);
        workerInputName = findViewById(R.id.workerInputName);
        spinnerTeam = findViewById(R.id.spinnerTeam);
        nextButton = findViewById(R.id.nextButton);
        totalHoursTextView = findViewById(R.id.totalHoursTextView);
        addWorkerButton = findViewById(R.id.addWorkerButton);
        listViewWorkers = findViewById(R.id.listViewWorkers);
        workerInputStartTime = findViewById(R.id.workerInputStartTime);
        workerInputFinishTime = findViewById(R.id.workerInputFinishTime);
    }

    public TextInputEditText getWorkerInputName() {
        return workerInputName;
    }

    public MaterialTextView getTotalHoursTextView() {
        return totalHoursTextView;
    }

    public TextInputEditText getWorkerInputTime() {
        return workerInputTime;
    }

    public AppCompatSpinner getSpinnerTeam() {
        return spinnerTeam;
    }

    public ListView getListViewWorkers() {
        return listViewWorkers;
    }

    public TextInputEditText getWorkerInputStartTime() {
        return workerInputStartTime;
    }

    public TextInputEditText getWorkerInputFinishTime() {
        return workerInputFinishTime;
    }
}