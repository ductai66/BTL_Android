package com.tai06.dothetai.danhba.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.tai06.dothetai.danhba.Activity.Activity_AddPhone;
import com.tai06.dothetai.danhba.Activity.Activity_Info;
import com.tai06.dothetai.danhba.Activity.Activity_Quayso;
import com.tai06.dothetai.danhba.Adapter.HistoryAdapter;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.R;

import java.util.ArrayList;
import java.util.List;

public class Frag_HistoryCall extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private ImageView quayso;
    private DBManager dbManager;
    private ArrayList<History> lstHistory;

    public Frag_HistoryCall() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.from(container.getContext()).inflate(R.layout.fragment_frag__history_call, container, false);
        recyclerView = v.findViewById(R.id.recyl_historyCall);
        quayso = v.findViewById(R.id.quayso);
        dbManager = new DBManager(getContext());
        lstHistory = new ArrayList<>();
        event();
        return v;
    }

    private void event(){
        quayso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Activity_Quayso.class);
                startActivity(intent);
            }
        });
    }

    private void setAdapter(List<History> list) {
        HistoryAdapter historyAdapter = new HistoryAdapter(list, Frag_HistoryCall.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(historyAdapter);
    }

    private void getAllhistory() {
        lstHistory.clear();
        lstHistory.addAll(dbManager.getHistory());
        setAdapter(lstHistory);
    }

    public void showInfoHistory(History history){
//        Intent intent = new Intent(getActivity(), Activity_Info_History.class);
        Intent intent = new Intent(getActivity(), Activity_Info.class);
        intent.putExtra("info_history",history);
        startActivity(intent);
    }

    public void InsertInfo(History history){
        Intent intent = new Intent(getActivity(), Activity_AddPhone.class);
        intent.putExtra("insertinfo",history);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        getAllhistory();
        super.onResume();
    }
}
