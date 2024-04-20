package com.example.myapplication_tips;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class EnterActivity extends AppCompatActivity {

    private MaterialButton createReport;
    private MaterialButton calcTips;

    private MaterialButton PdfTable;

    private static String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);
        findViews();
        calcTips.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setType("calcTips");
                goToActivity(type);
            }
        });
        createReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setType("createReport");
                goToActivity(type);
            }
        });
        PdfTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setType("PdfTable");
                goToActivity(type);
            }
        });
    }

    private void goToActivity(String type) {
        if (type == "calcTips") {
            Intent i = new Intent(getApplicationContext(),CalcTipsActivity.class);
            i.putExtra("message","0");
            startActivity(i);
        } else if (type == "PdfTable") {
            Intent i = new Intent(getApplicationContext(),PdfTableActivity.class);
            startActivity(i);
        } else {
            Intent i = new Intent(getApplicationContext(),CalcTipsActivity.class);
            i.putExtra("message","1");
            startActivity(i);
        }
    }

    private void findViews() {
        createReport = findViewById(R.id.createReport);
        calcTips = findViewById(R.id.calcTips);
        PdfTable = findViewById(R.id.PdfTable);
    }
    public void setType(String type) {this.type = type;}
}