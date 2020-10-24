package com.example.edu24.ui.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.edu24.R;
import com.example.edu24.model.Messages;

import java.util.ArrayList;

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessagesViewHolder> {
    private ArrayList<Messages> mMessagesArrayList;
    private Context mContext;

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_send, parent, false);
        return new MessagesViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, int position) {
        Messages message = mMessagesArrayList.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MessagesViewHolder extends RecyclerView.ViewHolder {
        ImageView profilePicture;
        TextView message;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.profilePicture = itemView.findViewById(R.id.profile_picture);
            this.message = itemView.findViewById(R.id.message);
        }

        public void bind(Messages message) {}
    }
}
