package com.tai06.dothetai.danhba.Adapter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tai06.dothetai.danhba.Activity.Activity_Info;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Fragment.Frag_HistoryCall;
import com.tai06.dothetai.danhba.Object.History;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.tai06.dothetai.danhba.Fragment.Frag_ListPhone.lstInfo;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<History> lstHistory;
    private Fragment context;
    DBManager dbManager;

    public HistoryAdapter(List<History> lstHistory, Fragment context) {
        this.lstHistory = lstHistory;
        this.context = context;
    }

    @NonNull
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_historycall, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryAdapter.ViewHolder holder, final int position) {
        final History history = lstHistory.get(position);
        holder.name_history.setText(history.getName());
        holder.number_history.setText(history.getNumber());
        holder.date_history.setText(history.getDate());
        holder.call_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = history.getNumber();
                if (ContextCompat.checkSelfPermission(context.getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(context.getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    String dial = "tel:" + number;
                    context.getActivity().startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                }

                long mTime = System.currentTimeMillis();
                Date curDateTime = new Date(mTime);

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String curDatea = formatter.format(curDateTime);

                History history1 = new History();
                dbManager  = new DBManager(context.getActivity());
                history1.setName(history.getName());
                history1.setNumber(history.getNumber());
                history.setDate(curDatea);
                history1.setId_db(history.getId_db());
                dbManager.AddHistory(history);
            }
        });
        holder.person_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                History history = lstHistory.get(position);
                for (int i=0;i<lstInfo.size();i++){
                    if (lstInfo.get(i).getNumber().equals(history.getNumber())){
                        ((Frag_HistoryCall)context).showInfoHistory(history);
                        Toast.makeText(context.getActivity(), "ok", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else{
                        ((Frag_HistoryCall)context).InsertInfo(history);
                        Toast.makeText(context.getActivity(), "Cần tạo mới thông tin!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return lstHistory.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name_history,number_history,date_history;
        ImageView call_history,person_history;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_history = itemView.findViewById(R.id.name_history);
            number_history = itemView.findViewById(R.id.number_history);
            date_history = itemView.findViewById(R.id.date_history);
            call_history = itemView.findViewById(R.id.call_history);
            person_history = itemView.findViewById(R.id.person_history);
        }
    }
}
