package com.example.start;

import androidx.annotation.StyleableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageButton startbutton;
    String welcome = "\"Button Clicked!\"";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        SharedPreferences resultLogin = getSharedPreferences("MyStorage", MODE_PRIVATE);
        final String output = resultLogin.getString("savedUsername","");
        //

        startbutton = findViewById(R.id.buttonmain);
        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (output.length() > 0){
                startActivities(new Intent[]{new  Intent(getApplicationContext(), Home.class)});
                }
                if (output.length() <= 0){
                    startActivities(new Intent[]{new  Intent(getApplicationContext(), Activity2.class)});
                }

            };

        });
    }
}
