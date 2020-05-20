package com.tai06.dothetai.danhba.Fragment;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tai06.dothetai.danhba.Activity.Activity_AddPhone;
import com.tai06.dothetai.danhba.Activity.Activity_EditInfo;
import com.tai06.dothetai.danhba.Activity.Activity_Info;
import com.tai06.dothetai.danhba.Adapter.InfoAdapter;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Frag_ListPhone extends Fragment {

    View v;
    private DBManager dbManager;
    private RecyclerView recyclerView;
    public static ArrayList<Info> lstInfo;
    private InfoAdapter infoAdapter;
    private ImageView add_phone,search_view;
    public static EditText edit_search_view;

    public Frag_ListPhone() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        lstInfo = new ArrayList<>();
        v = inflater.from(container.getContext()).inflate(R.layout.fragment_frag__list_phone, container, false);
        dbManager = new DBManager(getContext());
        init();
        event();
        return v;
    }

    private void init() {
        recyclerView = v.findViewById(R.id.recyl_lstPhone);
        add_phone = v.findViewById(R.id.add_phone);
        search_view = v.findViewById(R.id.search_view);
        edit_search_view = v.findViewById(R.id.edit_search_view);
    }

    private void event(){
        add_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), Activity_AddPhone.class));
            }
        });
        search_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timkiem();
                closeKeyboard();
            }
        });
    }

    private void sort(){
        Collections.sort(lstInfo, new Comparator<Info>() {
            @Override
            public int compare(Info o1, Info o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }
    private void closeKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view!=null){
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    private void timkiem() {
        lstInfo.clear();
        lstInfo.addAll(dbManager.getSearch());
        setAdapter(lstInfo);
    }

    private void setAdapter(List<Info> list) {
        InfoAdapter infoAdapter = new InfoAdapter(list, Frag_ListPhone.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(infoAdapter);
    }

    public void getAllInfo() {
        lstInfo.clear();
        lstInfo.addAll(dbManager.getInfo());
        setAdapter(lstInfo);
        sort();
    }

    public void showPhone(Info info){
        Intent intent = new Intent(getActivity(), Activity_Info.class);
        intent.putExtra("info_history",info);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        getAllInfo();
        super.onResume();
    }
}
