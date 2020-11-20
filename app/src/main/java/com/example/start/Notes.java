package com.example.start;

import android.content.Intent;

class Notes {
    private String mTitle;
    private String mDetail;
    private int mInd;
    private Boolean mToggle;

    Notes(String title,String detail,int ind,Boolean toggle){
        this.mTitle = title;
        this.mDetail = detail;
        this.mInd = ind;
        this.mToggle = toggle;
    }
    private String getTitle(){
        return mTitle;
    }
    private String getDetail(){
        return mDetail;
    }
    private int getInd(){
        return mInd;
    }
    private Boolean getToggle(){
        return mToggle;
    }
}
