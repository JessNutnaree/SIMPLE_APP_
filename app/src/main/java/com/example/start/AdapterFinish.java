package com.example.start;

import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AdapterFinish extends BaseAdapter {
    private Context mContext2;
    private List<Product> mProductList2;

    public AdapterFinish(Context mContext2, List<Product> mProductList2) {
        this.mContext2 = mContext2;
        this.mProductList2 = mProductList2;
    }

    @Override
    public int getCount() {
        return mProductList2.size();
    }

    @Override
    public Object getItem(int position) {
        return mProductList2.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = View.inflate(mContext2, R.layout.item_finish, null);
        TextView subName = (TextView) v.findViewById(R.id.Subjects);
        TextView taskName = (TextView) v.findViewById(R.id.Task);
        TextView dateName = (TextView) v.findViewById(R.id.date);
        TextView dateTextName = (TextView) v.findViewById(R.id.textView7);
        TextView timeTextName = (TextView) v.findViewById(R.id.timeList);
        subName.setText(mProductList2.get(position).getsubject());
        taskName.setText(mProductList2.get(position).gettask());
        dateName.setText(mProductList2.get(position).getdate());
        dateTextName.setText(mProductList2.get(position).getdatetext());
        timeTextName.setText(mProductList2.get(position).gettimetext());

        final String TASK = mProductList2.get(position).gettask();
        if (TASK.length() <= 15){
            taskName.setText(TASK);
        }
        if (TASK.length() > 15){
            final String CUT = TASK.substring(0 , 14);
            final String DOT = "..";
            String message2 = mContext2.getApplicationContext().getString(R.string.dotdotdot, CUT, DOT);
            taskName.setText(message2);}

       if (mProductList2.get(position).getcheckbox() == true){
           subName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
           dateTextName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
           taskName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
           timeTextName.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
       }
       if (mProductList2.get(position).getcheckbox() == false) {
           subName.setPaintFlags(subName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
           dateTextName.setPaintFlags(subName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
           taskName.setPaintFlags(subName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
           timeTextName.setPaintFlags(subName.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

       }




        return v;
    }
}
