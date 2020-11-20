package com.example.start;

import android.app.ActionBar;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import androidx.appcompat.widget.Toolbar;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Schedule extends Fragment {
    private ListView memoList;
    private Notesadapter notesadapter;
    List<Notes> memo = new ArrayList<>();

    ViewFlipper viewFlipper;
    ViewFlipper viewFlipper3;


    public Schedule() {
        // Required empty public constructor
        //((RelativeLayout)findViewById(R.id.includedLayout)).setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_schedule, container, false);

        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);

        //USERNAME
        SharedPreferences preferences = getActivity().getSharedPreferences("MyStorage", Context.MODE_PRIVATE);
        final String value = preferences.getString("savedUsername", null);
        final SharedPreferences.Editor mEditor = preferences.edit();
        TextView username = (TextView) v.findViewById(R.id.accountname2);
        username.setText(value);

        //MEMOLIST
        loadData();
        memoList = v.findViewById(R.id.notesList);
        notesadapter = new Notesadapter(getActivity().getApplicationContext(), memo);
        memoList.setAdapter(notesadapter);

        //FLIPPERS
        viewFlipper = (ViewFlipper) v.findViewById(R.id.view_flipper);
        viewFlipper3 = (ViewFlipper) v.findViewById(R.id.view_flipper3);

        //ADD_BUTTON
        ImageButton addMemo = v.findViewById(R.id.addMemo);
        EditText titleM = v.findViewById(R.id.notesTitle);
        EditText detailM = v.findViewById(R.id.notesDetails);
        addMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibility();
                bottomNavigationView.setVisibility(View.INVISIBLE);
                viewFlipper.setVisibility(View.VISIBLE);
                viewFlipper3.setVisibility(View.INVISIBLE);
                viewFlipper.showNext();
                titleM.setText("");
                detailM.setText("");
            }
        });

        //Relativelayout
        Button saveUser = v.findViewById(R.id.memoSaved12);
        ImageButton backB = v.findViewById(R.id.imageButton31);
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibility();
                bottomNavigationView.setVisibility(View.VISIBLE);
                memo.add(new Notes(titleM.getText().toString(), detailM.getText().toString(), 1, false));
                notesadapter.notifyDataSetChanged();
                sorting();
                saveData();
                viewFlipper.showPrevious();

            }
        });
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                visibility();
                bottomNavigationView.setVisibility(View.VISIBLE);
                viewFlipper.showPrevious();

            }
        });

        //MEMO_ITEM
        memoList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edit(memo.get(position),position);
            }
        });

        return v;

    }

    //METHOD
    private void edit(final Notes oldItem, final int index) {

        //Display
        viewFlipper3 = (ViewFlipper) getActivity().findViewById(R.id.view_flipper3);
        viewFlipper = (ViewFlipper) getActivity().findViewById(R.id.view_flipper);
        viewFlipper3.setVisibility(View.VISIBLE);
        viewFlipper.setVisibility(View.GONE);
        viewFlipper3.showNext();

        //TOOOL_BAR
        BottomNavigationView bottomNavigationView = getActivity().findViewById(R.id.bottomNav);
        bottomNavigationView.setVisibility(View.INVISIBLE);

        //LAYOUT_EDIT
        EditText titleM = getActivity().findViewById(R.id.notesTitle3);
        EditText detailM = getActivity().findViewById(R.id.notesDetails3);
        ToggleButton toggleB = getActivity().findViewById(R.id.toggleButton);
        titleM.setText(oldItem.getTitle());
        detailM.setText(oldItem.getDetail());
        toggleB.setChecked(oldItem.getToggle());

        Button saveUser = getActivity().findViewById(R.id.memoSaved123);
        ImageButton backB = getActivity().findViewById(R.id.imageButton313);

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar3);
        toolbar.inflateMenu(R.menu.example_menu);

        if (oldItem.getToggle() == false) {
            visibility();
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.item2){
                        memo.remove(oldItem);
                        notesadapter.notifyDataSetChanged();
                        saveData();
                        viewFlipper3.showPrevious();
                        toolbar.getMenu().clear();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            toggleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (toggleB.isChecked()) {
                        memo.set(index, new Notes(titleM.getText().toString(), detailM.getText().toString(), 2, true));
                        notesadapter.notifyDataSetChanged();
                        sorting();
                        saveData();
                        viewFlipper3.showPrevious();
                        Toast.makeText(getContext(), "Pin", Toast.LENGTH_SHORT).show();
                        toolbar.getMenu().clear();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                }
            });

            saveUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    memo.set(index, new Notes(titleM.getText().toString(), detailM.getText().toString(), 1, false));
                    notesadapter.notifyDataSetChanged();
                    sorting();
                    saveData();
                    viewFlipper3.showPrevious();
                    toolbar.getMenu().clear();
                    bottomNavigationView.setVisibility(View.VISIBLE);
                }
            });

            backB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewFlipper3.showPrevious();
                    toolbar.getMenu().clear();
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            });
        }

        if (oldItem.getToggle() == true) {
            visibility();
            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.item2){
                        memo.remove(oldItem);
                        notesadapter.notifyDataSetChanged();
                        saveData();
                        viewFlipper3.showPrevious();
                        toolbar.getMenu().clear();
                        bottomNavigationView.setVisibility(View.VISIBLE);
                    }
                    return false;
                }
            });
            toggleB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!toggleB.isChecked()) {
                        memo.remove(oldItem);
                        memo.add(new Notes(titleM.getText().toString(), detailM.getText().toString(), 1, false));
                        notesadapter.notifyDataSetChanged();
                        sorting();
                        saveData();
                        Toast.makeText(getContext(),"Unpin",Toast.LENGTH_SHORT).show();
                        viewFlipper3.showPrevious();
                        toolbar.getMenu().clear();
                        bottomNavigationView.setVisibility(View.VISIBLE);

                    }
                }
            });

            saveUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    memo.set(index, new Notes(titleM.getText().toString(), detailM.getText().toString(), 2, true));
                    notesadapter.notifyDataSetChanged();
                    sorting();
                    saveData();
                    viewFlipper3.showPrevious();
                    toolbar.getMenu().clear();
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            });

            backB.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewFlipper3.showPrevious();
                    toolbar.getMenu().clear();
                    bottomNavigationView.setVisibility(View.VISIBLE);

                }
            });
        }

    }


    public void visibility(){
        View blank = viewFlipper.getChildAt(R.id.map);
        View blank2 = viewFlipper3.getChildAt(R.id.map2);
        View page1 = viewFlipper.getChildAt(R.id.includedLayout1);
        View page2 = viewFlipper3.getChildAt(R.id.includedLayout2);
        if (viewFlipper.getCurrentView() == blank || viewFlipper.getCurrentView() == blank2){
            memoList.setVisibility(View.VISIBLE);
        }
        if (viewFlipper.getCurrentView() == page1 || viewFlipper.getCurrentView() == page2){
            memoList.setVisibility(View.INVISIBLE);
        }
    }
    private void sorting() {

        if (memo.size() > 0)
            Collections.sort(memo, (o1, o2) -> {
                int one;
                int two;
                one = o1.getInd();
                two = o2.getInd();

                return two - one;
            });

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
}
