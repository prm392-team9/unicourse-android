package com.example.unicourse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.unicourse.ui.activities.ActivitySupportContact;
import com.example.unicourse.ui.activities.ControllerActivity;
import com.example.unicourse.ui.activities.LoginActivity;

public class UnicourseApplication extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_default);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        boolean rememberMe = sharedPreferences.getBoolean("remember_me", false);

        new CountDownTimer(3000, 1000) {
            @Override
            public void onTick(long l) {
                // No action needed
            }

            @Override
            public void onFinish() {
                Intent intent;
                if (rememberMe) {
                    intent = new Intent(UnicourseApplication.this, ControllerActivity.class);
                } else {
                    intent = new Intent(UnicourseApplication.this, LoginActivity.class);
                }
                startActivity(intent);
                finish();
            }
        }.start();
    }
}