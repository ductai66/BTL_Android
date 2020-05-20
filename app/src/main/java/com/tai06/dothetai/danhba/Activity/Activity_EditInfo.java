package com.tai06.dothetai.danhba.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

public class Activity_EditInfo extends AppCompatActivity {
    private TextInputEditText edit_inputname,edit_inputnumber;
    private Button btn_edit_luu,btn_edit_huy;
    Info info;
    History history;
    Object object;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_info);
        init();
        event();
//        setInfo();
        setObject();
    }

    private void init() {
        dbManager = new DBManager(this);
        btn_edit_luu = findViewById(R.id.btn_edit_luu);
        btn_edit_huy = findViewById(R.id.btn_edit_huy);
        edit_inputname = findViewById(R.id.edit_intputname);
        edit_inputnumber = findViewById(R.id.edit_intputnumber);
    }

    private void setInfo(){
        info = (Info) getIntent().getSerializableExtra("info_history");
        edit_inputname.setText(info.getName());
        edit_inputnumber.setText(info.getNumber());
    }

    private void setObject(){
        object = getIntent().getSerializableExtra("info_history");
        if (object instanceof Info){
            info = (Info) object;
            edit_inputname.setText(info.getName());
            edit_inputnumber.setText(info.getNumber());
        }
        else if(object instanceof History){
            history = (History) object;
            edit_inputname.setText(history.getName());
            edit_inputnumber.setText(history.getNumber());
        }
    }

    private void event() {
        btn_edit_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object instanceof Info){
                    UpdateInfo();
                }else if(object instanceof History){
                    UpdateHistory();
                }
                finish();
            }
        });
        btn_edit_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void UpdateInfo() {
        String edit_name = edit_inputname.getText().toString().trim();
        String edit_number = edit_inputnumber.getText().toString().trim();
        if (edit_name.equals("") || edit_number.equals("")){
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
        }
        else{
            info.getId();
            info.setName(edit_inputname.getText().toString().trim());
            info.setNumber(edit_inputnumber.getText().toString().trim());
            dbManager.UpdateInfo(info);
            History history = new History();
//            int id = info.getId();
            String number = info.getNumber();
            history.setName(edit_inputname.getText().toString().trim());
            history.setNumber(edit_inputnumber.getText().toString().trim());
//            dbManager.UpdateHistory(history,id);
            dbManager.TestHistory(history,number);
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        }
    }
    private void UpdateHistory(){
        String edit_name = edit_inputname.getText().toString().trim();
        String edit_number = edit_inputnumber.getText().toString().trim();
        if (edit_name.equals("") || edit_number.equals("")){
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
        }
        else{
            history.getId();
            history.setName(edit_inputname.getText().toString().trim());
            history.setNumber(edit_inputnumber.getText().toString().trim());
            dbManager.TestHistory(history,edit_number);
            Info info = new Info();
//            int id = info.getId();
            String number = history.getNumber();
            info.setName(edit_inputname.getText().toString().trim());
            info.setNumber(edit_inputnumber.getText().toString().trim());
//            dbManager.UpdateHistory(history,id);
            dbManager.TestInfo(info,number);
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
