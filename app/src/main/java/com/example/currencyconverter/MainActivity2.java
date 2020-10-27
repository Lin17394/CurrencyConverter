package com.example.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;

import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;


public class MainActivity2 extends AppCompatActivity {

    //private  int lastScore_a,lastScore_b,score_a,score_b;
    //private Button getButton4;
    //EditText edt,edt2,edt3;
    float dollar;
    float pound;
    float euro;
    //private  static final  String TAG = "MainActivity2";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        dollar = intent.getFloatExtra("dollar_rate", 0.0f);
        pound = intent.getFloatExtra("pound_rate", 0.0f);
        euro = intent.getFloatExtra("euro_rate", 0.0f);
        EditText ett1 = findViewById(R.id.edt);
        EditText ett2 = findViewById(R.id.edt2);
        EditText ett3 = findViewById(R.id.edt3);
        ett1.setText("" + dollar);
        ett2.setText("" + pound);
        ett3.setText("" + euro);
    }
    public void save(View v){
        EditText ett1 = findViewById(R.id.edt);
        dollar = Float.parseFloat(ett1.getText().toString());
        EditText ett2 = findViewById(R.id.edt2);
        pound = Float.parseFloat(ett2.getText().toString());
        EditText ett3 = findViewById(R.id.edt3);
        euro = Float.parseFloat(ett3.getText().toString());
        Intent config = getIntent();
        Bundle rate = new Bundle();
        rate.putFloat("dollar_rate", dollar);
        rate.putFloat("pound_rate", pound);
        rate.putFloat("euro_rate", euro);
        config.putExtras(rate);
        setResult(2,config);

        SharedPreferences sp = getSharedPreferences("rate", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("dollar_rate", dollar);
        editor.putFloat("pound_rate", pound);
        editor.putFloat("euro_rate", euro);
        editor.apply();
        finish();
    }
}