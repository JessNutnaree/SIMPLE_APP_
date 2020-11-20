package com.example.start;

import android.graphics.Color;
import android.graphics.Paint;
import android.widget.CheckBox;

import java.util.Date;

public class Product {
    private String mSubject;
    private String mTask;
    private String mDetails;
    private String mDate;
    private String mTime;
    private String mDateText;
    private Boolean mCheckBox;
    private String mUnique;


    public Product(String subject,String task, String details, String date, String datetext, Boolean checkbox, String timetext, String unique){
        mSubject = subject;
        mTask = task;
        mDetails = details;
        mDate = date;
        mDateText = datetext;
        mCheckBox = checkbox;
        mTime = timetext;
        mUnique = unique;


    }
    public String getsubject(){
        return mSubject;
    }
    public String gettask(){
        return mTask;
    }

    public String getdetails() {
        return mDetails;
    }
    public String getdate(){return mDate;}
    public String getdatetext() {
        return mDateText;
    }
    public Boolean getcheckbox() {
        return mCheckBox;
    }
    public String gettimetext() {
        return mTime;
    }
    public String getunique() {
        return mUnique;
    }
}
