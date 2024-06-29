package com.example.unicourse.services;

import com.example.unicourse.models.EnrolledCourseProgressResponse;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.models.user.Cart;
import com.example.unicourse.models.user.DeleteCartResponse;
import com.example.unicourse.models.user.ProfileResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApiService {
    @GET("user/{userId}")
    Call<ProfileResponse> getUser(@Path("userId") String userId);

    @GET("user/{userId}/get-enrolled-course")
    Call<EnrolledCourseProgressResponse> getCourseProgress(@Path("userId") String userId);

    @GET("cart/retrieve-user-cart")
    Call<CommonResponse<Cart>> getUserCart();

    @PUT("cart/{cartId}/delete-single/{courseId}")
    Call<CommonResponse<DeleteCartResponse>> deleteCourseFromCart(
            @Path("cartId") String cartId,
            @Path("courseId") String courseId
    );
}
