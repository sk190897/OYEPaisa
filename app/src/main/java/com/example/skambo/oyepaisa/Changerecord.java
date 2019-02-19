package com.example.skambo.oyepaisa;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;

public class Changerecord extends AppCompatActivity {

    String date, description, amount;
    TextView norecord;
    Intent intent2;
    ActionBar actionBar;
    Activity activity;
    CustomDialog customDialog;
    ListView listView;
    Customadapter customadapter;
    ImageButton deletebutton;
    Databasehelper myDB;
    AdView mAdView;
    Toolbar toolbar;

    String[] name_months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    ArrayList<Listitems> reclist;
    boolean del = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changerecord);
        MobileAds.initialize(this, "ca-app-pub-9576259107695089~1630354670");

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        norecord = findViewById(R.id.norecord);
        AccountActivity acc = new AccountActivity();
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        reclist = new ArrayList<>();
        listView = findViewById(R.id.mylistview);
        deletebutton = findViewById(R.id.delete_button);
        myDB = new Databasehelper(this);
        date = getIntent().getStringExtra("DATE");
        customDialog = new CustomDialog(this);
        toolbar.setTitle("RECORDS FOR : " + date);

        customadapter = new Customadapter(this, reclist);
        getdata(date);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                popup();


            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                updateview();
            }
        });

        if (reclist.isEmpty())
            norecord.setVisibility(View.VISIBLE);

    }

    public void deleterecord(View view) {
        del = true;
        final int position = listView.getPositionForView(view);
        //Toast.makeText(this,""+position+""+reclist.get(position).getMdescription()+reclist.get(position).getMamount(),Toast.LENGTH_SHORT).show();

        int delrow = myDB.deletedata(date, reclist.get(position).getMdescription(), reclist.get(position).getMamount());
        reclist.remove(position);
        if (reclist != null) {
            norecord.setVisibility(View.INVISIBLE);
            updateview();
        }
        if (reclist.isEmpty())
            norecord.setVisibility(View.VISIBLE);
    }

    public void updateview() {

        if (customDialog.click) {
            customDialog.click = false;

            description = customDialog.desc;
            amount = customDialog.amount1;
            if (description != null && amount != null) {
                reclist.add(new Listitems(description, amount));
                customDialog.desc = null;
                customDialog.amount1 = null;
                adddata(date, description, amount);
                listView.setAdapter(customadapter);
            }
        }

        if (del) {
            del = false;
            listView.setAdapter(customadapter);

        }


    }

    public void popup() {
        customDialog.show();


    }

    public void adddata(String date, String description, String amount) {

        boolean isinserted = myDB.insertdata(date, description, amount);

    }

    public void getdata(String date) {
        String description1;
        String amount1;
        if (date.equals(String.valueOf(""))) {
            return;
        }

        Cursor cursor = myDB.getdata(date);

        if (cursor != null) {
            del = true;
            if (cursor.moveToFirst()) {
                do {
                    description1 = cursor.getString(1);
                    amount1 = cursor.getString(2);
                    reclist.add(new Listitems(description1, amount1));
                } while (cursor.moveToNext());
            }
            updateview();
        }
    }

}
