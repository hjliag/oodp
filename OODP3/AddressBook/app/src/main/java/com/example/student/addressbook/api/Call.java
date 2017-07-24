package com.example.student.addressbook.api;
import java.util.Date;

/**
 * Created by student on 2017. 4. 13..
 */
public class Call {
    // constructor
    public Call(){ }
    public Call(String number, Boolean called, Boolean missed, Date startTime, Date endTime) {
        this.number = number;
        this.called = called;
        this.missed = missed;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // getters
    public String getNumber() {
        return number;
    }
    public Boolean getCalled() {
        return called;
    }
    public Boolean getMissed() {
        return missed;
    }
    public Date getStartTime() {
        return startTime;
    }
    public Date getEndTime() {
        return endTime;
    }

    // setters
    public void setPhoneNumber(String number) {
        this.number = number;
    }
    public void setCalled(Boolean called) {
        this.called = called;
    }
    public void setMissed(Boolean missed) {
        this.missed = missed;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    // member variables
    private String number = new String();
    private Boolean called = new Boolean(true);
    private Boolean missed = new Boolean(true);
    private Date startTime = new Date();
    private Date endTime = new Date();
}
