package com.example.unicourse.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.models.chatroom.Message;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Message> messages;

    public ChatAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Message message = messages.get(position);
        holder.bind(message);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public void updateMessages(List<Message> newMessages, RecyclerView recyclerView) {
        this.messages = newMessages;
        notifyDataSetChanged();
        if (!messages.isEmpty()) {
            recyclerView.scrollToPosition(messages.size() - 1);
        }
    }

    static class ChatViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewMessage;
        private TextView textViewUserName;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewMessage = itemView.findViewById(R.id.textViewMessage);
            textViewUserName = itemView.findViewById(R.id.textViewUserName);
        }

        public void bind(Message message) {
            textViewMessage.setText(message.getMessage());
            if (message.getUser() != null) {
                textViewUserName.setText(message.getUser().getFullName());
            } else {
                textViewUserName.setText("Unknown User"); // or handle it in a way that fits your app
            }
        }
    }
}