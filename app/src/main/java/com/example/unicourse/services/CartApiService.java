package com.example.unicourse.services;


import com.example.unicourse.models.cart.CartResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface CartApiService {
    @POST("cart/add-to-cart/{id}")
    Call<CartResponse> addToCart(@Path("id") String courseId);
}
