package com.tai06.dothetai.danhba.BinCycle;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

public class Activity_EditInfo_History extends AppCompatActivity {
    TextInputEditText edit_intputname_history,edit_intputnumber_history;
    Button btn_edit_huy_history,btn_edit_luu_history;
    History history;
    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__edit_info__history);
        init();
        event();
        setInfo();
    }

    private void init() {
        dbManager = new DBManager(this);
        edit_intputname_history = findViewById(R.id.edit_intputname_history);
        edit_intputnumber_history = findViewById(R.id.edit_intputnumber_history);
        btn_edit_luu_history = findViewById(R.id.btn_edit_luu_history);
        btn_edit_huy_history = findViewById(R.id.btn_edit_huy_history);
    }

    private void event(){
        btn_edit_luu_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateHistory();
                finish();
            }
        });
        btn_edit_huy_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setInfo(){
        history = (History) getIntent().getSerializableExtra("info_history");
        edit_intputname_history.setText(history.getName());
        edit_intputnumber_history.setText(history.getNumber());
    }

    private void UpdateHistory(){
        String edit_name = edit_intputname_history.getText().toString().trim();
        String edit_number = edit_intputnumber_history.getText().toString().trim();
        if (edit_name.equals("") || edit_number.equals("")){
            Toast.makeText(this, "Vui lòng nhập thông tin", Toast.LENGTH_SHORT).show();
        }
        else{
            history.getId_db();
            history.setName(edit_intputname_history.getText().toString().trim());
            history.setNumber(edit_intputnumber_history.getText().toString().trim());
            dbManager.UpdateHistoryTest(history);
            Info info = new Info();
//            int id = history.getId_db();
            String number = history.getNumber();
            info.setName(edit_intputname_history.getText().toString().trim());
            info.setNumber(edit_intputnumber_history.getText().toString().trim());
//            dbManager.UpdateInfoTest(info,id);
            dbManager.TestInfo(info,number);
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        }
    }

}
