package com.tai06.dothetai.danhba.BinCycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.R;

public class Activity_Info_History extends AppCompatActivity {
    private final static int Request_call_sms_history = 1;
    private ImageView call_history, chat_history, setting_history, call_one_history, chat_one_history;
    public static TextView name_info_history, number_info_history;
    private History history;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__info__history);
        init();
        event();
        setHistory();
    }

    private void init() {
        dbManager = new DBManager(this);
        call_history = findViewById(R.id.call_history);
        chat_history = findViewById(R.id.chat_history);
        setting_history = findViewById(R.id.setting_history);
        call_one_history = findViewById(R.id.call_one_history);
        chat_one_history = findViewById(R.id.chat_one_history);
        name_info_history = findViewById(R.id.name_info_history);
        number_info_history = findViewById(R.id.number_info_history);
    }

    private void setHistory(){
        history = (History) getIntent().getSerializableExtra("info_history");
        name_info_history.setText(history.getName());
        number_info_history.setText(history.getNumber());
    }

    private void event() {
        event_call_history(call_history);
        event_call_history(call_one_history);
        event_chat_history(chat_history);
        event_chat_history(chat_one_history);
        setting_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Info_History.this,Activity_EditInfo_History.class);
                intent.putExtra("info_history",history);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void event_call_history(ImageView img){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhone();
            }
        });
    }

    private void makePhone() {
        String number = number_info_history.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(Activity_Info_History.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Info_History.this, new String[]{Manifest.permission.CALL_PHONE}, Request_call_sms_history);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    private void event_chat_history(ImageView img){
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeChat();
            }
        });
    }

    private void makeChat() {
        String number = number_info_history.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(Activity_Info_History.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Info_History.this, new String[]{Manifest.permission.SEND_SMS}, Request_call_sms_history);
        } else {
            String dial = "sms:" + number;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dial)));
        }
    }

    private void getInfoHistory(){
        dbManager.UpdateLoadHistory(history);
    }

    @Override
    protected void onResume() {
        getInfoHistory();
        super.onResume();
    }
}
