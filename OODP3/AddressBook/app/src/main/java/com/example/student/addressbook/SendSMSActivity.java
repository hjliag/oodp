package com.example.student.addressbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student.addressbook.api.SMSManager;

import java.util.Date;

public class SendSMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_sms);

        // setting member variables
        db = openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        smsManager = new SMSManager(db);

        editNumber = (EditText)findViewById(R.id.edit_number);
        editMessage = (EditText)findViewById(R.id.edit_message);
        buttonSend = (Button)findViewById(R.id.button_send);

    }

    public void onClick(View view){
        if(view == buttonSend){
            smsManager.addSMS(editNumber.getText().toString(), true, editMessage.getText().toString(), new Date());

            Toast.makeText(this, "add successful!", Toast.LENGTH_SHORT).show();

            finish();
        }
    }

    private EditText editNumber;
    private EditText editMessage;

    private Button buttonSend;

    private SMSManager smsManager;
    SQLiteDatabase db;
}
