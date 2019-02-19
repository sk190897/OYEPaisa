package com.example.skambo.oyepaisa;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Calendar;

public class AccountActivity extends AppCompatActivity {


    Intent intent;
    TextView textView, amounttextview;
    Button editrecord;
    DatePicker datePicker;
    Intent intent2;
    String date, amountdate;
    Databasehelper db;
    double hasamount = 0;
    int month_id, day_id, year_id;

    Calendar calendar;

    String[] name_months = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        datePicker = findViewById(R.id.calendarView2);
        db = new Databasehelper(this);
        textView = findViewById(R.id.datetextview);
        amounttextview = findViewById(R.id.amount_month);

        textView = findViewById(R.id.datetextview);

        calendar = Calendar.getInstance();

        month_id = calendar.get(Calendar.MONTH);


        textView.setText("DATE : " + (calendar.get(Calendar.DAY_OF_MONTH)) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR));
        date = (calendar.get(Calendar.DAY_OF_MONTH)) + "-" + (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        amountdate = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.YEAR);
        datePicker.init(
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int month, int day) {

                        settext(year, month, day);
                        month_id = month;
                        day_id = day;
                        year_id = year;
                        amountdate = (month + 1) + "-" + year;
                        getdata();
                    }
                }
        );

        getdata();


    }

    @Override
    protected void onResume() {
        super.onResume();
        getdata();

    }

    public void getdata() {


        Cursor cursor = db.getdata2(amountdate);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {

                    hasamount = hasamount + Double.parseDouble(cursor.getString(2));
                } while (cursor.moveToNext());

            }
            amounttextview.setText(name_months[month_id] + " : " + hasamount + " ₹");
            hasamount = 0;

        }
    }


    public void settext(int year, int month, int day) {
        date = "" + day + "-" + (month + 1) + "-" + year;
        textView.setText("DATE " + date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.account_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.signoutmenu) {
            SignOut();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void SignOut() {
        FirebaseAuth.getInstance().signOut();
        intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void setrecord(View view) {

        intent2 = new Intent(this, Changerecord.class);
        intent2.putExtra("DATE", date);
        intent2.putExtra("day", day_id);
        intent2.putExtra("month", month_id);
        intent2.putExtra("year", year_id);
        startActivity(intent2);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                AccountActivity.this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }
}
