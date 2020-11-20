package com.example.start;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Goals extends Fragment {
    TextView account;
    Button editprf;
    ListView itemList2;
    AdapterFinish adapter2;
    List<Product> todoList2 = new ArrayList<>();

    private ListView memoList;
    private Notesadapter notesadapter;
    List<Notes> memo = new ArrayList<>();

    SharedPreferences DefaultSharedPreferences;
    String TimeString;


    public Goals() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_goals, container, false);

        itemList2 = getActivity().findViewById(R.id.doneList);
        adapter2 = new AdapterFinish(getActivity().getApplicationContext(), todoList2);

        memoList = v.findViewById(R.id.notesList);
        notesadapter = new Notesadapter(getActivity().getApplicationContext(), memo);

        SharedPreferences preferences = getActivity().getSharedPreferences("MyStorage", Context.MODE_PRIVATE);
        final String value = preferences.getString("savedUsername",null);
        final SharedPreferences.Editor mEditor = preferences.edit();
        TextView account = (TextView) v.findViewById(R.id.accountname);
        account.setText(value);

        SharedPreferences preferences2 = getActivity().getSharedPreferences("DefaultTime", Context.MODE_PRIVATE);
        final String time = preferences2.getString("insertTime","");

        Button editprf = (Button) v.findViewById(R.id.buttonedit);
        Button task = v.findViewById(R.id.buttontask);
        Button defaultTime = v.findViewById(R.id.buttontask3);
        TextView timeInfo = v.findViewById(R.id.textView9);
        Button memoClear = v.findViewById(R.id.buttontask81);
        CheckBox checkTime = v.findViewById(R.id.duetimeCheck);

        DefaultSharedPreferences = getActivity().getApplicationContext().getSharedPreferences("DefaultTime",Context.MODE_PRIVATE);
        final SharedPreferences.Editor DefaultEditor = DefaultSharedPreferences.edit();


        if (time.length() > 0){
            checkTime.setChecked(true);
            defaultTime.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
            timeInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
            defaultTime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    defaultTime.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
                    timeInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
                    SharedPreferences preferences2 = getActivity().getSharedPreferences("DefaultTime", Context.MODE_PRIVATE);
                    final String time = preferences2.getString("insertTime","");
                    final Dialog confirmation = new Dialog(getContext());
                    confirmation.setContentView(R.layout.defaulttime);
                    TextView direction = (TextView) confirmation.findViewById(R.id.confirm1);
                    Button savebutton = (Button) confirmation.findViewById(R.id.saveB);
                    TextView dueTimeD = (TextView) confirmation.findViewById(R.id.dueTimeDefault1);
                    ImageButton clock = (ImageButton) confirmation.findViewById(R.id.clockDefault);
                    dueTimeD.setText(time.toString());
                    confirmation.show();

                    clock.setOnClickListener(new View.OnClickListener() {
                        final Calendar calendar = Calendar.getInstance();
                        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog timeSetListener = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    String string = (hourOfDay + ":" + minute);
                                    SimpleDateFormat format = new SimpleDateFormat("h:m");
                                    SimpleDateFormat formatterOut = new SimpleDateFormat("h:mm a");
                                    try {
                                        Date date = format.parse(string);
                                        dueTimeD.setText(formatterOut.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                            timeSetListener.show();
                        }
                    });

                    savebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dueTimeD.length() > 0) {
                                if (time.length() > 0) {
                                    TimeString = dueTimeD.getText().toString();
                                    DefaultEditor.putString("insertTime", TimeString);
                                    DefaultEditor.apply();
                                    Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                if (time.length() <= 0) {
                                    TimeString = dueTimeD.getText().toString();
                                    DefaultEditor.putString("insertTime", TimeString);
                                    DefaultEditor.commit();
                                    System.out.println(time);
                                    Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                confirmation.dismiss();
                            }
                            else
                                Toast.makeText(getActivity(), "Please select a time!", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            });
        }
        if (time.length() <= 0){
            defaultTime.setOnClickListener(null);
            checkTime.setChecked(false);
        }

        checkTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkTime.isChecked()){
                    defaultTime.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
                    timeInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.defaultdue));
                    SharedPreferences preferences2 = getActivity().getSharedPreferences("DefaultTime", Context.MODE_PRIVATE);
                    final String time = preferences2.getString("insertTime","");
                    final Dialog confirmation = new Dialog(getContext());
                    confirmation.setContentView(R.layout.defaulttime);
                    TextView direction = (TextView) confirmation.findViewById(R.id.confirm1);
                    Button savebutton = (Button) confirmation.findViewById(R.id.saveB);
                    TextView dueTimeD = (TextView) confirmation.findViewById(R.id.dueTimeDefault1);
                    ImageButton clock = (ImageButton) confirmation.findViewById(R.id.clockDefault);
                    dueTimeD.setText(time.toString());

                    dueTimeD.setText("8:00 AM");
                    DefaultEditor.putString("insertTime", "8:00 AM");
                    DefaultEditor.commit();

                    confirmation.show();

                    clock.setOnClickListener(new View.OnClickListener() {
                        final Calendar calendar = Calendar.getInstance();
                        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                        final int minute = calendar.get(Calendar.MINUTE);
                        @Override
                        public void onClick(View v) {
                            TimePickerDialog timeSetListener = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                @Override
                                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                    String string = (hourOfDay + ":" + minute);
                                    SimpleDateFormat format = new SimpleDateFormat("h:m");
                                    SimpleDateFormat formatterOut = new SimpleDateFormat("h:mm a");
                                    try {
                                        Date date = format.parse(string);
                                        dueTimeD.setText(formatterOut.format(date));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                            timeSetListener.show();
                        }
                    });
                    savebutton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (dueTimeD.length() > 0) {
                                if (time.length() > 0) {
                                    TimeString = dueTimeD.getText().toString();
                                    DefaultEditor.putString("insertTime", TimeString);
                                    DefaultEditor.apply();
                                    Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                if (time.length() <= 0) {
                                    TimeString = dueTimeD.getText().toString();
                                    DefaultEditor.putString("insertTime", TimeString);
                                    DefaultEditor.commit();
                                    System.out.println(time);
                                    Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                }
                                confirmation.dismiss();
                            }
                            else
                                Toast.makeText(getActivity(), "Please select a time!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    defaultTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            confirmation.show();

                            clock.setOnClickListener(new View.OnClickListener() {
                                final Calendar calendar = Calendar.getInstance();
                                final int hour = calendar.get(Calendar.HOUR_OF_DAY);
                                final int minute = calendar.get(Calendar.MINUTE);
                                @Override
                                public void onClick(View v) {
                                    TimePickerDialog timeSetListener = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                                        @Override
                                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                            String string = (hourOfDay + ":" + minute);
                                            SimpleDateFormat format = new SimpleDateFormat("h:m");
                                            SimpleDateFormat formatterOut = new SimpleDateFormat("h:mm a");
                                            try {
                                                Date date = format.parse(string);
                                                dueTimeD.setText(formatterOut.format(date));
                                            } catch (ParseException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                                    timeSetListener.show();
                                }
                            });

                            savebutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (dueTimeD.length() > 0) {
                                        if (time.length() > 0) {
                                            TimeString = dueTimeD.getText().toString();
                                            DefaultEditor.putString("insertTime", TimeString);
                                            DefaultEditor.apply();
                                            Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        if (time.length() <= 0) {
                                            TimeString = dueTimeD.getText().toString();
                                            DefaultEditor.putString("insertTime", TimeString);
                                            DefaultEditor.commit();
                                            System.out.println(time);
                                            Toast.makeText(getActivity(), "Default due time set to:" + " " + dueTimeD.getText().toString(), Toast.LENGTH_SHORT).show();
                                        }
                                        confirmation.dismiss();
                                    }
                                    else
                                        Toast.makeText(getActivity(), "Please select a time!", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });
                }
                if (!checkTime.isChecked()){
                    defaultTime.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                    timeInfo.setTextColor(ContextCompat.getColor(getContext(), R.color.Black));
                    defaultTime.setOnClickListener(null);
                    DefaultEditor.clear();
                    DefaultEditor.commit();
                }
            }
        });
        memoClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog memoD = new Dialog(getContext());
                memoD.setContentView(R.layout.restartapp);
                Button deleteB = (Button) memoD.findViewById(R.id.restartB);
                Button notDeleteB = (Button) memoD.findViewById(R.id.notrestartB);
                memoD.show();
                deleteB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadData();
                        memo.clear();
                        notesadapter.notifyDataSetChanged();
                        saveData();
                        memoD.dismiss();
                    }
                });
                notDeleteB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        memoD.dismiss();
                    }
                });

            }
        });


        editprf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog renameDia = new Dialog(getContext());
                renameDia.setContentView(R.layout.activity_rename);
                TextView errorMessage = renameDia.findViewById(R.id.errorText);
                EditText userName = renameDia.findViewById(R.id.edittextname);
                Button buttonRename = renameDia.findViewById(R.id.donebutton2);
                renameDia.show();

                buttonRename.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (userName.length() <= 0 || userName.length() > 7){
                            errorMessage.setVisibility(View.VISIBLE);
                        }
                        if (userName.length() > 0 && userName.length() <= 7) {
                            mEditor.putString("savedUsername", userName.getText().toString());
                            mEditor.commit();
                            account.setText(userName.getText().toString());
                            renameDia.dismiss();

                        }
                    }
                });
            }
        });

        task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(getActivity())
                        .setTitle("Are you sure you want to delete all of your finish tasks?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                loadData2();
                                todoList2.clear();
                                adapter2.notifyDataSetChanged();
                                saveData2();
                            }
                        })
                        .setNegativeButton("No", null)
                        .create();
                dialog.show();
                dialog.getWindow().setBackgroundDrawableResource(R.color.red);
                dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
                dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getContext(), R.color.white));
            }
        });

        return v;
    }
    private void loadData2() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("homework2", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences.getString("homeworkList2", null);
        Type type2 = new TypeToken<ArrayList<Product>>() {
        }.getType();
        todoList2 = gson2.fromJson(json2, type2);
        if (todoList2 == null)
            todoList2 = new ArrayList<>();
    }
    private void saveData2() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("homework2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(todoList2);
        editor2.putString("homeworkList2", json2);
        editor2.apply();

    }

    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Memo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(memo);
        editor.putString("memoNotes", json);
        editor.apply();

    }
    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Memo", Context.MODE_PRIVATE);
        Gson gson2 = new Gson();
        String json2 = sharedPreferences.getString("memoNotes", null);
        Type type2 = new TypeToken<ArrayList<Notes>>() {
        }.getType();
        memo = gson2.fromJson(json2, type2);
        if (memo == null)
            memo = new ArrayList<>();
    }

    //RESET data
    private void deleteAppData() {
        try {
            // clearing app data
            String packageName = getActivity().getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);

        } catch (Exception e) {
            e.printStackTrace();
        } }
}
