package com.example.unicourse.services;

import com.example.unicourse.models.chatroom.ChatRoomDetailResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ChatRoomApiService {
    @GET("chatRoom/get-room/{id}")
    Call<ChatRoomDetailResponse> getChatRoomDetail(@Path("id") String id);
}

