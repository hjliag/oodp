package com.example.student.addressbook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.addressbook.api.AddressManager;
import com.example.student.addressbook.api.CallManager;

import java.util.ArrayList;
import java.util.List;

public class ContactViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        buttonDelete = (Button)findViewById(R.id.button_delete);
        db = openOrCreateDatabase(new String("ContactDB"), Context.MODE_PRIVATE, null);
        addressManager = new AddressManager(db);
        callManager = new CallManager(db);


        // Get Contact ID
        ID = getIntent().getIntExtra("VIEW_ID", 0);

        // add Name TextView
        Cursor c = addressManager.GetContactListCursorByID(ID);
        LinearLayout linearLayout = (LinearLayout)findViewById(R.id.name_layout);
        while(c.moveToNext()){
            TextView textView = new TextView(this);
            textView.setText(c.getString(2));
            textView.setId((int)System.currentTimeMillis());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }

        // add Numbers TextView
        c = addressManager.GetNumbersCursorByID(ID);
        linearLayout = (LinearLayout)findViewById(R.id.number_layout);
        while(c.moveToNext()){
            TextView textView = new TextView(this);
            textView.setText(c.getString(3));
            textView.setId((int)System.currentTimeMillis());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }

        // add Emails TextView
        c = addressManager.GetEmailsCursorByID(ID);
        linearLayout = (LinearLayout)findViewById(R.id.email_layout);
        while(c.moveToNext()){
            TextView textView = new TextView(this);
            textView.setText(c.getString(2));
            textView.setId((int)System.currentTimeMillis());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }

        // add Addresses TextView
        c = addressManager.GetAddressesCursorByID(ID);
        linearLayout = (LinearLayout)findViewById(R.id.address_layout);
        while(c.moveToNext()){
            TextView textView = new TextView(this);
            textView.setText(c.getString(2));
            textView.setId((int)System.currentTimeMillis());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }

        // add Emails TextView
        c = addressManager.GetContactListCursorByID(ID);
        linearLayout = (LinearLayout)findViewById(R.id.memo_layout);
        while(c.moveToNext()){
            TextView textView = new TextView(this);
            textView.setText(c.getString(3));
            textView.setId((int)System.currentTimeMillis());
            textView.setLayoutParams(new LinearLayoutCompat.LayoutParams(
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                    LinearLayoutCompat.LayoutParams.WRAP_CONTENT));
            linearLayout.addView(textView);
        }
    }

    public void onClick(View view){
        if(view == buttonDelete){
            addressManager.deleteAddress(ID);
            finish();
        }
    }

    int ID;
    private Button buttonDelete;

    AddressManager addressManager;
    CallManager callManager;
    ListView listViewCall;
    ListView listViewSMS;
    SQLiteDatabase db;
}
