package com.example.student.addressbook.api;

import android.util.Pair;

import java.util.ArrayList;

/**
 * Created by student on 2017. 4. 8..
 */
public class Address {
    // Constructors
    public Address() { }
    public Address(String name, ArrayList<Pair<String, String> > numbers, ArrayList<String> emails, ArrayList<String> addresses, String memo) {
        this.name = name;
        this.numbers = numbers;
        this.emails = emails;
        this.addresses = addresses;
        this.memo = memo;
    }

    // getters
    public String getName() {
        return name;
    }
    public ArrayList<Pair<String, String> > getNumbers() {
        return numbers;
    }
    public ArrayList<String> getEmails() {
        return emails;
    }
    public ArrayList<String> getAddresses() {
        return addresses;
    }
    public String getMemo() {
        return memo;
    }

    // setters
    public void setName(String name) {
        this.name = name;
    }
    public void setNumbers(ArrayList<Pair<String, String>> numbers) {
        this.numbers = numbers;
    }
    public void setEmails(ArrayList<String> emails) {
        this.emails = emails;
    }
    public void setAddresses(ArrayList<String> addresses) {
        this.addresses = addresses;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    // member function

    // member variables
    private String name = new String();
    private ArrayList<Pair<String, String> > numbers = new ArrayList<Pair<String, String> >();
    private ArrayList<String> emails = new ArrayList<String>();
    private ArrayList<String> addresses = new ArrayList<String>();
    private String memo = new String();
}
