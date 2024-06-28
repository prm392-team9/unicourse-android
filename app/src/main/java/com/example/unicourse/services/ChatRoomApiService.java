package com.example.unicourse.services;

import com.example.unicourse.models.chatroom.ChatRoomDetailResponse;
import com.example.unicourse.models.chatroom.ChatRoomSendMessageResponse;
import com.example.unicourse.models.chatroom.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ChatRoomApiService {
    @GET("chatRoom/get-room/{id}")
    Call<ChatRoomDetailResponse> getChatRoomDetail(@Path("id") String id);

    @PUT("chatRoom/send-message")
    Call<ChatRoomSendMessageResponse> sendMessage(@Body SendMessageRequest body);

    class ApiResponse {
        private String message;
        private Number status;
        private List<Message> data;
    }

    class SendMessageRequest {
        String chatRoomId;
        String message;

        public SendMessageRequest(String chatRoomId, String message) {
            this.chatRoomId = chatRoomId;
            this.message = message;
        }
    }
}

