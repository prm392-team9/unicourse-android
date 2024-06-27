package com.example.unicourse.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.unicourse.R;
import com.example.unicourse.adapters.ChatAdapter;
import com.example.unicourse.adapters.ChatViewModel;
import com.example.unicourse.models.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private EditText editTextMessage;
    private Button buttonSend;

    private Socket mSocket;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        chatViewModel.getChatRoomDetail("65ebe4d2d0cb58ef9cb250cc").observe(getViewLifecycleOwner(), chatRoomDetail -> {
            if (chatRoomDetail != null) {
                chatAdapter.updateMessages(chatRoomDetail.getMessages());
            }
        });

        buttonSend.setOnClickListener(v -> {
            sendMessage();
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve userId from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);
        String roomId = "65ebe4d2d0cb58ef9cb250cc"; // Room ID

        try {
            mSocket = IO.socket("http://10.0.2.2:4040");
            mSocket.connect();
            mSocket.on("newMessage", onNewMessage);

            // Emit joinRoom event
            if (userId != null) {
                JSONObject joinRoomData = new JSONObject();
                joinRoomData.put("roomId", roomId);
                joinRoomData.put("userId", userId);
                mSocket.emit("joinRoom", joinRoomData);
            }
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        String messageText = editTextMessage.getText().toString().trim();
        if (!messageText.isEmpty()) {
            // Assuming the server expects a JSON object with the message details
            JSONObject message = new JSONObject();
            try {
                message.put("message", messageText);
                mSocket.emit("sendMessage", message);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            if (getActivity() != null) {
                getActivity().runOnUiThread(() -> {
                    JSONObject data = (JSONObject) args[0];
                    try {
                        JSONObject listMessage = data.getJSONObject("listMessage");
                        JSONArray messagesArray = listMessage.getJSONArray("messages");
                        List<Message> newMessages = new ArrayList<>();
                        for (int i = 0; i < messagesArray.length(); i++) {
                            JSONObject msg = messagesArray.getJSONObject(i);
                            Message message = new Message();
                            // Assuming Message has a method to populate from JSON
                            message.fromJson(msg);
                            newMessages.add(message);
                        }
                        chatAdapter.updateMessages(newMessages);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        mSocket.disconnect();
        mSocket.off("newMessage", onNewMessage);
    }
}
