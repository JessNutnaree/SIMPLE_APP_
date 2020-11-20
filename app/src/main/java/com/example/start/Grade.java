package com.example.start;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.gson.Gson;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ast.Scope;
import org.xml.sax.InputSource;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;


/**
 * A simple {@link Fragment} subclass.
 */
public class Grade extends Fragment implements View.OnClickListener {
    private EditText inputtext;
    private TextView result;
    private Button but0;
    private Button but1;
    private Button but2;
    private Button but3;
    private Button but4;
    private Button but5;
    private Button but6;
    private Button but7;
    private Button but8;
    private Button but9;
    private Button butclear;
    private Button butbracet;
    private Button butplus;
    private Button butmulti;
    private Button butdivi;
    private Button butminus;
    private Button butdot;
    private Button butequal;
    private Button butpercent;
    private Button butzero;
    private ImageButton delete;
    String process;
    boolean checkBracket = false;

    public Grade() {

        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_grade, container, false);
        inputtext = v.findViewById(R.id.editText);
        inputtext.setShowSoftInputOnFocus(false);
        inputtext.setSelection(inputtext.getText().length());
        result = v.findViewById(R.id.textView5);
        but0 = v.findViewById(R.id.button17);
        but1 = v.findViewById(R.id.button8);
        but2 = v.findViewById(R.id.button10);
        but3 = v.findViewById(R.id.button19);
        but4 = v.findViewById(R.id.button5);
        but5 = v.findViewById(R.id.button9);
        but6 = v.findViewById(R.id.button13);
        but7 = v.findViewById(R.id.button2);
        but8 = v.findViewById(R.id.button6);
        but9 = v.findViewById(R.id.button7);
        butclear = v.findViewById(R.id.button14);
        butbracet = v.findViewById(R.id.button3);
        butplus = v.findViewById(R.id.button20);
        butminus = v.findViewById(R.id.button15);
        butdivi = v.findViewById(R.id.button11);
        butmulti = v.findViewById(R.id.button12);
        butequal = v.findViewById(R.id.button21);
        butpercent = v.findViewById(R.id.button4);
        butdot = v.findViewById(R.id.button16);
        butzero = v.findViewById(R.id.button18);
        delete = v.findViewById(R.id.imageButton);

        setOnClick();



        return v;
    }

    private void setOnClick () {
        but0.setOnClickListener(this);
        but1.setOnClickListener(this);
        but2.setOnClickListener(this);
        but3.setOnClickListener(this);
        but4.setOnClickListener(this);
        but5.setOnClickListener(this);
        but6.setOnClickListener(this);
        but7.setOnClickListener(this);
        but8.setOnClickListener(this);
        but9.setOnClickListener(this);
        butclear.setOnClickListener(this);
        butbracet.setOnClickListener(this);
        butplus.setOnClickListener(this);
        butminus.setOnClickListener(this);
        butdivi.setOnClickListener(this);
        butmulti.setOnClickListener(this);
        butequal.setOnClickListener(this);
        butpercent.setOnClickListener(this);
        butdot.setOnClickListener(this);
        butzero.setOnClickListener(this);
        delete.setOnClickListener(this);
        }

    @Override
    public void onClick(View v) {
        int Id = v.getId();
        switch (Id) {
            case R.id.button17:
                append("0");
                break;
            case R.id.button8:
                append("1");
                break;
            case R.id.button10:
                append("2");
                break;
            case R.id.button19:
                append("3");
                break;
            case R.id.button5:
                append("4");
                break;
            case R.id.button9:
                append("5");
                break;
            case R.id.button13:
                append("6");
                break;
            case R.id.button2:
                append("7");
                break;
            case R.id.button6:
                append("8");
                break;
            case R.id.button7:
                append("9");
                break;
            case R.id.button14:
                clear();
                break;
            case R.id.button3:
                if (checkBracket) {
                    process = inputtext.getText().toString();
                    inputtext.setText(process + ")");
                    checkBracket = false;
                } else {
                    process = inputtext.getText().toString();
                    inputtext.setText(process + "(");
                    checkBracket = true;
                }
                break;
            case R.id.button18:
                append("00");
                break;
            case R.id.button20:
                if (endsWithOperator())
                    replace("+");
                else
                    append("+");
                break;
            case R.id.button15:
                if (endsWithOperator())
                    replace("-");
                else
                    append("-");
                break;
            case R.id.button11:
                if (endsWithOperator())
                    replace("÷");
                else
                    append("÷");
                break;
            case R.id.button12:
                if (endsWithOperator())
                    replace("×");
                else
                    append("×");
                break;
            case R.id.button21:
                if (inputtext.length() > 0){
                    process = inputtext.getText().toString();
                process = process.replaceAll("×", "*");
                process = process.replaceAll("%", "/100");
                process = process.replaceAll("÷", "/");

                Context rhino = Context.enter();
                rhino.setOptimizationLevel(-1);
                String finalResult = "";
                try {
                    Scriptable scriptable = rhino.initSafeStandardObjects();
                    finalResult = rhino.evaluateString(scriptable, process, "javascript", 1, null).toString();
                } catch (Exception e) {
                    finalResult = "0";
                }
                inputtext.setText(finalResult);
                result.setText(finalResult);
                inputtext.setSelection(inputtext.getText().length());
                }

                else
                    inputtext.setText("");
                break;
            case R.id.button4:
                append("%");
                break;
            case R.id.button16:
                if (inputtext.length() <= 0)
                append("0.");
                else
                    append(".");
                break;
            case R.id.imageButton:
                delete();
                break;
        }

    }

    private Boolean stateError=false;

    private boolean endsWithOperator(){
        return getInput().endsWith("+") || getInput().endsWith("-")|| getInput().endsWith("×")|| getInput().endsWith("÷") || getInput().endsWith("%");
    }
    private void replace(String str){
        inputtext.getText().replace(getInput().length()-1,getInput().length(),str);

    }

    private void clear() {
        inputtext.getText().clear();
        result.setText("");
    }

    private void append(String str) {
        this.inputtext.getText().append(str);
    }
    private void delete(){
        if (!isEmpty()){
            this.inputtext.getText().delete(getInput().length()-1,getInput().length());
        }
    }
    private String getInput(){
        return this.inputtext.getText().toString();
    }
    private boolean isEmpty(){
        return this.getInput().isEmpty();
    }

}




