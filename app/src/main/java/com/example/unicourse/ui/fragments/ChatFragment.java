package com.example.unicourse.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.airbnb.lottie.LottieAnimationView;
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
    private ProgressBar progressBar;
    private TextView roomName;
    private TextView roomStatus;
    private ImageButton backButton;

    private Socket mSocket;
    private boolean isSendingMessage = false;
    private String chatRoomId = "65ebe4d2d0cb58ef9cb250cc";
    private ChatRoomApiService chatRoomService;
    private String userId = null;
    private String accessToken = null;
    private ChatRoomDetail currentChatRoomDetail;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LottieAnimationView loadingAnimation;

    private static final String PREF_JOINED_ROOM_KEY = "joined_room_";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chat, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        editTextMessage = view.findViewById(R.id.editTextMessage);
        buttonSend = view.findViewById(R.id.buttonSend);
        progressBar = view.findViewById(R.id.progressBar);
        roomName = view.findViewById(R.id.roomName);
        roomStatus = view.findViewById(R.id.roomStatus);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        loadingAnimation = view.findViewById(R.id.loadingAnimation);

        // Retrieve userId and accessToken from SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getString("user_id", null);
        accessToken = sharedPreferences.getString("access_token", null);

        // Initialize chatRoomService
        chatRoomService = RetrofitClient.getClient(ApiConstants.BASE_URL, accessToken).create(ChatRoomApiService.class);

        // Check if the user has already joined the room
        if (!sharedPreferences.getBoolean(PREF_JOINED_ROOM_KEY + chatRoomId, false)) {
            joinChatRoom(chatRoomId);
        }

        chatViewModel = new ViewModelProvider(this).get(ChatViewModel.class);
        chatAdapter = new ChatAdapter(new ArrayList<>());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(chatAdapter);

        showLoading();
        chatViewModel.getChatRoomDetail(chatRoomId).observe(getViewLifecycleOwner(), chatRoomDetail -> {
            if (chatRoomDetail != null) {
                currentChatRoomDetail = chatRoomDetail;
                roomName.setText(chatRoomDetail.getName());
                roomStatus.setText(chatRoomDetail.getStatus());
                chatAdapter.updateMessages(chatRoomDetail.getMessages(), recyclerView);
                hideLoading();
            }
        });

        swipeRefreshLayout.setOnRefreshListener(() -> {
            showLoading();
            chatViewModel.getChatRoomDetail(chatRoomId).observe(getViewLifecycleOwner(), chatRoomDetail -> {
                if (chatRoomDetail != null) {
                    currentChatRoomDetail = chatRoomDetail;
                    roomName.setText(chatRoomDetail.getName());
                    roomStatus.setText(chatRoomDetail.getStatus());
                    chatAdapter.updateMessages(chatRoomDetail.getMessages(), recyclerView);
                    hideLoading();
                }
            });
            swipeRefreshLayout.setRefreshing(false);
        });

        buttonSend.setOnClickListener(v -> {
            sendMessage();
        });

        return view;
    }

    private void joinChatRoom(String chatRoomId) {
        Call<Void> call = chatRoomService.joinChatRoom(chatRoomId);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Save to SharedPreferences that the user has joined this room
                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean(PREF_JOINED_ROOM_KEY + chatRoomId, true);
                    editor.apply();
                } else {
//                    Toast.makeText(getContext(), "Failed to join chat room", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
//                Toast.makeText(getContext(), "Failed to join chat room", Toast.LENGTH_SHORT).show();
            }
        });
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
        showLoading();

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
                    hideLoading();
                }

                @Override
                public void onFailure(Call<ChatRoomSendMessageResponse> call, Throwable t) {
                    Toast.makeText(getContext(), "Failed to send message", Toast.LENGTH_SHORT).show();
                    isSendingMessage = false;
                    hideLoading();
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

    private void showLoading() {
        loadingAnimation.setVisibility(View.VISIBLE);
        loadingAnimation.playAnimation();

    }

    private void hideLoading() {
        loadingAnimation.setVisibility(View.GONE);
        loadingAnimation.pauseAnimation();
    }
}