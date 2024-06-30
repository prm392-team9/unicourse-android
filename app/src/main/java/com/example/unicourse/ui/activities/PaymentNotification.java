package com.example.unicourse.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.unicourse.R;

public class PaymentNotification extends AppCompatActivity {
    TextView txtNotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment_notification);

        txtNotification = findViewById(R.id.textViewNotify);

        Intent intent = getIntent();
        txtNotification.setText(intent.getStringExtra("result"));
    }
}