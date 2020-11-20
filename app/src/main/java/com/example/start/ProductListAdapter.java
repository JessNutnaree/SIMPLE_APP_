package com.example.start;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;


import androidx.core.app.NotificationCompat;

import java.util.List;

public class ProductListAdapter extends BaseAdapter {
    private Context mContext;
    private List<Product> mProductList;


    public ProductListAdapter(Context mContext, List<Product> mProductList) {
        this.mContext = mContext;
        this.mProductList = mProductList;

    }

    @Override
    public int getCount() {
        return mProductList.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext, R.layout.item_list, null);
        TextView subName = (TextView) v.findViewById(R.id.Subjects);
        TextView taskName = (TextView) v.findViewById(R.id.Task);
        TextView dateName = (TextView) v.findViewById(R.id.date);
        TextView dateTextName = (TextView) v.findViewById(R.id.textView7);
        TextView timeTextName = (TextView) v.findViewById(R.id.timeList);
        subName.setText(mProductList.get(position).getsubject());
        taskName.setText(mProductList.get(position).gettask());
        dateName.setText(mProductList.get(position).getdate());
        dateTextName.setText(mProductList.get(position).getdatetext());
        timeTextName.setText(mProductList.get(position).gettimetext());

        final String TASK = mProductList.get(position).gettask();
        if (TASK.length() <= 15){
            taskName.setText(TASK);
        }
        if (TASK.length() > 15){
            final String CUT = TASK.substring(0 , 14);
            final String DOT = "..";
            String message2 = mContext.getApplicationContext().getString(R.string.dotdotdot, CUT, DOT);
            taskName.setText(message2);}


        return v;
    }

}


