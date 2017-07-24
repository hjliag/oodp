package com.example.student.addressbook.api;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.student.addressbook.api.Call;

import java.io.*;
import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;

/**
 * Created by student on 2017. 4. 13..
 */
public class CallManager {
    public CallManager(SQLiteDatabase db){
        this.db = db;

        db.execSQL("CREATE TABLE IF NOT EXISTS CallList(" +
                "ID INTEGER NOT NULL PRIMARY KEY, " +
                "NUMBER VARCHAR(255), " +
                "CALLED BOOLEAN, " +
                "MISSED BOOLEAN, " +
                "STARTTIME VARCHAR(50), " +
                "ENDTIME VARCHAR(50) );");
    }

    // member functions
    public ArrayList<Integer> GetIDByNumber(String str){
        ArrayList<Integer> IDList = new ArrayList<Integer>();

        // Retrieving all records
        Cursor c = db.rawQuery("SELECT * FROM CallList " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            IDList.add(Integer.parseInt(c.getString(0)));
        }

        return IDList;
    }

    public Cursor GetCursorByNumber(String str){
        return db.rawQuery("SELECT rowid _id, * FROM CallList " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);
    }
    public Cursor GetCursorByIDList(ArrayList<Integer> IDList){
        String IDs = new String();
        for(int i = 0; i < IDList.size(); i++){
            IDs += " ID=" + IDList.get(i).toString() + " OR ";
        }
        Cursor c = db.rawQuery("SELECT rowid _id, * FROM CallList " +
                "WHERE " + IDs + " ID=0;", null);

        return c;
    }

    public void addCall(Call call){
        String startTimeString = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(call.getStartTime());
        String endTimeString = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss").format(call.getEndTime());

        db.execSQL("INSERT INTO CallList (NUMBER, CALLED, MISSED, STARTTIME, ENDTIME) " +
                "VALUES('" + call.getNumber() + "', " + BooleanToInt(call.getCalled()) + ", " +
                BooleanToInt(call.getMissed()) + ", '" + startTimeString + "', '" + endTimeString + "' );");
    }
    public void addCall(String number, Boolean called, Boolean missed, Date startTime, Date endTime){
        addCall(new Call(number, called, missed, startTime, endTime));
    }
    public void deleteCall(int ID){
        db.execSQL("DELETE FROM CallList WHERE ID=" + ID + ";");
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

    // member variable
    private SQLiteDatabase db;
}
