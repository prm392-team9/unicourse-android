package com.example.unicourse.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicourse.R;
import com.example.unicourse.zaloconfig.Api.CreateOrder;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONObject;

import java.math.BigDecimal;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class PaymentScreenActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 123;
    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AanoC__oibyjcZdSFp4tfsQSBE3vwg0yTlhYsO56JAKze3t1c4w6W4OTDXVdWlF3rVUj8mEW0zlTt2U5");

    private RadioGroup radioGroup;
    private RadioButton radioPayPal, radioApplePay, radioZaloPay;
    private Button btnPayment;
    private ImageButton backBtn = null;
    TextView txtTongTien;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        // ZaloPay SDK Init
        ZaloPaySDK.init(553, Environment.SANDBOX);

        // PayPal Service Init
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);

        // Initialize views
        radioGroup = findViewById(R.id.radioGroup);
        radioPayPal = findViewById(R.id.radioPayPal);
        radioApplePay = findViewById(R.id.radioApplePay);
        radioZaloPay = findViewById(R.id.radioZaloPay);
        btnPayment = findViewById(R.id.btnPayment);
        backBtn = findViewById(R.id.backBtn);

        // Set up radio buttons
        radioPayPal.setOnClickListener(v -> selectPaymentMethod(radioPayPal));
        radioApplePay.setOnClickListener(v -> selectPaymentMethod(radioApplePay));
        radioZaloPay.setOnClickListener(v -> selectPaymentMethod(radioZaloPay));

        Intent intentData = getIntent();
        Integer total = intentData.getIntExtra("total", 0);
        String totalString = Integer.toString(total);

        // Set up add new card button
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioPayPal.isChecked()) {
                    processPayPalPayment(totalString);
                } else if (radioZaloPay.isChecked()) {
                    processZaloPayPayment(totalString);
                } else if (radioApplePay.isChecked()) {
                    Toast.makeText(PaymentScreenActivity.this, "Apple Pay đang phát triển", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBtn.setOnClickListener(v -> {
            finish();
        });
    }

    private void processPayPalPayment(String amount) {
        PayPalPayment payment = new PayPalPayment(new BigDecimal(amount), "USD", "UniCourse Payment",
                PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

        startActivityForResult(intent, PAYPAL_REQUEST_CODE);
    }

    private void processZaloPayPayment(String amount) {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(amount);
            String code = data.getString("return_code");
            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");
                ZaloPaySDK.getInstance().payOrder(PaymentScreenActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(String s, String s1, String s2) {
                        Intent intent1 = new Intent(PaymentScreenActivity.this, PaymentNotification.class);
                        intent1.putExtra("result", "Thanh toán thành công");
                        startActivity(intent1);
                    }

                    @Override
                    public void onPaymentCanceled(String s, String s1) {
                        Intent intent1 = new Intent(PaymentScreenActivity.this, PaymentNotification.class);
                        intent1.putExtra("result", "Hủy thanh toán");
                        startActivity(intent1);
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String s, String s1) {
                        Intent intent1 = new Intent(PaymentScreenActivity.this, PaymentNotification.class);
                        intent1.putExtra("result", "Lỗi thanh toán");
                        startActivity(intent1);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    JSONObject jsonObject = confirm.toJSONObject();
                    // Handle payment confirmation
//                    Toast.makeText(this, "Payment successful", Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(PaymentScreenActivity.this, PaymentNotification.class);
                    intent1.putExtra("result", "Thanh toán thành công");
                    startActivity(intent1);
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Payment cancelled", Toast.LENGTH_LONG).show();
            } else if (resultCode == com.paypal.android.sdk.payments.PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "Invalid payment", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void selectPaymentMethod(RadioButton selectedRadioButton) {
        // Deselect all radio buttons
        radioGroup.clearCheck();
        // Select the clicked radio button
        selectedRadioButton.setChecked(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}