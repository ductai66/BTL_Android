package com.tai06.dothetai.danhba.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.tai06.dothetai.danhba.Activity.MainActivity;
import com.tai06.dothetai.danhba.DataBase.DBManager;
import com.tai06.dothetai.danhba.Fragment.Frag_ListPhone;
import com.tai06.dothetai.danhba.Object.Info;
import com.tai06.dothetai.danhba.R;

import java.util.List;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.ViewHolder> {

    private List<Info> lstInfo;
    private Fragment context;

    public InfoAdapter(List<Info> lstInfo, Fragment context) {
        this.lstInfo = lstInfo;
        this.context = context;
    }

    @NonNull
    @Override
    public InfoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_lstphone, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.ViewHolder holder, int position) {
        Info info = lstInfo.get(position);
        holder.txt_name.setText(info.getName());
    }

    @Override
    public int getItemCount() {
        return lstInfo.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_name = itemView.findViewById(R.id.txt_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Info info = lstInfo.get(getAdapterPosition());
                    ((Frag_ListPhone)context).showPhone(info);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    final Dialog dialog = new Dialog(context.getActivity());
                    final DBManager dbManager = new DBManager(context.getActivity());
                    dialog.setContentView(R.layout.deleta_dialog);
                    dialog.setCanceledOnTouchOutside(false);

                    Button xoa_dialog = dialog.findViewById(R.id.xoa_dialog);
                    Button huy_dialog = dialog.findViewById(R.id.huy_dialog);

                    xoa_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Info info = lstInfo.get(getAdapterPosition());
                            int id = Integer.parseInt(String.valueOf(info.getId()));
                            dbManager.Delete(id);
                            dialog.dismiss();
                            Toast.makeText(context.getActivity(), info.getName()+"đã xóa khỏi danh bạ", Toast.LENGTH_SHORT).show();
                            ((Frag_ListPhone) context).getAllInfo();
                        }
                    });

                    huy_dialog.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                    return true;
                }
            });
        }
    }
}
