package com.example.student.addressbook;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.student.addressbook.api.CallManager;
import com.example.student.addressbook.api.SMSManager;

import java.util.Date;

public class CallActivity extends AppCompatActivity {

    public void onClick(View view){
        if(buttonEndCall == view){
            finish();
        }
    }

    // basic member functions
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        Intent intent = getIntent();
        number = intent.getExtras().getString("NUMBER");
        startTime = (Date)intent.getExtras().get("START_TIME");
        db = openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        buttonEndCall = (Button) findViewById(R.id.button_end_call);
    }
    @Override
    public void onStop(){
        super.onStop();

        endTime = new Date();
        callManager = new CallManager(db);
        callManager.addCall(number, true, true, startTime, endTime);
    }

    // custom member variables
    SQLiteDatabase db;
    CallManager callManager;
    String number;
    Date startTime;
    Date endTime;
    Button buttonEndCall;
}
