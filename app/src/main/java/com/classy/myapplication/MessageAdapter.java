package com.classy.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.classy.myapplication.Object.Message;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder>{
    private ArrayList<Message> listdata;

    // RecyclerView recyclerView;
    public MessageAdapter(ArrayList<Message> listdata) {
        this.listdata = listdata;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.message_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //create a date string.

        holder.message_LBL_DATA.setText(listdata.get(position).getDate());
        holder.message_LBL_message.setText(listdata.get(position).getMessage());

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView message_LBL_DATA;
        public TextView message_LBL_message;
        public LinearLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.message_LBL_DATA = (TextView) itemView.findViewById(R.id.message_LBL_DATA);
            this.message_LBL_message = (TextView) itemView.findViewById(R.id.message_LBL_message);
            relativeLayout = (LinearLayout)itemView.findViewById(R.id.message_list_id);
        }
    }
}