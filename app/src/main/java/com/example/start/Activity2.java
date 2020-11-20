package com.example.start;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AlertDialogLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Scanner;
import java.util.jar.Attributes;

public class Activity2 extends AppCompatActivity {
     Button doneButton;
     private EditText userName;
     TextView errorMessage;
     //preferences
     SharedPreferences mSharedPreferences;
     String userNameString;


     final Context context = this;
     Boolean valid = false;
        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2);
        doneButton = findViewById(R.id.donebutton);

        //sharing?
        mSharedPreferences = getApplicationContext().getSharedPreferences("MyStorage",MODE_PRIVATE);
        final SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        //shared?

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                errorMessage = findViewById(R.id.errorText);
                userName = findViewById(R.id.edittextname);
                userName.getText().toString();

                if (userName.length() <= 0 || userName.length() > 7){
                    valid = false;
                    errorMessage.setVisibility(View.VISIBLE);
                }
                if (userName.length() > 0 && userName.length() <= 7){
                    valid = true;

                    final  Dialog confirmation =  new Dialog(context);
                    confirmation.setContentView(R.layout.confirm);
                    TextView messageConfirm = (TextView)confirmation.findViewById(R.id.confirm);
                    messageConfirm.setText("Hi, " + userName.getText().toString() + ". Do you want to continue with this name?");
                    Button yesButton = (Button) confirmation.findViewById(R.id.yesb);
                    Button noButton = (Button) confirmation.findViewById(R.id.nob);

                    yesButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            userNameString = userName.getText().toString();
                            mEditor.putString("savedUsername",userNameString);
                            mEditor.commit();
                            Toast.makeText(Activity2.this,"Name Saved",Toast.LENGTH_LONG).show();

                            startActivities(new Intent[]{new  Intent(getApplicationContext(), Activity3.class)});
                            errorMessage.setVisibility(View.INVISIBLE);
                        }
                    });

                    noButton.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            mEditor.remove(userNameString);
                            mEditor.clear();
                            mEditor.commit();
                            Toast.makeText(Activity2.this,"Rename",Toast.LENGTH_LONG).show();

                            confirmation.dismiss();
                        }
                    });

                    confirmation.show();






                }
            }
        });


        }
}