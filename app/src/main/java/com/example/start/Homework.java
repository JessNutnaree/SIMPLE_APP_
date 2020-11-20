package com.example.start;


import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import java.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */

public class Homework extends Fragment implements DatePickerDialog.OnDateSetListener {
    //Adapter
    private ListView itemList;
    private ProductListAdapter adapter;
    List<Product> todoList = new ArrayList<>();

    //finishAdapter
    private ListView itemList2;
    private AdapterFinish adapter2;
    List<Product> todoList2 = new ArrayList<>();

    public Homework() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_homework, container, false);

        SharedPreferences preferences2 = getActivity().getSharedPreferences("DefaultTime", Context.MODE_PRIVATE);
        final String time = preferences2.getString("insertTime","");


        ImageButton addNewtask = v.findViewById(R.id.addButton);
        Switch switchtask = v.findViewById(R.id.switch1);

        //Arraylist
        itemList = v.findViewById(R.id.hwList);
        itemList2 = v.findViewById(R.id.doneList);

        loadData();
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), todoList);
        itemList.setAdapter(adapter);

        loadData2();
        adapter2 = new AdapterFinish(getActivity().getApplicationContext(), todoList2);
        itemList2.setAdapter(adapter2);

        itemList2.setVisibility(View.GONE);

        //SWITCH
        switchtask.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                itemList.setVisibility(View.GONE);
                itemList2.setVisibility(View.VISIBLE);
            } else {
                itemList.setVisibility(View.VISIBLE);
                itemList2.setVisibility(View.GONE);
            }
        });

        //Adding Task
        addNewtask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog confirmation = new Dialog(getActivity());
                confirmation.setContentView(R.layout.addtask);
                final EditText edittext = confirmation.findViewById(R.id.addTask);
                final EditText edittask = confirmation.findViewById(R.id.task);
                final EditText details = confirmation.findViewById(R.id.details);
                final Button additem = confirmation.findViewById(R.id.additem);
                final ImageButton deleteB = confirmation.findViewById(R.id.imageButton2);
                final ImageButton calendarB = confirmation.findViewById(R.id.calendar);
                final ImageButton timeB = confirmation.findViewById(R.id.timeButt);
                final TextView duedateT = confirmation.findViewById(R.id.duedate);
                final TextView duetimeT = confirmation.findViewById(R.id.time);
                final TextView dateText = confirmation.findViewById(R.id.textView6);
                final CheckBox checkBox = confirmation.findViewById(R.id.checkBox);
                final TextView idText = confirmation.findViewById(R.id.Id);
                additem.setText("Add task!");
                checkBox.setVisibility(View.INVISIBLE);
                deleteB.setVisibility(View.INVISIBLE);

                //ADD_ID (NEW)
                Date currentTime = Calendar.getInstance().getTime();

                SimpleDateFormat idFormat = new SimpleDateFormat("MMddHHmmss");
                String id = idFormat.format(currentTime.getTime());
                try {
                    Date date = idFormat.parse(id);
                    idText.setText(idFormat.format(date));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                timeB.setOnClickListener(new View.OnClickListener() {
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
                                    duetimeT.setText(formatterOut.format(date));
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                        timeSetListener.show();
                    }
                });

                calendarB.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar calendar = Calendar.getInstance();
                        int year = calendar.get(Calendar.YEAR);
                        int month = calendar.get(Calendar.MONTH);
                        int day = calendar.get(Calendar.DAY_OF_MONTH);
                        DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
                        dateDialog.show();
                    }

                    private DatePickerDialog.OnDateSetListener datePickerListener = (view, year, month, dayOfMonth) -> {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                        String newDate = dateFormat.format(cal.getTime());
                        duedateT.setText(newDate);
                        SimpleDateFormat dateTextFormat = new SimpleDateFormat("EEE, MMM d");
                        String newDateText = dateTextFormat.format(cal.getTime());
                        dateText.setText(newDateText);
                    };
                });

                confirmation.show();

                //AddItems
                additem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (dateText.getText().toString().length() > 0) {

                            if (duetimeT.getText().toString().length() > 0) {

                                //Sun May 03 15:41:44 GMT+07:00 2020
                                Date currentTime = Calendar.getInstance().getTime();

                                todoList.add(new Product(edittext.getText().toString(), edittask.getText().toString(),
                                        details.getText().toString(), duedateT.getText().toString(), dateText.getText().toString(), false, duetimeT.getText().toString(), idText.getText().toString()));
                                adapter.notifyDataSetChanged();
                                sorting();
                                saveData();


                                //NOTIFICATION
                                String string = (duedateT.getText().toString() + duetimeT.getText().toString());
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyh:mm a");
                                String currentDateandTime = sdf.format(currentTime.getTime());
                                System.out.println("curentdatetime:" + currentDateandTime);
                                try {
                                    Date current = sdf.parse(currentDateandTime);
                                    long currentMillis = current.getTime();
                                    Date date = sdf.parse(string);
                                    long millis = date.getTime();
                                    System.out.println(millis + "/" + currentMillis);
                                    if (millis >= currentMillis) {
                                        scheduleNotification(getNotification(edittext.getText().toString(), edittask.getText().toString(), millis,idText.getText().toString()), millis, idText.getText().toString());
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (duetimeT.getText().toString().length() <= 0) {

                                //Sun May 03 15:41:44 GMT+07:00 2020
                                Date currentTime = Calendar.getInstance().getTime();

                                //SET_DEFAULT_TIME
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("h:mm a");
                                String defaultTime = simpleDateFormat.format(currentTime.getTime());
                                if (time.length() <= 0){
                                duetimeT.setText(defaultTime);}
                                if (time.length() > 0){
                                    duetimeT.setText(time);
                                }

                                todoList.add(new Product(edittext.getText().toString(), edittask.getText().toString(),
                                        details.getText().toString(), duedateT.getText().toString(), dateText.getText().toString(), false, duetimeT.getText().toString(), idText.getText().toString()));
                                adapter.notifyDataSetChanged();
                                sorting();
                                saveData();

                                //NOTIFICATION
                                String string = (duedateT.getText().toString() + duetimeT.getText().toString());
                                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyh:mm a");
                                String currentDateandTime = sdf.format(currentTime.getTime());

                                try {
                                    Date current = sdf.parse(currentDateandTime);
                                    long currentMillis = current.getTime();
                                    Date date = sdf.parse(string);
                                    long millis = date.getTime();
                                    if (millis >= currentMillis) {
                                        scheduleNotification(getNotification(edittext.getText().toString(), edittask.getText().toString(), millis,idText.getText().toString()), millis, idText.getText().toString());
                                    }
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            edittext.setText("");
                            edittask.setText("");
                            details.setText("");
                            duedateT.setText("");
                            dateText.setText("");
                            duetimeT.setText("");

                            confirmation.dismiss();

                        } else
                            Toast.makeText(getActivity(), "Please put in a due date!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        itemList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showEditBox(todoList.get(position), position);
                return true;
            }
        });
        itemList2.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                showEditBox(todoList2.get(position), position);
                return true;
            }
        });
        return v;
    }


    //METHOD

    //todolist
    public void showEditBox(final Product oldItem, final int index) {
        final Dialog confirmation = new Dialog(getActivity());
        confirmation.setContentView(R.layout.addtask);
        final EditText edittext = confirmation.findViewById(R.id.addTask);
        final EditText edittask = confirmation.findViewById(R.id.task);
        final EditText details = confirmation.findViewById(R.id.details);
        final Button additem = confirmation.findViewById(R.id.additem);
        final ImageButton calendarB = confirmation.findViewById(R.id.calendar);
        final TextView duedateT = confirmation.findViewById(R.id.duedate);
        final TextView dateText = confirmation.findViewById(R.id.textView6);
        final TextView duetimeT = confirmation.findViewById(R.id.time);
        final CheckBox checkBox = confirmation.findViewById(R.id.checkBox);
        final ImageButton timeB = confirmation.findViewById(R.id.timeButt);
        final TextView idText = confirmation.findViewById(R.id.Id);
        additem.setText("Save!");
        checkBox.setChecked(oldItem.getcheckbox());
        duetimeT.setText(oldItem.gettimetext());
        duedateT.setText(oldItem.getdate());
        details.setText(oldItem.getdetails());
        edittask.setText(oldItem.gettask());
        edittext.setText(oldItem.getsubject());
        dateText.setText(oldItem.getdatetext());
        idText.setText(oldItem.getunique());
        final ImageButton deleteB = confirmation.findViewById(R.id.imageButton2);

        //FALSE
        if (oldItem.getcheckbox() == false) {
            additem.setVisibility(View.VISIBLE);

            //buttons
            timeB.setOnClickListener(new View.OnClickListener() {
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
                                duetimeT.setText(formatterOut.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                    timeSetListener.show();
                }

            });

            calendarB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    calendar.set(Calendar.MINUTE, 0);
                    calendar.set(Calendar.SECOND, 0);
                    calendar.set(Calendar.MILLISECOND, 0);
                    DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
                    dateDialog.show();
                }

                private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                        SimpleDateFormat dateTextFormat = new SimpleDateFormat("EEE, MMM d");
                        String newDateText = dateTextFormat.format(cal.getTime());
                        dateText.setText(newDateText);
                        String newDate = dateFormat.format(cal.getTime());
                        duedateT.setText(newDate);
                    }
                };
            });

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Delete this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    todoList.remove(oldItem);
                                    remove(oldItem.getunique().toString());
                                    adapter.notifyDataSetChanged();
                                    saveData();
                                }
                            })
                            .setNegativeButton("No", null)
                            .create();
                    dialog.show();
                    confirmation.dismiss();
                }
            });

            additem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Sun May 03 15:41:44 GMT+07:00 2020
                    Date currentTime = Calendar.getInstance().getTime();

                    //REMOVE_ID (OLD)
                    remove(oldItem.getunique());

                    //ADD_ID (NEW)
                    SimpleDateFormat idFormat = new SimpleDateFormat("yyMMddHHmmss");
                    String id = idFormat.format(currentTime.getTime());
                    try {
                        Date date = idFormat.parse(id);
                        idText.setText(idFormat.format(date));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    todoList.set(index, new Product(edittext.getText().toString(), edittask.getText().toString(), details.getText().toString(), duedateT.getText()
                            .toString(), dateText.getText().toString(), false, duetimeT.getText().toString(), idText.getText().toString()));
                    adapter.notifyDataSetChanged();
                    saveData();

                    //NOTIFICATION
                    String string = (duedateT.getText().toString() + duetimeT.getText().toString());
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyh:mm a");
                    String currentDateandTime = sdf.format(currentTime.getTime());
                    try {
                        Date current = sdf.parse(currentDateandTime);
                        long currentMillis = current.getTime();
                        Date date2 = sdf.parse(string);
                        long millis = date2.getTime();
                        if (millis >= currentMillis) {
                            scheduleNotification(getNotification(edittext.getText().toString(), edittask.getText().toString(), millis,idText.getText().toString()), millis, idText.getText().toString());
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    confirmation.dismiss();
                    sorting();
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (checkBox.isChecked()) {
                        additem.setVisibility(View.INVISIBLE);
                        todoList.remove(oldItem);
                        adapter.notifyDataSetChanged();
                        saveData();

                        //Sun May 03 15:41:44 GMT+07:00 2020
                        Date currentTime = Calendar.getInstance().getTime();

                        //ADD_ID (NEW)
                        SimpleDateFormat idFormat = new SimpleDateFormat("MMddHHmmss");
                        String id = idFormat.format(currentTime.getTime());
                        try {
                            Date date = idFormat.parse(id);
                            idText.setText(idFormat.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        todoList2.add(new Product(edittext.getText().toString(), edittask.getText().toString(), details.getText().toString(), duedateT.getText()
                                .toString(), dateText.getText().toString(), true, duetimeT.getText().toString(), idText.getText().toString()));
                        adapter2.notifyDataSetChanged();
                        sorting2();
                        saveData2();

                        remove(oldItem.getunique());

                        checkBox.setEnabled(false);
                    }
                }
            });

            confirmation.show();
        }

        //TRUE
        if (oldItem.getcheckbox() == true) {
            additem.setVisibility(View.INVISIBLE);

            timeB.setOnClickListener(new View.OnClickListener() {
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
                                duetimeT.setText(formatterOut.format(date));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                        }
                    }, hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                    timeSetListener.show();
                }

            });

            calendarB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    DatePickerDialog dateDialog = new DatePickerDialog(getActivity(), datePickerListener, year, month, day);
                    dateDialog.show();
                }

                private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, dayOfMonth);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                        SimpleDateFormat dateTextFormat = new SimpleDateFormat("EEE, MMM d");
                        String newDateText = dateTextFormat.format(cal.getTime());
                        dateText.setText(newDateText);
                        String newDate = dateFormat.format(cal.getTime());
                        duedateT.setText(newDate);
                    }
                };
            });

            deleteB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog dialog = new AlertDialog.Builder(getActivity())
                            .setTitle("Delete this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    todoList2.remove(oldItem);
                                    adapter2.notifyDataSetChanged();
                                    saveData2();
                                }
                            })
                            .setNegativeButton("No", null)
                            .create();
                    dialog.show();
                    confirmation.dismiss();
                }
            });

            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!checkBox.isChecked()) {
                        todoList2.remove(oldItem);
                        adapter2.notifyDataSetChanged();
                        saveData2();

                        //Sun May 03 15:41:44 GMT+07:00 2020
                        Date currentTime = Calendar.getInstance().getTime();

                        //ADD_ID (NEW)
                        SimpleDateFormat idFormat = new SimpleDateFormat("MMddHHmmss");
                        String id = idFormat.format(currentTime.getTime());
                        try {
                            Date date = idFormat.parse(id);
                            idText.setText(idFormat.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        todoList.add(new Product(edittext.getText().toString(), edittask.getText().toString(), details.getText().toString(), duedateT.getText()
                                .toString(), dateText.getText().toString(), false, duetimeT.getText().toString(), idText.getText().toString()));
                        adapter.notifyDataSetChanged();
                        sorting();
                        saveData();

                        //NOTIFICATION
                        String string = (duedateT.getText().toString() + duetimeT.getText().toString());
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyh:mm a");
                        String currentDateandTime = sdf.format(currentTime.getTime());
                        try {
                            Date current = sdf.parse(currentDateandTime);
                            long currentMillis = current.getTime();
                            Date date2 = sdf.parse(string);
                            long millis = date2.getTime();
                            if (millis >= currentMillis) {
                                scheduleNotification(getNotification(edittext.getText().toString(), edittask.getText().toString(), millis,idText.getText().toString()), millis, idText.getText().toString());
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        checkBox.setEnabled(false);
                    }
                }
            });

            confirmation.show();
        }
    }

    //sorting
    public void sorting() {

        if (todoList.size() > 0)
            Collections.sort(todoList, (o1, o2) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyh:mm a");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(o1.getdate() + o1.gettimetext());
                    date2 = dateFormat.parse(o2.getdate() + o2.gettimetext());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            });

    }

    public void sorting2() {

        if (todoList.size() > 0)
            Collections.sort(todoList2, (o1, o2) -> {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyh:mm a");
                Date date1 = null;
                Date date2 = null;
                try {
                    date1 = dateFormat.parse(o1.getdate() + o1.gettimetext());
                    date2 = dateFormat.parse(o2.getdate() + o2.gettimetext());

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return date1.compareTo(date2);
            });

    }

    //saveData
    private void saveData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("homework", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(todoList);
        editor.putString("homeworkList", json);
        editor.apply();

    }

    private void saveData2() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("homework2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences.edit();
        Gson gson2 = new Gson();
        String json2 = gson2.toJson(todoList2);
        editor2.putString("homeworkList2", json2);
        editor2.apply();

    }

    //loadData
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

    private void loadData() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("homework", Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("homeworkList", null);
        Type type = new TypeToken<ArrayList<Product>>() {
        }.getType();
        todoList = gson.fromJson(json, type);
        if (todoList == null)
            todoList = new ArrayList<>();
    }


    //notifications
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    private void remove(String alarmId) {
        //remove(idText.getText().toString());
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(getActivity(), App.class);
        Long i = Long.parseLong(alarmId);
        int y = i.intValue();
        System.out.println(y);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), y, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.cancel(pendingIntent);
    }
    private void scheduleNotification(Notification notification, long delay, String alarmId) {
        //scheduleNotification(getNotification(edittext.getText().toString(), edittask.getText().toString()), millis,idText.getText().toString());
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent notificationIntent = new Intent(getActivity(), App.class);
        notificationIntent.putExtra(App.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(App.NOTIFICATION, notification);
        Long i = Long.parseLong(alarmId);
        int y = i.intValue();
        System.out.println(y);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), y, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, delay, pendingIntent);


    }
    private Notification getNotification(String edittext, String edittask, Long when, String alarmId) {

        Intent notiWarp = new Intent(getContext(), MainActivity.class);
        Long i = Long.parseLong(alarmId);
        int y = i.intValue();
        PendingIntent notiPending = PendingIntent.getActivity(getContext(),y,notiWarp,PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), default_notification_channel_id);
        builder.setContentTitle("Your task for today");
        builder.setContentText(edittext + ":  " + edittask);
        builder.setSmallIcon(R.drawable.notify);
        builder.setAutoCancel(true);
        builder.setContentIntent(notiPending);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        builder.setWhen(when);
        return builder.build();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }


}






