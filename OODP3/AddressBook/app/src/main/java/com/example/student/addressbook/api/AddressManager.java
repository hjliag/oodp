package com.example.student.addressbook.api;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;
import android.widget.Toast;

import java.io.*;
import java.util.*;

import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

/**
 * Created by student on 2017. 4. 8..
 */
public class AddressManager {
    public AddressManager(SQLiteDatabase db){
        this.db = db;

        db.execSQL("CREATE TABLE IF NOT EXISTS ContactList(" +
                "ID INTEGER NOT NULL PRIMARY KEY, " +
                "NAME VARCHAR(255), " +
                "MEMO VARCHAR(255) );");

        db.execSQL("CREATE TABLE IF NOT EXISTS Numbers(" +
                "ID INTEGER, " +
                "NUMBERTYPE VARCHAR(100), " +
                "NUMBER VARCHAR(100) );");

        db.execSQL("CREATE TABLE IF NOT EXISTS Emails(" +
                "ID INTEGER, " +
                "EMAIL VARCHAR(100) );");

        db.execSQL("CREATE TABLE IF NOT EXISTS Addresses(" +
                "ID INTEGER, " +
                "ADDRESS VARCHAR(100) );");
    }

    // Member Functions
    public ArrayList<Integer> GetIDByName(String str){
        ArrayList<Integer> IDList = new ArrayList();

        // Retrieving all records
        Cursor c = db.rawQuery("SELECT * FROM ContactList " +
                "WHERE NAME LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            IDList.add(Integer.parseInt(c.getString(0)));
        }

        return IDList;
    }
    public ArrayList<Integer> GetIDByNumber(String str){
        ArrayList<Integer> IDList = new ArrayList();

        Cursor c = db.rawQuery("SELECT * FROM Numbers " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(0));
            // check duplication
            if(!IDList.contains(ID)){
                IDList.add(ID);
            }
        }

        return IDList;
    }
    public ArrayList<Integer> GetIDByEmail(String str){
        ArrayList<Integer> IDList = new ArrayList();

        Cursor c = db.rawQuery("SELECT * FROM Emails " +
                "WHERE EMAIL LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(0));
            // check duplication
            if(!IDList.contains(ID)){
                IDList.add(ID);
            }
        }

        return IDList;
    }
    public ArrayList<Integer> GetIDByAddress(String str){
        ArrayList<Integer> IDList = new ArrayList();

        Cursor c = db.rawQuery("SELECT * FROM Addresses " +
                "WHERE ADDRESS LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            int ID = Integer.parseInt(c.getString(0));
            // check duplication
            if(!IDList.contains(ID)){
                IDList.add(ID);
            }
        }

        return IDList;
    }
    public ArrayList<Integer> GetIDByMemo(String str){
        ArrayList<Integer> IDList = new ArrayList();

        Cursor c = db.rawQuery("SELECT * FROM ContactList " +
                "WHERE MEMO LIKE '%" + str + "%';", null);

        // add IDs to IDList
        while(c.moveToNext()) {
            IDList.add(Integer.parseInt(c.getString(0)));
        }

        return IDList;
    }

    public Cursor GetCursorByNumber(String str){
        return db.rawQuery("SELECT rowid _id, * FROM Numbers " +
                "WHERE NUMBER LIKE '%" + str + "%';", null);
    }
    public Cursor GetCursorByIDList(ArrayList<Integer> IDList){
        String IDs = new String();
        for(int i = 0; i < IDList.size(); i++){
            IDs += " ID=" + IDList.get(i).toString() + " OR ";
        }
        Cursor c = db.rawQuery("SELECT rowid _id, * FROM ContactList " +
                "WHERE" + IDs + " ID=0 " +
                "ORDER BY NAME ASC", null);
        return c;
    }
    public Cursor GetNameCursorByIDList(ArrayList<Integer> IDList){
        String IDs = new String();
        for(int i = 0; i < IDList.size(); i++){
            IDs += " ID=" + IDList.get(i).toString() + " OR ";
        }
        Cursor c = db.rawQuery("SELECT rowid _id, NAME FROM ContactList " +
                "WHERE " + IDs + " ID=0 " +
                "ORDER BY NAME ASC ", null);
        return c;
    }

    public Cursor GetContactListCursorByID(int ID){
        return db.rawQuery("SELECT rowid _id, * FROM ContactList " +
                "WHERE ID=" + ID + ";", null);
    }
    public Cursor GetNumbersCursorByID(int ID){
        return db.rawQuery("SELECT rowid _id, * FROM Numbers " +
                "WHERE ID=" + ID + ";", null);
    }
    public Cursor GetEmailsCursorByID(int ID){
        return db.rawQuery("SELECT rowid _id, * FROM Emails " +
                "WHERE ID=" + ID + ";", null);
    }
    public Cursor GetAddressesCursorByID(int ID){
        return db.rawQuery("SELECT rowid _id, * FROM Addresses " +
                "WHERE ID=" + ID + ";", null);
    }

    public void addAddress(Address address){
        db.execSQL("INSERT INTO ContactList (NAME, MEMO) " +
                "VALUES('" + address.getName() + "', '" + address.getMemo() + "');");
        Cursor c = db.rawQuery("SELECT MAX(ID) FROM ContactList;", null);
        c.moveToFirst();
        int ID = c.getInt(0);

        int size = address.getNumbers().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Numbers (ID, NUMBERTYPE, NUMBER) " +
                    "VALUES(" + ID + ", '" + address.getNumbers().get(i).first + "', '" +
                    address.getNumbers().get(i).second + "');");
        }

        size = address.getEmails().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Emails (ID, EMAIL) " +
                    "VALUES(" + ID + ", '" + address.getEmails().get(i) + "');");
        }

        size = address.getAddresses().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Addresses (ID, ADDRESS) " +
                    "VALUES(" + ID + ", '" + address.getAddresses().get(i) + "');");
        }
    }
    public void addAddress(int ID, Address address){
        db.execSQL("INSERT INTO ContactList (ID, NAME, MEMO)" +
                "VALUES(" + ID + ",'" + address.getName() + "', '" + address.getMemo() + "');");

        int size = address.getNumbers().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Numbers (ID, NUMBERTYPE, NUMBER)" +
                    "VALUES(" + ID + ", '" + address.getNumbers().get(i).first + "', '" +
                    address.getNumbers().get(i).second + "');");
        }

        size = address.getEmails().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Emails (ID, EMAIL)" +
                    "VALUES(" + ID + ", '" + address.getEmails().get(i) + "');");
        }

        size = address.getAddresses().size();
        for(int i = 0; i < size; i++){
            db.execSQL("INSERT INTO Addresses (ID, ADDRESS)" +
                    "VALUES(" + ID + ", '" + address.getAddresses().get(i) + "');");
        }
    }
    public void addAddress(String name, String number, String email, String address, String memo){
        ArrayList<Pair<String, String> > numbers = new ArrayList<>();
        numbers.add(new Pair<String, String>("phone", number));

        ArrayList<String> emails = new ArrayList<>();
        emails.add(email);

        ArrayList<String> addresses = new ArrayList<>();
        addresses.add(address);

        addAddress(name, numbers, emails, addresses, memo);
    }
    public void addAddress(String name, ArrayList<Pair<String, String>> numbers, ArrayList<String> emails, ArrayList<String> addresses, String memo){
        addAddress(new Address(name, numbers, emails, addresses, memo));
    }
    public void deleteAddress(int ID){
        db.execSQL("DELETE FROM ContactList WHERE ID=" + ID + ";");
        db.execSQL("DELETE FROM Numbers WHERE ID=" + ID + ";");
        db.execSQL("DELETE FROM Emails WHERE ID=" + ID + ";");
        db.execSQL("DELETE FROM Addresses WHERE ID=" + ID + ";");
    }
    public void editAddress(int ID, Address address){
        deleteAddress(ID);
        addAddress(ID, address);
    }

    // member variable
    private SQLiteDatabase db;
}