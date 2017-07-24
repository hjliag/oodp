package com.example.student.addressbook;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TabHost;
import android.widget.Toast;

import com.example.student.addressbook.api.AddressManager;
import com.example.student.addressbook.api.SMSManager;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabHost host = (TabHost)findViewById(R.id.main_tab_host);
        host.setup();

        // tab 1: Dial
        TabHost.TabSpec spec = host.newTabSpec("Sms");
        spec.setContent(R.id.sms_tab);
        spec.setIndicator("Sms");
        host.addTab(spec);

        // tab 2: Calls
        spec = host.newTabSpec("Calls");
        spec.setContent(R.id.calls_tab);
        spec.setIndicator("Calls");
        host.addTab(spec);

        // tab 3: Contacts
        spec = host.newTabSpec("Contacts");
        spec.setContent(R.id.contacts_tab);
        spec.setIndicator("Contacts");
        host.addTab(spec);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ContactsFragment contactsFragment = new ContactsFragment();
        fragmentTransaction.add(R.id.contact_list_layout, contactsFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        SMSListFragment smsListFragment = new SMSListFragment();
        fragmentTransaction.add(R.id.sms_list_layout, smsListFragment);
        fragmentTransaction.commit();

        fragmentTransaction = fragmentManager.beginTransaction();
        CallFragment callFragment = new CallFragment();
        fragmentTransaction.add(R.id.call_list_layout, callFragment);
        fragmentTransaction.commit();
    }

    public void onClick(View view){
        if(view == findViewById(R.id.button_add)){
            Intent intent = new Intent(this, AddContactActivity.class);
            startActivity(intent);
        }
        else if(view == findViewById(R.id.button_view)){
            AddressManager addressManager = new AddressManager(openOrCreateDatabase("ContactDB", MODE_PRIVATE, null));
            ArrayList IDList = addressManager.GetIDByNumber("");
            Cursor c = addressManager.GetNameCursorByIDList(IDList);

            // If nothing found,
            if(c.getCount()==0) {
                Toast.makeText(this, "Nothing found..", Toast.LENGTH_SHORT).show();
                return;
            }
            // Appending records to a string buffer
            StringBuffer buffer = new StringBuffer();
            while(c.moveToNext()) {
                buffer.append("ID: "+c.getString(0)+"\n");
                buffer.append("name: "+c.getString(1)+"\n");
            }
            // Displaying all records
            Toast.makeText(this, buffer.toString(), Toast.LENGTH_SHORT).show();
        }
        else if(view == findViewById(R.id.button_send)){
            Intent intent = new Intent(this, SendSMSActivity.class);
            startActivity(intent);
        }
        else if(view == findViewById(R.id.button_call)){
            Intent intent = new Intent(this, DialActivity.class);
            startActivity(intent);
        }
        else if(view == findViewById(R.id.button_search_contact)){
            EditText editSearchContact = (EditText) findViewById(R.id.edit_search_contact);
            ContactsFragment contactsFragment = (ContactsFragment) getFragmentManager().findFragmentById(R.id.contact_list_layout);
            contactsFragment.SearchContactList(editSearchContact.getText().toString());
        }
        else if(view == findViewById(R.id.button_search_sms)){
            EditText editSearchSMS = (EditText) findViewById(R.id.edit_search_sms);
            SMSListFragment smsListFragment = (SMSListFragment) getFragmentManager().findFragmentById(R.id.sms_list_layout);
            smsListFragment.SearchSMSList(editSearchSMS.getText().toString());
        }
        else if(view == findViewById(R.id.button_search_call)){
            EditText editSearchCall = (EditText) findViewById(R.id.edit_search_call);
            CallFragment callFragment= (CallFragment) getFragmentManager().findFragmentById(R.id.call_list_layout);
            callFragment.SearchContactList(editSearchCall.getText().toString());
        }
    }
}