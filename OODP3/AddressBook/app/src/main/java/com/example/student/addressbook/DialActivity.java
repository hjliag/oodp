package com.example.student.addressbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

public class DialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);


        editNumber = (EditText)findViewById(R.id.edit_number);
        buttonCall = (Button)findViewById(R.id.button_call);
    }

    public void onClick(View view){
        if(view == buttonCall){
            Date date = new Date();
            Intent intent = new Intent(this, CallActivity.class);

            intent.putExtra("START_TIME", date);
            intent.putExtra("NUMBER", editNumber.getText().toString());

            startActivity(intent);
        }
    }
    public void onPause(){
        super.onPause();
        finish();
    }

    EditText editNumber;
    Button buttonCall;
}
