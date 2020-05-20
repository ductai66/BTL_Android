package com.tai06.dothetai.danhba.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_Info extends AppCompatActivity {
    private final static int Request_call_sms = 1;
    private ImageView call, chat, setting, call_one, chat_one;
    public static TextView name_info, number_info;
    private Info info;
    private History history;
    private Object object;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__info);
        init();
        event();
//        setInfo();
        setObject();
    }

    private void init() {
        dbManager = new DBManager(this);
        call = findViewById(R.id.call);
        call_one = findViewById(R.id.call_one);
        chat = findViewById(R.id.chat);
        chat_one = findViewById(R.id.chat_one);
        setting = findViewById(R.id.setting);
        name_info = findViewById(R.id.name_info);
        number_info = findViewById(R.id.number_info);
    }

    private void setInfo() {
        info = (Info) getIntent().getSerializableExtra("info_history");
        name_info.setText(info.getName());
        number_info.setText(info.getNumber());
    }

    private void setObject(){
        object = getIntent().getSerializableExtra("info_history");
        if (object instanceof Info){
            info = (Info) object;
            name_info.setText(info.getName());
            number_info.setText(info.getNumber());
        }
        else if(object instanceof History){
            history = (History) object;
            name_info.setText(history.getName());
            number_info.setText(history.getNumber());
        }
    }

    private void event() {
        event_call(call);
        event_call(call_one);
        event_chat(chat);
        event_chat(chat_one);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Info.this, Activity_EditInfo.class);
                if (object instanceof Info){
                    info = (Info) object;
                    intent.putExtra("info_history", info);
                }
                else if(object instanceof History){
                    history = (History) object;
                    intent.putExtra("info_history", history);
                }
                //intent.putExtra("info_history", info);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void event_call(ImageView img) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object instanceof Info){
                    info = (Info) object;
                    long mTime = System.currentTimeMillis();
                    Date curDateTime = new Date(mTime);

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String curDate = formatter.format(curDateTime);

                    makePhone();
                    History history = new History();
                    history.setName(info.getName());
                    history.setNumber(info.getNumber());
                    history.setDate(curDate);
                    history.setId_db(info.getId());
                    dbManager.AddHistory(history);
                }else if(object instanceof History){
                    history = (History) object;
                    long mTime = System.currentTimeMillis();
                    Date curDateTime = new Date(mTime);

                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                    String curDate = formatter.format(curDateTime);

                    makePhone();
                    History history1 = new History();
                    history1.setName(history.getName());
                    history1.setNumber(history.getNumber());
                    history.setDate(curDate);
                    history1.setId_db(history.getId_db());
                    dbManager.AddHistory(history);
                }

            }
        });
    }

    private void event_chat(ImageView img) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeChat();
            }
        });
    }

    private void makePhone() {
        String number = number_info.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(Activity_Info.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Info.this, new String[]{Manifest.permission.CALL_PHONE}, Request_call_sms);
        } else {
            String dial = "tel:" + number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }
    }

    private void makeChat() {
        String number = number_info.getText().toString().trim();
        if (ContextCompat.checkSelfPermission(Activity_Info.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Activity_Info.this, new String[]{Manifest.permission.SEND_SMS}, Request_call_sms);
        } else {
            String dial = "sms:" + number;
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dial)));
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == Request_call_sms) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                makePhone();
                makeChat();
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void getInfo() {
        if (object instanceof Info){
            info = (Info) object;
            dbManager.UpdateLoadInfo(info);
        }
        else if(object instanceof History){
            history = (History) object;
            dbManager.UpdateLoadHistory(history);
        }
//        dbManager.UpdateLoadInfo(info);
    }

    @Override
    protected void onResume() {
        getInfo();
        super.onResume();
    }
}
