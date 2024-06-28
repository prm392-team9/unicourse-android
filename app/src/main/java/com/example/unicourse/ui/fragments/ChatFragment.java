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
import android.widget.Toast;

import com.example.unicourse.R;
import com.example.unicourse.adapters.ChatAdapter;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.chatroom.ChatRoomDetail;
import com.example.unicourse.models.chatroom.ChatRoomSendMessageResponse;
import com.example.unicourse.services.ChatRoomApiService;
import com.example.unicourse.services.RetrofitClient;
import com.example.unicourse.viewmodels.ChatViewModel;
import com.example.unicourse.models.chatroom.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatFragment extends Fragment {
    private ChatViewModel chatViewModel;
    private RecyclerView recyclerView;
    private ChatAdapter chatAdapter;
    private EditText editTextMessage;
    private Button buttonSend;

    private Socket mSocket;
    private boolean isSendingMessage = false;
    private String chatRoomId = "65ebe4d2d0cb58ef9cb250cc";
    private ChatRoomApiService chatRoomService;
    private String userId = null;
    private String accessToken = null;
    public ChatRoomDetail currentChatRoomDetail;

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

        chatViewModel.getChatRoomDetail(chatRoomId).observe(getViewLifecycleOwner(), chatRoomDetail -> {
            if (chatRoomDetail != null) {
                currentChatRoomDetail = chatRoomDetail;
                chatAdapter.updateMessages(chatRoomDetail.getMessages(), recyclerView);
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

        // Retrieve userId and accessToken from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        accessToken = sharedPreferences.getString("access_token", null);

        // Initialize the chatRoomService
        chatRoomService = RetrofitClient.getClient(ApiConstants.BASE_URL, accessToken).create(ChatRoomApiService.class);

        try {
            mSocket = IO.socket(ApiConstants.BASE_SOCKET_URL);
            mSocket.connect();
            mSocket.on("newMessage", onNewMessage);

            // Emit joinRoom event
            if (userId != null) {
                JSONObject joinRoomData = new JSONObject();
                joinRoomData.put("roomId", chatRoomId);
                joinRoomData.put("userId", userId);
                mSocket.emit("joinRoom", joinRoomData);
            }
        } catch (URISyntaxException | JSONException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage() {
        if (isSendingMessage) return; // Prevent multiple sends
        isSendingMessage = true;

        String messageText = editTextMessage.getText().toString().trim();
        if (chatRoomId != null && !messageText.isEmpty() && currentChatRoomDetail != null) {
            ChatRoomApiService.SendMessageRequest request = new ChatRoomApiService.SendMessageRequest(chatRoomId, messageText);
            Call<ChatRoomSendMessageResponse> call = chatRoomService.sendMessage(request);
            call.enqueue(new Callback<ChatRoomSendMessageResponse>() {
                @Override
                public void onResponse(Call<ChatRoomSendMessageResponse> call, Response<ChatRoomSendMessageResponse> response) {
                    if (response.isSuccessful() && response.body() != null) {
                        List<Message> newMessages = response.body().getData();
                        chatAdapter.updateMessages(newMessages, recyclerView);
                        editTextMessage.setText("");

                        if (userId != null) {
                            try {
                                JSONArray messagesJsonArray = new JSONArray();
                                for (Message message : newMessages) {
                                    JSONObject messageJson = new JSONObject();
                                    messageJson.put("user", new JSONObject()
                                            .put("_id", message.getUser().get_id())
                                            .put("email", message.getUser().getEmail())
                                            .put("fullName", message.getUser().getFullName())
                                            .put("profileName", message.getUser().getProfileName())
                                            .put("profile_image", message.getUser().getProfileImage())
                                    );
                                    messageJson.put("message", message.getMessage());
                                    messageJson.put("date", message.getDate());
                                    messageJson.put("status", message.getStatus());
                                    messageJson.put("_id", message.get_id());
                                    messagesJsonArray.put(messageJson);
                                }

                                JSONObject listMessage = new JSONObject();
                                listMessage.put("_id", currentChatRoomDetail.getId());
                                listMessage.put("name", currentChatRoomDetail.getName());
                                listMessage.put("status", currentChatRoomDetail.getStatus());
                                listMessage.put("memberCount", currentChatRoomDetail.getMemberCount());
                                listMessage.put("thumbnail", currentChatRoomDetail.getThumbnail());
                                listMessage.put("created_at", currentChatRoomDetail.getCreatedAt());
                                listMessage.put("updated_at", currentChatRoomDetail.getUpdatedAt());
                                listMessage.put("__v", 517); // Assuming you have this version number from somewhere
                                listMessage.put("messages", messagesJsonArray);

                                JSONArray usersArray = new JSONArray();
                                for (com.example.unicourse.models.authentication.User user : currentChatRoomDetail.getUsers()) {
                                    JSONObject userJson = new JSONObject();
                                    userJson.put("_id", user.get_id());
                                    userJson.put("email", user.getEmail());
                                    userJson.put("fullName", user.getFullName());
                                    userJson.put("profileName", user.getProfileName());
                                    userJson.put("profile_image", user.getProfileImage());
                                    usersArray.put(userJson);
                                }
                                listMessage.put("users", usersArray);

                                JSONObject sendMessageData = new JSONObject();
                                sendMessageData.put("roomId", chatRoomId);
                                sendMessageData.put("userId", userId);
                                sendMessageData.put("message", messageText);
                                sendMessageData.put("listMessage", listMessage);

                                mSocket.emit("sendMessage", sendMessageData);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    isSendingMessage = false;
                }

                @Override
                public void onFailure(Call<ChatRoomSendMessageResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                    isSendingMessage = false;
                }
            });
        } else {
            isSendingMessage = false; // Reset the flag if no message to send
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
                        chatAdapter.updateMessages(newMessages, recyclerView);
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
        // Emit leaveRoom event
        if (userId != null) {
            JSONObject leaveRoomData = new JSONObject();
            try {
                leaveRoomData.put("roomId", chatRoomId);
                leaveRoomData.put("userId", userId);
                mSocket.emit("leaveRoom", leaveRoomData);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        mSocket.off("newMessage", onNewMessage);
        mSocket.disconnect();
    }
}
