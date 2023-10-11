package com.example.mychatgptandroid.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mychatgptandroid.R;
import com.example.mychatgptandroid.model.Message;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {
    Context context;
    List<Message>  messages;

    public MessageAdapter(Context context, List<Message> messages) {
        this.context = context;
        this.messages = messages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_chat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Message message1 = messages.get(position);
        if(message1.getSend_id() == 0){
            holder.layout2.setVisibility(View.VISIBLE);
            holder.layout1.setVisibility(View.GONE);
            holder.tv_right.setText(message1.getContent());
        }else {
            holder.layout2.setVisibility(View.GONE);
            holder.layout1.setVisibility(View.VISIBLE);
            holder.tv_left.setText(message1.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout layout1, layout2;
        TextView tv_left, tv_right;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            layout1 = itemView.findViewById(R.id.layout1);
            layout2 = itemView.findViewById(R.id.layout2);
            tv_left = itemView.findViewById(R.id.tv_chat_left);
            tv_right = itemView.findViewById(R.id.tv_chat_right);
        }
    }
}
