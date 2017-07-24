package com.example.student.addressbook.api;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by student on 2017. 4. 12..
 */
public class SMSManager {
    public SMSManager(SQLiteDatabase db){
        this.db = db;

        db.execSQL("CREATE TABLE IF NOT EXISTS SMSList(" +
                "ID INTEGER NOT NULL PRIMARY KEY, " +
                "NUMBER VARCHAR(255), " +
                "SENT BOOLEAN, " +
                "MESSAGE VARCHAR(255), " +
                "DATE VARCHAR(50) );");
    }

    // Member functions
    public ArrayList<Integer> GetIDByNumber(String str){
        ArrayList<Integer> IDList = new ArrayList<Integer>();

        // Retrieving all records
        Cursor c = db.rawQuery("SELECT * FROM SMSList " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            IDList.add(Integer.parseInt(c.getString(0)));
        }

        return IDList;
    }
    public ArrayList<Integer> GetIDByMessage(String str){
        ArrayList<Integer> IDList = new ArrayList<Integer>();

        Cursor c = db.rawQuery("SELECT * FROM SMSList " +
                "WHERE MESSAGE LIKE '%" + str + "%';", null);

        while(c.moveToNext()) {
            IDList.add(Integer.parseInt(c.getString(0)));
        }

        return IDList;
    }

    public Cursor GetCursorByNumber(String str){
        return db.rawQuery("SELECT rowid _id, * FROM SMSList " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);
    }
    public Cursor GetCursorByMessage(String str){
        return db.rawQuery("SELECT rowid _id, * FROM SMSList " +
                "WHERE MESSAGE LIKE '%" + str + "%';", null);
    }
    public Cursor GetCursorByIDList(ArrayList<Integer> IDList){
        String IDs = new String();
        for(int i = 0; i < IDList.size(); i++){
            IDs += " ID=" + IDList.get(i).toString() + " OR ";
        }
        Cursor c = db.rawQuery("SELECT rowid _id, * FROM SMSList " +
                "WHERE " + IDs + " ID=0;", null);

        return c;
    }

    public void addSMS(SMS sms) {
        String date = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(sms.getDate());

        db.execSQL("INSERT INTO SMSList (NUMBER, SENT, MESSAGE, DATE) " +
                "VALUES('" + sms.getNumber() + "', " + BooleanToInt(sms.getSent()) + ", '" +sms.getMessage() + "', " +
                "'" + date + "' );");
    }
    public void addSMS(String number, Boolean sent, String message, Date date){
        addSMS(new SMS(number, sent, message, date));
    }
    public void deleteSMS(int ID){
        db.execSQL("DELETE FROM SMSList WHERE ID=" + ID + ";");
    }

    public int BooleanToInt(Boolean bool){
        if(bool.equals(true)){
            return 1;
        }
        return 0;
    }
    public boolean IntToBoolean(int i){
        if(i == 1){
            return true;
        }
        return false;
    }

    // Member variable
    private SQLiteDatabase db;
}
