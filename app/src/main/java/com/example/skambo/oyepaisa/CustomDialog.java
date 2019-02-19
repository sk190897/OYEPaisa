package com.example.skambo.oyepaisa;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;


public class CustomDialog extends Dialog {

    Activity activity;
    Dialog dialog;
    Changerecord changerecord;
    Button yes, no;
    String desc = null, amount1 = null;
    EditText des, amount;
    ArrayList<Listitems> reclist1;
    boolean click = false;


    public CustomDialog(Activity a) {

        super(a);
        this.activity = a;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_customdialog);
        reclist1 = new ArrayList<>();
        changerecord = new Changerecord();
        yes = findViewById(R.id.add);
        no = findViewById(R.id.cancel);
        des = findViewById(R.id.description);
        amount = findViewById(R.id.amount);


        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                desc = des.getText().toString();
                amount1 = amount.getText().toString();
                if (desc.equals(String.valueOf("")))
                    des.setError("Enter description");
                else if (amount1.equals(String.valueOf("")))
                    amount.setError("Enter amount");
                else if (Double.parseDouble(amount1) > 1000000) {
                    amount.setError("Amount too large");
                } else if (desc.length() > 26)
                    des.setError("Max characters :26");
                else {
                    click = true;
                    reclist1.add(new Listitems(desc, amount1));
                    des.setText(null);
                    amount.setText(null);
                    dismiss();
                }

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }
}
