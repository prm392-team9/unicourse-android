package com.example.unicourse.services;

import com.example.unicourse.models.transaction.TransactionResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.GET;

public interface TransactionApiService {

    @POST("transactions/pay")
    Call<TransactionResponse> payWithPaypal(@Body TransactionRequest body);

    class TransactionRequest {
        private Object payer;
        private String cart_id;
        private String payment_method;
        private double total_new_amount;
        private String voucher_id;
        private String status;
        private String transaction_code;
        private boolean used_coin;

        public TransactionRequest(Object payer, String cart_id, String payment_method, double total_new_amount, String voucher_id, String status, String transaction_code, boolean used_coin) {
            this.payer = payer;
            this.cart_id = cart_id;
            this.payment_method = payment_method;
            this.total_new_amount = total_new_amount;
            this.voucher_id = voucher_id;
            this.status = status;
            this.transaction_code = transaction_code;
            this.used_coin = used_coin;
        }
    }
}