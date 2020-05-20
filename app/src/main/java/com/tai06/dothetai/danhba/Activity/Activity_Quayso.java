package com.tai06.dothetai.danhba.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_Quayso extends AppCompatActivity {
    Button one, two, three, four, five, six, seven, eight, nine, zero, sao, thang;
    ImageView dialap_call, dialap_backspace;
    TextInputEditText dialap_edit;
    String onee = "1", twoo = "2", threee = "3", fourr = "4", fivee = "5", sixx = "6", sevenn = "7", eightt = "8", ninee = "9", zeroo = "0", saoo = "*", thangg = "#";
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__quayso);
        init();
        delete();
        event(one, onee);
        event(two, twoo);
        event(three, threee);
        event(four, fourr);
        event(five, fivee);
        event(six, sixx);
        event(seven, sevenn);
        event(eight, eightt);
        event(nine, ninee);
        event(sao, saoo);
        event(thang, thangg);
        event(zero, zeroo);
    }

    private void init() {
        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        sao = findViewById(R.id.sao);
        thang = findViewById(R.id.thang);
        dialap_call = findViewById(R.id.dialap_call);
        dialap_backspace = findViewById(R.id.backspace);
        dialap_edit = findViewById(R.id.dialap_edit);
        dbManager = new DBManager(this);
    }

    private void delete() {
        dialap_backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int lenght = dialap_edit.getText().length();
                if (lenght > 0) {
                    dialap_edit.getText().delete(lenght - 1, lenght);
                }
            }
        });
        dialap_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = dialap_edit.getText().toString().trim();
                if (ContextCompat.checkSelfPermission(Activity_Quayso.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Activity_Quayso.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    String dial = "tel:" + number;
                    startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }

                long mTime = System.currentTimeMillis();
                Date curDateTime = new Date(mTime);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String curDate = formatter.format(curDateTime);
                History history = new History();
                history.setNumber(number);
                history.setDate(curDate);
//                history.setId_db(info.getId());
                dbManager.AddHistory(history);
                dialap_edit.getText().clear();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void makePhone() {
        String number = dialap_edit.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(Activity_Quayso.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Quayso.this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }


    private void event(Button b, final String index) {
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = dialap_edit.getText().toString().trim();
                dialap_edit.setText(data + index);
            }
        });
    }
}
