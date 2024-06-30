package com.example.unicourse.ui.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.unicourse.R;
import com.example.unicourse.adapters.TransactionHistoryAdapter;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.models.common.CommonResponse;
import com.example.unicourse.models.user.TransactionHistory;
import com.example.unicourse.services.UserApiService;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TransactionHistoryActivity extends AppCompatActivity {

    private static final String BASE_URL = ApiConstants.BASE_URL;
    private RecyclerView transactionHistoryRV;
    private String accessToken;
    private TransactionHistoryAdapter transactionAdapter;
    private ImageButton backBtn;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction_history);

        transactionHistoryRV = findViewById(R.id.transactionHistoryRV);
        backBtn = findViewById(R.id.cartBackBtn);

        backBtn.setOnClickListener(v -> finish());

        getUserPrefs();
        renderData();
    }

    private void getUserPrefs() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", null);
    }

    private void renderData() {
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
        Call<CommonResponse<ArrayList<TransactionHistory>>> call = userApiService.getTransactionHistory();

        call.enqueue(new Callback<CommonResponse<ArrayList<TransactionHistory>>>() {
            @Override
            public void onResponse(Call<CommonResponse<ArrayList<TransactionHistory>>> call, Response<CommonResponse<ArrayList<TransactionHistory>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CommonResponse<ArrayList<TransactionHistory>> userTransactionResponse = response.body();
                    if (String.valueOf(userTransactionResponse.getStatus()).equals("200")) {
                        transactionAdapter = new TransactionHistoryAdapter(TransactionHistoryActivity.this, userTransactionResponse.getData());
                        transactionHistoryRV.setAdapter(transactionAdapter);
                        transactionHistoryRV.setLayoutManager(new LinearLayoutManager(TransactionHistoryActivity.this, RecyclerView.VERTICAL, false));
                    }
                }
            }

            @Override
            public void onFailure(Call<CommonResponse<ArrayList<TransactionHistory>>> call, Throwable throwable) {
                Toast.makeText(TransactionHistoryActivity.this, "Failed loading in THActivity: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
