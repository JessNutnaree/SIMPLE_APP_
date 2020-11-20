package com.example.start;

import android.content.Context;
import android.content.res.Resources;
import android.text.InputFilter;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Notesadapter extends BaseAdapter {
    public static int getLineCount(String text){
        return text.split("[\n|\r]").length;
    }
    private Context ct;
    private List<Notes> noteL;

    public Notesadapter(Context ct, List<Notes> noteL) {
        this.ct = ct;
        this.noteL = noteL;
    }

    @Override
    public int getCount() {
        return noteL.size();
    }

    @Override
    public Object getItem(int position) {
        return noteL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = View.inflate(ct,R.layout.notes,null);
        TextView Tnotes = (TextView) view.findViewById(R.id.titleNotes);
        TextView Dnotes = (TextView) view.findViewById(R.id.detailNotes);
        ImageView Pnotes = (ImageView) view.findViewById(R.id.pinImage);
        Tnotes.setText(noteL.get(position).getTitle());

        //Details
        final String APPEAR = noteL.get(position).getDetail();
        int count = getLineCount(APPEAR);
        if (count > 1){
            final String CUT = APPEAR.substring(0, APPEAR.indexOf("\n"));
            final String DOT = "..";
            String message = ct.getApplicationContext().getString(R.string.dotdotdot, CUT, DOT);
            Dnotes.setText(message);
        }
        if (count <= 1){
            if (APPEAR.length() <= 30){
                Dnotes.setText(APPEAR);
            }
            if (APPEAR.length() > 30){
                final String CUT = APPEAR.substring(0 , 30);
                final String DOT = "..";
                String message = ct.getApplicationContext().getString(R.string.dotdotdot, CUT, DOT);
                Dnotes.setText(message);}
        }

        //Titles
        final String TITLE = noteL.get(position).getTitle();
        if (TITLE.length() <= 15){
            Tnotes.setText(TITLE);
        }
        if (TITLE.length() > 15){
            final String CUT = TITLE.substring(0 , 15);
            final String DOT = "..";
            String message2 = ct.getApplicationContext().getString(R.string.dotdotdot, CUT, DOT);
            Tnotes.setText(message2);}

        //pinned
        if (noteL.get(position).getToggle() == true){
            Pnotes.setVisibility(View.VISIBLE);
        }
        if (noteL.get(position).getToggle() == false){
            Pnotes.setVisibility(View.INVISIBLE);
        }


        return view;
    }
}


