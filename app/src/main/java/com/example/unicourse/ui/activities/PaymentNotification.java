package com.example.unicourse.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicourse.R;
import com.example.unicourse.contants.ApiConstants;
import com.example.unicourse.contants.CommonConstants;
import com.example.unicourse.services.ChatRoomApiService;
import com.example.unicourse.services.RetrofitClient;
import com.example.unicourse.services.TransactionApiService;
import com.example.unicourse.models.transaction.TransactionResponse;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentNotification extends AppCompatActivity {
    private JSONObject payer;
    private String cart_id, payment_method, status, transaction_code;
    private Integer total_new_amount;
    private Boolean used_coin = false;
    private TransactionApiService apiService;
    private String userId = null;
    private String accessToken = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_notification);

        // Retrieve userId and accessToken from SharedPreferences
        SharedPreferences sharedPreferences2 = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences2.getString("user_id", null);
        accessToken = sharedPreferences2.getString("access_token", null);

        apiService = RetrofitClient.getClient(ApiConstants.BASE_URL, accessToken).create(TransactionApiService.class);

        // Get the intent and the result message
        Intent intent = getIntent();
        String resultMessage = intent.getStringExtra("result");

        // Get data from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("OrderPrefs", MODE_PRIVATE);
        cart_id = sharedPreferences.getString("cart_id", "");
        payment_method = sharedPreferences.getString("payment_method", "");
        total_new_amount = sharedPreferences.getInt("total_new_amount", 0);
        transaction_code = sharedPreferences.getString("transaction_code", "");

        // Set the result message in the TextView
        TextView statusMessage = findViewById(R.id.statusMessage);
        statusMessage.setText(resultMessage);

        // Set the status icon based on the result
        ImageView statusIcon = findViewById(R.id.statusIcon);
        if ("Thanh toán thành công".equals(resultMessage)) {
            statusIcon.setImageResource(R.drawable.payment_notify_ic_check);
            try {
                payer = new JSONObject();
                payer.put("cart_id", cart_id);
                payer.put("payment_method", payment_method);
                payer.put("total_new_amount", total_new_amount);
                payer.put("transaction_code", transaction_code);
                payer.put("status", CommonConstants.SUCCESS);

                // Call the API to notify the payment success
                TransactionApiService.TransactionRequest request = new TransactionApiService.TransactionRequest(
                        payer, cart_id, payment_method, total_new_amount, null, CommonConstants.SUCCESS, transaction_code, used_coin
                );
                makePayment(request);

            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        } else {
            statusIcon.setImageResource(R.drawable.payment_notify_ic_error);
        }

        // Handle the return to home button click
        Button returnHomeButton = findViewById(R.id.returnHomeButton);
        returnHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(PaymentNotification.this, ControllerActivity.class);
                startActivity(homeIntent);
                finish();
            }
        });
    }

    private void makePayment(TransactionApiService.TransactionRequest request) {
        Call<TransactionResponse> call = apiService.payWithPaypal(request);
        call.enqueue(new Callback<TransactionResponse>() {
            @Override
            public void onResponse(Call<TransactionResponse> call, Response<TransactionResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(PaymentNotification.this, "Payment successful!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentNotification.this, "Payment failed!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<TransactionResponse> call, Throwable t) {
                Toast.makeText(PaymentNotification.this, "An error occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}