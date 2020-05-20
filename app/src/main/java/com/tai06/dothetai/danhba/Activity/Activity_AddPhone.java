package com.tai06.dothetai.danhba.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

import static com.tai06.dothetai.danhba.Fragment.Frag_ListPhone.lstInfo;

public class Activity_AddPhone extends AppCompatActivity {
    private EditText edit_inputnumber,edit_inputname;
    private Button btn_luu,btn_huy;
    private DBManager dbManager;
    private History history;
    private Object object;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__add_phone);
        init();
        getObject();
        event();
    }

    private void init() {
        dbManager = new DBManager(this);
        edit_inputname = findViewById(R.id.edit_intputname);
        edit_inputnumber = findViewById(R.id.edit_intputnumber);
        btn_luu = findViewById(R.id.btn_luu);
        btn_huy = findViewById(R.id.btn_huy);
    }

    private void getObject(){
        object = getIntent().getSerializableExtra("insertinfo");
        if (object instanceof History){
            history = (History) object;
            edit_inputnumber.setText(history.getNumber());
        }
    }

    private void event(){
        btn_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (object instanceof History){
                    InsertInfo();
                    startActivity(new Intent(Activity_AddPhone.this,MainActivity.class));
                }
                else {
                    if (kiemtraNumber() == true){
                        Toast.makeText(Activity_AddPhone.this, "Số điện thoại đã tồn tại!", Toast.LENGTH_SHORT).show();
                    }else if(kiemtraNumber() == false){
                        AddInfo();
                        startActivity(new Intent(Activity_AddPhone.this,MainActivity.class));
                    }
                }
            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_AddPhone.this,MainActivity.class));
            }
        });
    }

    private boolean kiemtraNumber(){
        boolean check = true;
        String number = edit_inputnumber.getText().toString().trim();
        for (int i=0;i<lstInfo.size();i++){
            if (lstInfo.get(i).getNumber().equals(number)) {
                Toast.makeText(this, "not ok", Toast.LENGTH_SHORT).show();
                return true;
            }
            else{
                return false;
            }
        }
        return check;
    }

    private void AddInfo(){
        String inputname = edit_inputname.getText().toString().trim();
        String inputnumber = edit_inputnumber.getText().toString().trim();
        if (inputname.equals("")||inputnumber.equals("")){
            Toast.makeText(this, "Vui lòng nhập thông tin !", Toast.LENGTH_SHORT).show();
        }
        else{
            Info info = new Info();
            info.setName(inputname);
            info.setNumber(inputnumber);
            dbManager.AddInfo(info);
        }
    }

    private void InsertInfo(){
        String inputname = edit_inputname.getText().toString().trim();
        String inputnumber = edit_inputnumber.getText().toString().trim();
        if (inputname.equals("")||inputnumber.equals("")){
            Toast.makeText(this, "Vui lòng nhập thông tin !", Toast.LENGTH_SHORT).show();
        }
        else{
            Info info = new Info();
            info.setName(inputname);
            info.setNumber(inputnumber);
            dbManager.AddInfo(info);
//            history.getId();
            history.setName(inputname);
            history.setNumber(inputnumber);
//            dbManager.UpdateHistoryTest(history);
            dbManager.TestHistory(history,inputnumber);
        }
    }
}
