package com.example.student.addressbook.api;
import java.util.Date;

/**
 * Created by student on 2017. 4. 12..
 */
public class SMS {
    // constructors
    public SMS(){ }
    public SMS(String number, Boolean sent, String message, Date date) {
        this.number = number;
        this.sent = sent;
        this.message = message;
        this.date = date;
    }
    public SMS(String number, Boolean sent, String message) {
        this.number = number;
        this.sent = sent;
        this.message = message;
        date = new Date();
    }

    // getters
    public String getNumber() {
        return number;
    }
    public Boolean getSent() {
        return sent;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }

    // setters
    public void setPhoneNumber(String phoneNumber) {
        this.number = phoneNumber;
    }
    public void setSent(Boolean sent) {
        this.sent = sent;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setDate(Date date) {
        this.date = date;
    }

    // Member variables
    private String number = new String();
    private Boolean sent = new Boolean(true);
    private String message = new String();
    private Date date = new Date();
}
