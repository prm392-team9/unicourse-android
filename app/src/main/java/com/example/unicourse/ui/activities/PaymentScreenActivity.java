package com.example.unicourse.ui.activities;

import static com.example.unicourse.R.*;

import android.annotation.SuppressLint;
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
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.paypal.android.sdk.payments.PaymentActivity;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class PaymentScreenActivity extends AppCompatActivity {

    private static final int PAYPAL_REQUEST_CODE = 123;
    private static final PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX)
            .clientId("AanoC__oibyjcZdSFp4tfsQSBE3vwg0yTlhYsO56JAKze3t1c4w6W4OTDXVdWlF3rVUj8mEW0zlTt2U5");

    private RadioGroup radioGroup;
    private RadioButton radioPayPal, radioApplePay, radioZaloPay;
    private Button btnPayment;
    private ImageButton backBtn = null;
    TextView cartItemFinalPriceVND, cartItemFinalPriceUSD;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

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
        cartItemFinalPriceVND = findViewById(R.id.cartItemFinalPriceVND);
        cartItemFinalPriceUSD = findViewById(R.id.cartItemFinalPriceUSD);

        // Set up radio buttons
        radioPayPal.setOnClickListener(v -> selectPaymentMethod(radioPayPal));
        radioApplePay.setOnClickListener(v -> selectPaymentMethod(radioApplePay));
        radioZaloPay.setOnClickListener(v -> selectPaymentMethod(radioZaloPay));

        Intent intentData = getIntent();
        Integer total = intentData.getIntExtra("total", 0);
        String totalString = Integer.toString(total);
        String totalUSDString = Integer.toString(total / 25000);

        // Format to VNĐ
        NumberFormat vndFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
        String totalVnd = vndFormat.format(total);
        cartItemFinalPriceVND.setText(totalVnd);

        // Format to USD
        NumberFormat usdFormat = NumberFormat.getCurrencyInstance(Locale.US);
        String totalUsd = usdFormat.format(total / 25000);
        cartItemFinalPriceUSD.setText(totalUsd);

        // Set up add new card button
        btnPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (radioPayPal.isChecked()) {
                    processPayPalPayment(totalUSDString);
                } else if (radioZaloPay.isChecked()) {
                    Toast.makeText(PaymentScreenActivity.this, "Zalo Pay đang phát triển", Toast.LENGTH_SHORT).show();
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