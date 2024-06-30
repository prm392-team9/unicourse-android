package com.example.unicourse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.unicourse.R;
import com.example.unicourse.adapters.CartAdapter;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.contants.CommonConstants;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.models.user.Cart;
import com.example.unicourse.services.UserApiService;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {

    private static final String BASE_URL = ApiConstants.BASE_URL;
    private String accessToken = null;
    private String username = null;
    private String userAvt = null;
    private boolean isEditMode = false;
    private Cart cartData = null;
    private ImageView userImage = null;
    private TextView usernameTxt = null;
    private RecyclerView cartRecyclerView = null;
    private TextView finalPriceTxt = null;
    private TextView totalItemBadge = null;
    private Button checkoutBtn = null;
    private Button selectAllBtn = null;
    private ImageButton editBtn = null;
    private ImageButton backBtn = null;
    private CartAdapter cartAdapter = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        userImage = findViewById(R.id.userImage);
        usernameTxt = findViewById(R.id.usernameTxt);
        cartRecyclerView = findViewById(R.id.cartRecyclerView);
        finalPriceTxt = findViewById(R.id.finalPrice);
        checkoutBtn = findViewById(R.id.checkoutBtn);
        totalItemBadge = findViewById(R.id.totalItemBadge);
        editBtn = findViewById(R.id.cartEditBtn);
        selectAllBtn = findViewById(R.id.cartSelectAllBtn);
        backBtn = findViewById(R.id.cartBackBtn);

        backBtn.setOnClickListener(v -> finish());

        selectAllBtn.setOnClickListener(v -> {
            if (cartData != null && cartData.getItems() != null) {
                if (selectAllBtn.getText().equals("Chọn tất cả")) {
                    for (Cart.Item item : cartData.getItems()) {
                        item.setSelected(true);
                    }
                    cartAdapter.notifyDataSetChanged();
                    selectAllBtn.setText("Bỏ chọn tất cả");
                    selectAllBtn.setWidth(selectAllBtn.getWidth() + 20);
                    selectAllBtn.setBackground(getDrawable(R.drawable.activity_cart_unselectall_button_rounded));
                    selectAllBtn.setTextColor(getColor(R.color.red));
                } else {
                    for (Cart.Item item : cartData.getItems()) {
                        item.setSelected(false);
                    }
                    cartAdapter.notifyDataSetChanged();
                    selectAllBtn.setText("Chọn tất cả");
                    selectAllBtn.setWidth(selectAllBtn.getWidth() - 20);
                    selectAllBtn.setBackground(getDrawable(R.drawable.activity_cart_selectall_button_rounded));
                    selectAllBtn.setTextColor(getColor(R.color.primary600));
                }
            }
        });

        getUserPrefs();

        checkoutBtn.setOnClickListener(v -> {
            if (cartData != null) {
                // Handle checkout action send final price to PaymentScreenActivity
                Integer total = cartData.getTotalPrice();
                Intent intent = new Intent(CartActivity.this, PaymentScreenActivity.class);
                intent.putExtra("total", total);

                // Store cart data in SharedPreferences
                SharedPreferences sharedPreferences = getSharedPreferences("OrderPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("total_new_amount", total);
                editor.putString("cart_id", cartData.getId());
                editor.putString("payment_method", CommonConstants.PAYPAL);
                editor.putString("transaction_code", generateTransactionCode());

                editor.apply();
                startActivity(intent);
            } else {
                Toast.makeText(CartActivity.this, "Giỏ hàng không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });

        usernameTxt.setText(username);
        Glide.with(this).load(userAvt).into(userImage);

        renderCartData();
    }

    private void getUserPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
        username = sharedPreferences.getString("user_full_name", null);
        userAvt = sharedPreferences.getString("profile_image", null);
    }

    private void renderCartData() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest.newBuilder()
                                .header("Authorization", "Bearer " + accessToken);
                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserApiService userApiService = retrofit.create(UserApiService.class);

        // Load user's cart data here
        Call<CommonResponse<Cart>> cartCall = userApiService.getUserCart();

        cartCall.enqueue(new Callback<CommonResponse<Cart>>() {
            @Override
            public void onResponse(Call<CommonResponse<Cart>> call, Response<CommonResponse<Cart>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommonResponse<Cart> userCartResponse = response.body();
                    if (String.valueOf(userCartResponse.getStatus()).equals("200") && userCartResponse.getData() != null) {
                        cartData = userCartResponse.getData();
                        if (cartData.getItems() != null && !cartData.getItems().isEmpty()) {
                            String totalItem = "(" + cartData.getItems().size() + ")";
                            cartAdapter = new CartAdapter(CartActivity.this, cartData.getItems(), isEditMode, cartData.getId());
                            cartRecyclerView.setAdapter(cartAdapter);
                            cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false));
                            totalItemBadge.setText(totalItem);
                            finalPriceTxt.setText(formatNumber(cartData.getAmount()));

                            editBtn.setOnClickListener(v -> {
                                if (cartAdapter != null) {
                                    isEditMode = !isEditMode;
                                    selectAllBtn.setClickable(!isEditMode);
                                    cartAdapter = new CartAdapter(CartActivity.this, cartData.getItems(), isEditMode, cartData.getId());
                                    cartRecyclerView.setAdapter(cartAdapter);
                                    cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false));
                                }
                            });
                        } else {
                            // Handle empty cart case
                            cartData = new Cart(); // Create an empty cart object to avoid null checks
                            cartAdapter = new CartAdapter(CartActivity.this, cartData.getItems(), isEditMode, "");
                            cartRecyclerView.setAdapter(cartAdapter);
                            cartRecyclerView.setLayoutManager(new LinearLayoutManager(CartActivity.this, RecyclerView.VERTICAL, false));
                            totalItemBadge.setText("(0)");
                            finalPriceTxt.setText(formatNumber(0));
                        }
                    } else {
                        Toast.makeText(CartActivity.this, userCartResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse<Cart>> call, Throwable throwable) {
                Toast.makeText(CartActivity.this, "Giỏ hàng không có dữ liệu", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String formatNumber(int number) {
        return NumberFormat.getNumberInstance(Locale.GERMAN).format(number) + " VNĐ";
    }

    private String generateTransactionCode() {
        return "UNI" + System.currentTimeMillis();
    }
}