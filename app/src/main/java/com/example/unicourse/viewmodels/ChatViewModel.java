package com.example.unicourse.viewmodels;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.chatroom.ChatRoomDetail;
import com.example.unicourse.models.chatroom.ChatRoomDetailResponse;
import com.example.unicourse.services.ChatRoomApiService;
import com.example.unicourse.services.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatViewModel extends AndroidViewModel {
    private MutableLiveData<ChatRoomDetail> chatRoomDetail = new MutableLiveData<>();
    private ChatRoomApiService chatRoomApiService;
    private String accessToken;

    public ChatViewModel(@NonNull Application application) {
        super(application);

        // Retrieve accessToken from SharedPreferences
        SharedPreferences sharedPreferences = application.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);

        // Initialize chatRoomApiService with accessToken
        chatRoomApiService = RetrofitClient.getClient(ApiConstants.BASE_URL, accessToken).create(ChatRoomApiService.class);
    }

    public LiveData<ChatRoomDetail> getChatRoomDetail(String id) {
        chatRoomApiService.getChatRoomDetail(id).enqueue(new Callback<ChatRoomDetailResponse>() {
            @Override
            public void onResponse(Call<ChatRoomDetailResponse> call, Response<ChatRoomDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chatRoomDetail.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ChatRoomDetailResponse> call, Throwable t) {
                // Handle failure
            }
        });
        return chatRoomDetail;
    }
}
