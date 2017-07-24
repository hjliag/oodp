package com.example.student.addressbook;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.student.addressbook.api.AddressManager;

import static android.R.attr.button;

public class AddContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        // initialize controllers
        editName = (EditText)findViewById(R.id.edit_name);
        editNumber = (EditText)findViewById(R.id.edit_number);
        editEmail = (EditText)findViewById(R.id.edit_email);
        editAddress = (EditText)findViewById(R.id.edit_address);
        editMemo = (EditText)findViewById(R.id.edit_memo);

        buttonAdd = (Button)findViewById(R.id.button_add);

        SQLiteDatabase db = openOrCreateDatabase("ContactDB", Context.MODE_PRIVATE, null);
        addressManager = new AddressManager(db);
    }

    public void onClick(View view){
        addressManager.addAddress(editName.getText().toString(), editNumber.getText().toString(),
                editEmail.getText().toString(), editAddress.getText().toString(),
                editMemo.getText().toString());

        Toast.makeText(this, "add successful!", Toast.LENGTH_SHORT).show();

        finish();
    }

    private EditText editName;
    private EditText editNumber;
    private EditText editEmail;
    private EditText editAddress;
    private EditText editMemo;
    private Button buttonAdd;
    private AddressManager addressManager;
}
